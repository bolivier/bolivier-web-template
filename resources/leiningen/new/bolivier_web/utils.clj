(ns {{name}}.utils
  (:require [camel-snake-kebab.core :refer [->camelCase
                                            ->kebab-case
                                            ->snake_case_string]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [clojure.walk :as walk]))

(defn- raw-to-uuid
  "This func takes a _guaranteed_ string and converts it
  into a uuid.

  Do not call this.  Use the safer ->uuid, which will do
  a noop for existing uuids"
  [str]
  (java.util.UUID/fromString str))

(defmulti ->uuid class)
(defmethod ->uuid java.util.UUID [u] u)
(defmethod ->uuid String [s] (raw-to-uuid s))

(defn- replace-keyword [elm]
  (when (vector? elm)
    (let [[k v] elm]
      [(-> k name keyword) v])))

(defn unqualify-keywords [coll]
  (clojure.walk/postwalk (some-fn replace-keyword identity) coll))

(defn kebabize [coll]
  (transform-keys ->kebab-case coll))

(defn camelize [coll]
  (transform-keys
   (fn [k]
     (if (re-find #"^__" (name k))
       k
       (->camelCase k)))
   coll))

(defn snakeize [coll]
  (transform-keys ->snake_case_string coll))

(defn prepare-for-client
  "The graphql client needs to accept and return data of the same form.

  For now, that means that it needs to return strings that are camel case."
  [coll]
  (->> coll
       unqualify-keywords
       (transform-keys ->camelCase)))
