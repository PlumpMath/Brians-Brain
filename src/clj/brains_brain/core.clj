(ns brains-brain.core
  (:require [seesaw.core :refer :all]
            [seesaw.graphics :refer [draw rect style]]
            [seesaw.border :refer [line-border]]
            [clojure.string :as str])
  (:use [clojure.pprint])
  (:refer-clojure :exclude [make-array])
  (:gen-class))

;; native ui
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

(defn draw-grid [c g]
  (doseq [x (range 0 board-size)
          y (range 0 board-size)
          :let [cell (aget c x y)
                xb (* x cell-size)
                yb (* y cell-size)]]
    (draw g
          (rect xb yb cell-size)
          (style :background (case cell
                               :on :red
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(comment

  ;; We can use clojure's `make-array' create 2d array, but we do our own here
  ;; and convert to array object
  (defn make-array
    "Create 2d-array object."
    ([width height] (make-array width height 0))
    ([width height init-val]
     (to-array-2d
      (partition width (repeat (* width height) init-val)))))

  (defn make-board
    "Create board with random initial value"
    []
    (let [board (make-array board-size board-size)]
      (doseq [x (range 0 board-size)
              y (range 0 board-size)]
        (aset board x y (if (< (rand-int 100) 50) :off :on)))
      board))

  (defn neighbors [[x y]]
    (for [dx [-1 0 1]
          dy (if (zero? dx)
               [-1 1]
               [-1 0 1])]
      [(+ dx x) (+ dy y)]))


  (defn create-world
    "Creates rectangular world with the specified width and height.
  Optionally takes coordinates of living cells."
    [w h & living-cells]
    (vec (for [y (range w)]
           (vec (for [x (range h)]
                  (if (contains? (first living-cells) [y x]) "X" " "))))))



  (defn create-world2
    "Creates rectangular world with the specified width and height.
  Optionally takes coordinates of living cells."
    ([w h] (create-world2 w h #{}))
    ([w h living-cells]
     (for [y (range w)]
       (for [x (range h)]
         (if (contains? living-cells [y x]) :on :off)))))

  (def create-world
    "Creates rectangular world with the specified width and height.
  Optionally takes coordinates of living cells."
    (fn self
      ([w h] (self w h #{}))
      ([w h living-cells]
       (for [y (range w)]
         (for [x (range h)]
           (if (contains? living-cells [y x]) :on :off))))))


  (create-world2 4 4)
  (create-world2 4 4 #{[0 0], [1 1], [2 2], [3 3]})

  (first #{[0 0], [1 1], [2 2], [3 3]})
  (contains? #{[0 0], [1 1], [2 2], [3 3]} [ 1])

  (defn neighbours
    "Determines all the neighbours of a given coordinate"
    [[x y]]
    (for [dx [-1 0 1] dy [-1 0 1] :when (not= 0 dx dy)]
      [(+ dx x) (+ dy y)]))

  (neighbours [1 1])

  (frequencies (mapcat neighbors #{[1 1] [1 0]}))

  (def a (create-world2 4 4 #{[0 0], [1 1], [2 2], [3 3]}))

  ;; (a [0 1])

  (def a (make-board))

  (mapcat neighbors a)


  )