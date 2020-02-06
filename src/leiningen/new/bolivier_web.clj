(ns leiningen.new.bolivier-web
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string :as str]))

(def render (renderer "bolivier-web"))

(defn bolivier-web
  "FIXME: write documentation"
  [name]
  (let [sanitized  (name-to-path name)
        data {:name name
              :sanitized sanitized}]
    (main/info "Generating fresh 'lein new' bolivier-web project.")
    (->files data
             [".gitignore"
              (render "gitignore" data)]

             ["README.md"
              (render "README.md" data)]

             ["config/dev/config.sample.edn"
              (render "config.sample.edn" data)]

             ["env/dev/clj/user.clj"
              (render "user.clj" data)]

             ["project.clj"
              (render "project.clj" data)]

             ["resources/schema.edn"
              (render "schema.edn" data)]

             ["resources/public/index.html"
              (render "index.html" data)]

             ["shadow-cljs.edn"
              (render "shadow-cljs.edn" data)]


             [(str "src/clj/" sanitized "/core.clj")
              (render "core.clj" data)]

             [(str "src/clj/" sanitized "/db/core.clj")
              (render "db_core.clj" data)]

             [(str "src/clj/" sanitized "/utils.clj")
              (render "utils.clj" data)]

             [(str "src/cljs/" sanitized "/core.cljs")
              (render "core.cljs" data)]

             [(str "src/clj/" sanitized "/db/schema.clj")
              (render "schema.clj" data)])))
