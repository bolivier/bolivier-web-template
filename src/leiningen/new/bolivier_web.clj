(ns leiningen.new.bolivier-web
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string :as str]))

(def render (renderer "bolivier-web"))

(def valid-flags #{"+graphql"})

(defn bolivier-web
  "FIXME: write documentation"
  [name & flags]
  (let [sanitized  (name-to-path name)
        graphql? (get flags "+graphql")
        data {:name name
              :sanitized sanitized
              :graphql graphql?}]
    (main/info "Generating fresh 'lein new' bolivier-web project.")
    (apply ->files
           data
           ;; ->files doesn't support `nil` so we just remove all the nils beforehand
           (remove nil?
                   [[".gitignore"
                     (render "gitignore" data)]

                    ["README.md"
                     (render "README.md" data)]

                    ["config/dev/config.sample.edn"
                     (render "config.sample.edn" data)]

                    ["env/dev/clj/user.clj"
                     (render "user.clj" data)]

                    ["project.clj"
                     (render "project.clj" data)]

                    (when graphql?
                      ["resources/schema.edn"
                       (render "schema.edn" data)])

                    ["resources/public/index.html"
                     (render "index.html" data)]

                    ["shadow-cljs.edn"
                     (render "shadow-cljs.edn" data)]


                    [(str "src/clj/" sanitized "/core.clj")
                     (render (if graphql?
                               "core-graphql.clj"
                               "core-rest.clj") data)]

                    [(str "src/clj/" sanitized "/db/core.clj")
                     (render "db_core.clj" data)]

                    [(str "src/clj/" sanitized "/utils.clj")
                     (render "utils.clj" data)]

                    [(str "src/cljs/" sanitized "/core.cljs")
                     (render "core.cljs" data)]
                    (when graphql?
                      [(str "src/clj/" sanitized "/db/schema.clj")
                       (render "schema.clj" data)])

                    [(str "src/clj/" sanitized "/db/schema.clj")
                     (render "schema.clj" data)]

                    ["package.json"
                     (render "package.json" data)]

                    ["resources/public/css/tailwind.css"
                     (render "tailwind.css" data)]]))))
