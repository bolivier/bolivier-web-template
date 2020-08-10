(ns {{name}}.middleware
  (:require [byte-streams :as bs]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [cheshire.core :as cheshire]))

(defn remove-ns-key [handler]
  (fn [req]
    (update (handler req)
            :body
            #(transform-keys (comp keyword name) %))))

(defn json-response-body
  "This and `json-request-body` should probably be rewritten and
  incorporated into reitit coercion, but I'm not sure how to do that
  right now."
  [handler]
  (fn [req]
    (let [res (handler req)]
      (update res :body cheshire/encode))))

(defn json-request-body [handler]
  (fn [req]
    (if-let [body (:body req)]
     (let [new-body (cheshire/decode (bs/to-string body) keyword)]
       (handler (-> req
                    (assoc :body new-body)
                    (assoc :body-params new-body))))
     (handler req))))
