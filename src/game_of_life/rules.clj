(ns game-of-life.rules 
  (:require [game-of-life.util :as util]))

(def UNDERPOPULATION 2)
(def OVERPOPULATION 3)
(def REPRODUCTION 3)

(defn- meets-survival-reqs?
  [num-alive-neighbors]
  (and (>= num-alive-neighbors UNDERPOPULATION)
       (<= num-alive-neighbors OVERPOPULATION)))

(defn- meets-regeneration-reqs?
  [num-alive-neighbors]
  (= REPRODUCTION num-alive-neighbors))

(defn survives? 
  [cell cells]
  (if (:alive? cell)
    (->> (util/get-neighbors cell cells)
        (filter :alive?)
        (count)
        (meets-survival-reqs?))
    false))

(defn regenerates?
  [cell cells]
  (if (:alive? cell)
    false
    (->> (util/get-neighbors cell cells)
         (filter :alive?)
         (count)
         (meets-regeneration-reqs?))))
