(ns brains-brain.core
  (:require [seesaw.core :refer :all]
            [seesaw.graphics :refer [draw rect style]]
            [seesaw.border :refer [line-border]]
            [clojure.string :as str])
  (:use [clojure.pprint])
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

(vec (repeat 9 0))

(def a (vec (replicate 8 (vec (replicate 8 :off)))))

(nth (nth a 1 ) 1)

;; We can use clojure's `make-array' create 2d array, but we do our own here
(defn make-array
  [width height init-val]
  (vec
   (map vec
       (partition width (repeat (* width height) init-val)))))

(defn array-get
  [arr i j]
  (nth (nth arr i) j))

(def a (make-array 8 4 :off))

(def a (make-array 8 8 :off))

(map vec a)
(assoc [0 1 2] 1 5)

(assoc (seq (nth a 3)) 2 2)

(var-set '(1 2 3) 1 )

(assoc a 0 (assoc (nth a 1) 0 1))

(array-get a 1 1)

(defn arr-set
  [arr i j val]
  (nth arr i)
  )

(for [y (range 8) x (range 8)] :off );; {:x x :y y})
;;(partition  8 (for [y (range 8) x (range 8)] {:x x :y y}))
(nth (partition 5 (repeat 25 0)) 1 )


(nth (nth a 0) 1)

(assoc a)

(arr-get a 1 1)

;; TODO: this function need a little modify to run on cljs
(defn make-board
  []
  (let [board (make-array Integer/TYPE board-size board-size)]
    (doseq [x (range 0 board-size)
            y (range 0 board-size)]
      (aset-int board x y (if (< (rand-int 100) 50) 0 1)))
    board))

(def a (make-board))

(defn draw-grid [c g]
  (doseq [x (range 0 board-size)
          y (range 0 board-size)
          :let [cell (aget a x y)
                xb (* x cell-size)
                yb (* y cell-size)]]
    (draw g
          (rect xb yb cell-size)
          (style :background (case cell
                               1 :red
                               :white) ; default (:off)
                 :foreground :black ; broader
                 ))))

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