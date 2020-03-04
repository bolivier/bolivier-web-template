(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-core "1.8.0"]
                 [ring/ring-jetty-adapter "1.8.0"]
                 [compojure "1.6.1"]
                 [seancorfield/next.jdbc "1.0.12"]
                 [honeysql "0.9.8"]
                 [migratus "1.2.7"]
                 [org.slf4j/slf4j-log4j12 "1.7.29"]
                 [hikari-cp "2.9.0"]
                 [org.postgresql/postgresql "42.2.6"]
                 [yogthos/config "1.1.7"]
                 [mount "0.1.16"]
                 [aleph "0.4.7-alpha5"]
                 {{#graphql}}
                 [com.walmartlabs/lacinia "0.36.0-alpha-2"]
                 {{/graphql}}
                 [camel-snake-kebab "0.4.1"]
                 [midje/midje "1.9.9"]
                 [metosin/reitit "0.3.10"]
                 [cheshire "5.9.0"]
                 [metosin/reitit-middleware "0.3.10"]
                 [metosin/muuntaja "0.6.6"]
                 [org.clojure/spec.alpha "0.2.176"]
                 [ring-cors "0.1.13"]
                 [thheller/shadow-cljs "2.8.83"]
                 [reagent "0.9.1"]]
  :main ^:skip-aot {{name}}.core
  :target-path "target/%s"
  :repl-options {:init-ns user}
  :source-paths ["src/clj" "src/cljs"]
  :profiles {:uberjar {:aot :all
                       :resource-paths ["config/prod"]}
             :prod {:resource-paths ["config/prod"]}
             :dev {:source-paths ["env/dev/clj"]
                   :resource-paths ["config/dev"]}})
