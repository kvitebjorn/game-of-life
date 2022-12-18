(ns game-of-life.life 
  (:require [game-of-life.rules :as rules]
            [game-of-life.util :as util]))

(defn tick
  [world]
  (let [cells (:cells world)
        time  (:time world)
        new-cells (map #(if (or (rules/survives? % cells) 
                                (rules/regenerates? % cells))
                          (util/create-live-cell (:x %) (:y %))
                          (util/create-dead-cell (:x %) (:y %))) cells)
        new-time (inc time)]
  {:cells new-cells :time new-time}))
