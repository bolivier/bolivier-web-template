(ns user
  (:require [com.walmartlabs.lacinia :as lacinia]
            [{{name}}.core]
            [{{name}}.db.core :as db]
            [{{name}}.db.schema :refer [schema]]
            [migratus.core :as migratus]
            [mount.core :as mount]
            [midje.sweet :as m]
            [camel-snake-kebab.core :refer [->snake_case]]
            [clojure.string :refer [join]]
            [honeysql.core :refer [format]]
            [honeysql.helpers :refer [select where limit from insert-into columns values sset]
             :as helpers]))

(defn start []
  (mount/start))

(defn stop []
  (mount/stop))

(defn restart []
  (stop)
  (start))

(def config {:db db/db
             :store :database})
(defn migrate []
  (migratus/migrate config))

(defn create-migration [name]
  (migratus/create config name))
