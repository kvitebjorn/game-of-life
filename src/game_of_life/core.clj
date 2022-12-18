(ns game-of-life.core
  (:gen-class) 
  (:require [game-of-life.life :as life]
            [game-of-life.seeds :as seeds]
            [game-of-life.util :as util]
            [quil.core :as q]))

(def WORLD (atom nil))
(def HEIGHT (* (q/screen-height) 0.95))
(def WIDTH  (* (q/screen-width) 1))
(def HEIGHT-TRANSLATE (* HEIGHT 0.5))
(def WIDTH-TRANSLATE  (* WIDTH 0.5))

(defn- continue?
  [world]
  (some :alive? (:cells world)))

(defn- grow
  [world]
  (let [cells (:cells world)
        cells-alive (filter :alive? cells)
        cells-alive-neighbors (flatten (map #(util/get-neighbors % cells) cells-alive))
        new-cells (distinct (concat cells cells-alive-neighbors))]
    (assoc world :cells new-cells)))

(defn render-world-setup []
  (q/background 255)
  (q/fill 0))

(defn render-world-update
  [])

;; seeds are defined on traditional x,y plane with (0,0) origin
;; so for rendering purposes, we want to start at the center of the screen etc
(defn center-and-mirror
  [cells]
  (map #(assoc % :x (+ WIDTH-TRANSLATE (* 16 (:x %)))
                 :y (+ HEIGHT-TRANSLATE (* -16 (:y %)))) cells))

(defn render-world-draw 
  []
  (q/background 255)
  (q/fill 0)
  (let [cells-to-draw (center-and-mirror (filter :alive? (:cells @WORLD)))]
    (doseq [{x :x y :y} cells-to-draw]
      (q/rect x y 14 14))))

(defn- play
  [seed]
  (reset! WORLD seed)
  (q/sketch
   :title "Conway's Game of Life"
   :host "Game of Life"
   :size [WIDTH HEIGHT]
   :setup #'render-world-setup
   :draw #'render-world-draw
   :update #'render-world-update)
  (Thread/sleep 5000)
  (while (continue? @WORLD)
    (reset! WORLD (life/tick (grow @WORLD)))
    (Thread/sleep 100)))

(defn -main
  [& args]
  (let [cells seeds/pentomino
        seed {:cells cells
              :time 0}]
    (play seed)))
