(ns game-of-life.seeds 
  (:require [game-of-life.util :as util]))

(def blinker 
  [(util/create-live-cell -1 0)
   (util/create-live-cell 0 0)
   (util/create-live-cell 1 0)])

(def block
  [(util/create-live-cell 0 0)
   (util/create-live-cell 0 1)
   (util/create-live-cell 1 1)
   (util/create-live-cell 1 0)])

(def pentomino
  [(util/create-dead-cell 0 0)
   (util/create-live-cell 1 0)
   (util/create-live-cell 2 0)
   (util/create-live-cell 0 -1)
   (util/create-live-cell 1 -1)
   (util/create-dead-cell 2 -1)
   (util/create-dead-cell 0 -2)
   (util/create-live-cell 1 -2)
   (util/create-dead-cell 2 -2)])
