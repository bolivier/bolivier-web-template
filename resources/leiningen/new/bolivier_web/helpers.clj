(ns {{name}}.db.helpers
    "To use the helper, just require it from this file in addition to the
  rest of the helpers you need."
    (:require [honeysql.helpers :as helpers]
              [honeysql.format :as fmt]))

(helpers/defhelper returning [m args]
  (assoc m :returning (first args)))

(defmethod fmt/format-clause :returning [[op v] sqlmap]
  (str "RETURNING " (fmt/to-sql v)))
