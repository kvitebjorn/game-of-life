(ns game-of-life.core
  (:gen-class) 
  (:require [game-of-life.life :as life]
            [game-of-life.seeds :as seeds]
            [quil.core :as q]))

(def WORLD (atom nil))
(def HEIGHT (* (q/screen-height) 0.95))
(def WIDTH  (* (q/screen-width) 1))
(def HEIGHT-TRANSLATE (* HEIGHT 0.5))
(def WIDTH-TRANSLATE  (* WIDTH 0.5))

(defn- continue?
  [world]
  (some :alive? (:cells world)))

(defn render-world-setup []
  (q/background 255)
  (q/fill 0))

(defn render-world-draw 
  []
  (q/background 255)
  (q/fill 0)
  (let [cells-to-draw (filter :alive? (:cells @WORLD))]
    (doseq [{x :x y :y} cells-to-draw]
      (q/rect (+ WIDTH-TRANSLATE  (*  16 x)) 
              (+ HEIGHT-TRANSLATE (* -16 y)) 
              14 14))))

(defn- play
  [seed]
  (reset! WORLD seed)
  (q/sketch
   :title "Conway's Game of Life"
   :host "Game of Life"
   :size [WIDTH HEIGHT]
   :setup #'render-world-setup
   :draw #'render-world-draw)
  (Thread/sleep 5000)
  (while (continue? @WORLD)
    (reset! WORLD (life/tick @WORLD))
    (Thread/sleep 100)))

(defn -main
  [& args]
  (let [cells seeds/pentomino
        seed {:cells cells
              :time 0}]
    (play seed)))
