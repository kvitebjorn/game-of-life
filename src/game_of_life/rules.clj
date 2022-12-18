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
  [cell num-alive-neighbors]
  (if (:alive? cell)
    (meets-survival-reqs? num-alive-neighbors)
    false))

(defn regenerates?
  [cell num-alive-neighbors]
  (if (:alive? cell)
    false
    (meets-regeneration-reqs? num-alive-neighbors)))

(defn is-alive-predicate
  [cell num-alive-neighbors]
       (or (survives?    cell num-alive-neighbors)
           (regenerates? cell num-alive-neighbors)))

(defn is-alive?
  [cell cells]
  (->> (util/get-neighbors cell cells)
       (filter :alive?)
       (count)
       (is-alive-predicate cell)))
