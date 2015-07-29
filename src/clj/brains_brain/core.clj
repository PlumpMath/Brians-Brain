(ns brains-brain.core
  (:require [seesaw.core :refer :all]
            [seesaw.graphics :refer [draw rect style]]
            [seesaw.border :refer [line-border]]
            [clojure.string :as str]
            )
  (:gen-class))

(native!)

;; cell size
(def cell-size 6)

;; actual board size is 90 x 90
(def board-size 90)

;; board size with grid
(def size
  (+ 1
     (+ cell-size
        (* (- cell-size 1)
           (- board-size cell-size)))))

(println (str "size: " size))

(defn make-board
  []
  (let [board (make-array Integer/TYPE board-size board-size)]
    (doseq [x (range 0 board-size)
            y (range 0 board-size)]
      (aset-int board x y (if (< (rand-int 100) 50) 0 1))
      )
    board))
(use 'clojure.pprint)


;;(make-indexed )

(def a (make-board))
(pprint a)
(aget a 1 1)

(defn draw-grid [c g]
  (doseq [x (range 0 board-size)
          y (range 0 board-size)
          :let [cell (aget a x y)
                xb (* x cell-size)
                yb (* y cell-size)
                ]]
    (draw g
          ;;            (rect x y (dec cell-size))
          (rect xb yb cell-size)
          (style :background (case cell
                               1 :red
                               :white)
                                        ; :white ;; "#F03232"
                 :foreground :black ; broader
                 )
          )
    ))

(comment
  (defn draw-grid [c g]
    (doseq [x (range 0 size cell-size)
            y (range 0 size cell-size)]
      (draw g
            ;;            (rect x y (dec cell-size))
            (rect x y cell-size)
            (style :background :white
                                        ; :white ;; "#F03232"
                   :foreground :black ; broader
                   )
            )
      ))
  )

(defn make-ui
  []
  (config!
   (frame :title "Brian's Brain"
          :on-close :exit
          :resizable? false
          :content (border-panel
                    :center (canvas :id :canvas
                                    ;; :size [270 :by 270]
                                    :size [ size :by size ]
                                    :background :black
                                    ;; :border (line-border :thickness 2 :color :white)
                                    :paint draw-grid
                                    )
                    :border 5)
          )))


(defn -main [& args]
  (let [ui (make-ui)]
    (-> ui pack! show!)))