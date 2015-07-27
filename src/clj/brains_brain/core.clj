(ns brains-brain.core
  (:require [seesaw.core :refer :all]
            [seesaw.graphics :refer [draw rect style]]
            [seesaw.border :refer [line-border]]
            [clojure.string :as str]
            )
  (:gen-class))

(native!)

;; actual board size is 90 x 90
(def board-size 180)

;; board size with grid
(def size (+ 3 (* 2 (- board-size 1))))


(defn draw-grid [c g]
  (let [cellw 3
        cellh 3
        ]
    (doseq [x (range 1 size 2)
            y (range 1 size 2)]
      (draw g
            (rect x y 1 1)
            (style :background :white ;; "#F03232"
                   )
            )
      )))

(defn make-ui
  []
  (config!
   (frame :title "Brian's Brain" :on-close :exit)
   :content (border-panel
             :center (canvas :id :canvas
                             ;; :size [270 :by 270]
                             :size [ size :by size ]
                             :background :black
                             :border (line-border :thickness 2 :color :white)
                             :paint draw-grid
                             )
             :border 5)
   ))


(defn -main [& args]
  (let [ui (make-ui)]
    (-> ui pack! show!)))