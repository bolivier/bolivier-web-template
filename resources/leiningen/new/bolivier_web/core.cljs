(ns {{name}}.core
    (:require [reagent.core :as r]))

(defn ^:dev/after-load start []
  (r/render [:span "hello world"]
            (.getElementById js/document "app")))

(defn init []
  (start))
