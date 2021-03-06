(ns {{name}}.core
  (:require [aleph.http :as http]
            [cheshire.core :as cheshire]
            [reitit.coercion.spec :as spec]
            [reitit.ring.coercion :refer [coerce-request-middleware coerce-exceptions-middleware]]
            [{{name}}.utils :as utils]
            [mount.core :refer [defstate]]
            [{{name}}.middleware :refer [json-response-body json-request-body]]
            [reitit.ring :as ring]
            [ring.middleware.cors :refer [wrap-cors]]))

(defn ping-handler [req]
  {:status 200
   :body "ping"})

(defn parse-query-params [handler]
  (fn [req]
    (handler [req])))

(defn index-handler [_]
  {:status 200
   :body (slurp "resources/public/index.html")})

(defn create-router []
  (ring/ring-handler
   (ring/router
    [""
     {:middleware [coerce-request-middleware coerce-exceptions-middleware]
      :coercion spec/coercion}
     ["/" {:get {:handler index-handler}}]
     ["/api"
      {:middleware [json-request-body json-response-body]}
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
