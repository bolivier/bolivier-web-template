(ns {{name}}.core
  (:gen-class)
  (:require [aleph.http :as http]
            [cheshire.core :as cheshire]            
            [{{name}}.utils :as utils]
            [mount.core :refer [defstate]]
            [reitit.ring :as ring]
            [ring.middleware.cors :refer [wrap-cors]]))

(defn ping-handler [req]
  {:status 200
   :body "fine"})

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
                :name ::ping}]]])
   (ring/routes
    (ring/create-resource-handler
     {:root ""
      :path "/"}))))

(declare server)
(defstate server
  :start (http/start-server (create-router) {:port 3000})
  :stop (.close server))
