(ns brains-brain.core
  (:require [seesaw.core :refer :all])
  (:gen-class))

(native!)


(def main-frame
  (frame :title "Brian's Brain" :on-close :exit))

(def field-x (text "1"))
(def field-y (text "2"))

(def result-label (label ""))

(config! main-frame :content
         (border-panel
          :north (horizontal-panel :items [field-x field-y])
          :center result-label
          :border 5))

(defn -main [& args]
  (-> main-frame pack! show!))