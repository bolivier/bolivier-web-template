(ns {{name}}.db.core
  (:require [hikari-cp.core :as hcp]
            [next.jdbc :as jdbc]
            [config.core :refer [env]]
            [mount.core :refer [defstate]]
            [honeysql.core :as sql]))

(def datasource-options {:auto-commit        true
                         :read-only          false
                         :connection-timeout 30000
                         :validation-timeout 5000
                         :idle-timeout       600000
                         :max-lifetime       1800000
                         :minimum-idle       10
                         :maximum-pool-size  10
                         :pool-name          "db-pool"
                         :adapter            "postgresql"
                         :username           (:username env)
                         :password           (:password env)
                         :database-name      (:database-name env)
                         :server-name        (:server-name env)
                         :port-number        5432
                         :register-mbeans    false})

(declare db)
(defstate db
  :start (hcp/make-datasource datasource-options)
  :stop (hcp/close-datasource db))

(defn run [raw-query]
  (let [query (if (map? raw-query)
                (sql/format raw-query)
                raw-query)]
    (jdbc/execute! db query)))
