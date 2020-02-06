(ns {{name}}.db.schema
  "GraphQL schema management.

  The schema needs to have identical data inputted and outputted.

  I usually opt for camel cased column names in my tables.  If you
  don't, then you'll need to update `utils/prepare-for-client` to use
  whatever convention you're using."
  (:require [clojure.edn :as edn]
            [com.walmartlabs.lacinia.schema :as schema]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [{{name}}.utils :as utils :refer [->uuid]]
            [mount.core :refer [defstate]]))

(defn user-by-username [_ args _]
  (utils/prepare-for-client
   {:users/username "mr-user"}))

(defn resolver-map []
  {:query/user-by-username user-by-username})

(defn load-schema []
  (-> "resources/schema.edn"
      slurp
      edn/read-string
      (attach-resolvers (resolver-map))
      schema/compile))

(declare schema)
(defstate schema
  :start (load-schema))
