(ns user
  (:require [{{name}}.core]
            [{{name}}.db.core :as db]
            {{#graphql}}
            [com.walmartlabs.lacinia :as lacinia]
            [{{name}}.db.schema :refer [schema]]
            {{/graphql}}
            [shadow.cljs.devtools.server :as server]
            [shadow.cljs.devtools.api :as shadow]
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

(mount/defstate config
  :start {:db db/db
          :store :database})

(defn migrate []
  (migratus/migrate config))

(defn create-migration [name]
  (migratus/create config name))

;; _____ _       _                              _       _
;;/  __ | |     (_)                            (_)     | |
;;| /  \| | ___  _ _   _ _ __ ___ ___  ___ _ __ _ _ __ | |_
;;| |   | |/ _ \| | | | | '__/ _ / __|/ __| '__| | '_ \| __|
;;| \__/| | (_) | | |_| | | |  __\__ | (__| |  | | |_) | |_
;; \____|_|\___/| |\__,_|_|  \___|___/\___|_|  |_| .__/ \__|
;;             _/ |                              | |
;;            |__/                               |_|

(defn start-cljs []
  (server/start!))

(defn watch-cljs []
  (shadow/watch :frontend))

(defmacro switch-to-cljs-repl []
  (shadow/repl :frontend))
