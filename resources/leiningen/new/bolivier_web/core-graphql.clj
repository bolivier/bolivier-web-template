(ns {{name}}.core
  (:require [aleph.http :as http]
            [cheshire.core :as cheshire]
            [com.walmartlabs.lacinia :as lacinia]
            [{{name}}.db.schema :as schema]
            [{{name}}.utils :as utils]
            [mount.core :refer [defstate]]
            [reitit.ring :as ring]
            [ring.middleware.cors :refer [wrap-cors]]))

(defn ping-handler [req]
  {:status 200
   :body "fine"})

(defn graphql-handler [req]
  (let [body (-> req
                 :body
                 clojure.java.io/reader
                 (cheshire/parse-stream true))
        query (:query body)
        variables (:variables body)
        result (lacinia/execute schema/schema query variables nil)

        camel-cased-data (into {} (map (fn [[query-name query-result]]
                                         [query-name (utils/camelize query-result)])
                                       (:data result)))]
    {:status 200
     :body (cheshire/generate-string
            (assoc result
                   :data
                   camel-cased-data))}))



(defn parse-query-params [handler]
  (fn [req]
    (handler [req])))

(defn index-handler [_]
  {:status 200
   :body (slurp "resources/public/index.html")})

(defn wrap-with-cors
  "Wrap the server response in a Control-Allow-Origin Header to
  allow connections from the web app."
  [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Origin"] "*")
          (assoc-in [:headers "Access-Control-Allow-Headers"] "x-requested-with, content-type")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "*")))))

(defn create-router []
  (ring/ring-handler
   (ring/router
    [""
     ["/" {:get {:handler index-handler}}]
     ["/api"
      ["/ping" {:get {:handler ping-handler}
                :name ::ping}]
      ["/graphql" {:post {:handler graphql-handler}
                   :middleware [wrap-with-cors]
                   :name ::graphql}]]])
   (ring/routes
    (ring/create-resource-handler
     {:root ""
      :path "/"}))))

(declare server)
(defstate server
  :start (http/start-server (create-router) {:port 3000})
  :stop (.close server))
