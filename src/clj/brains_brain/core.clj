(ns brains-brain.core
  (:require [seesaw.core :refer :all]
            [seesaw.graphics :refer [draw rect style]]
            [seesaw.border :refer [line-border]]
            [clojure.string :as str]
            )
  (:gen-class))

(native!)

;; cell size
(def cell-size 9)

;; actual board size is 90 x 90
(def board-size 90)

;; board size with grid
(def size
  (+ 1
     (+ cell-size
        (* (- cell-size 1)
           (- board-size cell-size)
           )
        )
     ;;(+ 3 (* 2 (- board-size 1)))
     ))

(println (str "size: " size))

(defn draw-grid [c g]
  (let [cellw 3
        cellh 3
        ]
    (doseq [x (range 0 size cell-size)
            y (range 0 size cell-size)]
      (draw g
            ;;            (rect x y (dec cell-size))
            (rect x y cell-size)
            (style :background :white ;; "#F03232"
                   :foreground :black ; broader
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
                             ;; :border (line-border :thickness 2 :color :white)
                             :paint draw-grid
                             )
             :border 5)
   ))


(defn -main [& args]
  (let [ui (make-ui)]
    (-> ui pack! show!)))