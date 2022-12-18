(ns game-of-life.life 
  (:require [game-of-life.rules :as rules]
            [game-of-life.util :as util]))

(defn- grow
  [cells]
  (let [cells-alive (filter :alive? cells)
        cells-alive-neighbors (flatten (pmap #(util/get-neighbors % cells) cells-alive))]
    (distinct (into cells cells-alive-neighbors))))

(defn tick
  [world]
  (let [cells (grow (:cells world))
        new-cells (pmap #(if (rules/is-alive? % cells)
                          (util/create-live-cell (:x %) (:y %))
                          (util/create-dead-cell (:x %) (:y %))) cells)
        new-time (inc (:time world))]
  {:cells new-cells :time new-time}))
