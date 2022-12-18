(ns game-of-life.util)

;; (x,y) is the bottom left corner of the cell
;; this is probably not standard interpretation, it is just my dumb brain
(defn create-live-cell
  [x y]
  {:x x
   :y y
   :alive? true})

(defn create-dead-cell
  [x y]
  {:x x
   :y y
   :alive? false})

(defn find-first
  [f coll]
  (reduce #(when (f %2) (reduced %2)) nil coll))

;; TODO: probably a smarter concurrent way to do this not at 3:59am
(defn get-neighbors
  [cell cells]
  (let [x (:x cell)
        y (:y cell)
        up (or (find-first #(and (= x (:x %)) (= (inc y) (:y %))) cells) 
               (create-dead-cell x (inc y)))
        down (or (find-first #(and (= x (:x %)) (= (dec y) (:y %))) cells)
                 (create-dead-cell x (dec y)))
        right (or (find-first #(and (= (inc x) (:x %)) (= y (:y %))) cells)
                  (create-dead-cell (inc x) y))
        left (or (find-first #(and (= (dec x) (:x %)) (= y (:y %))) cells)
                 (create-dead-cell (dec x) y))
        ur (or (find-first #(and (= (inc x) (:x %)) (= (inc y) (:y %))) cells)
               (create-dead-cell (inc x) (inc y)))
        ul (or (find-first #(and (= (dec x) (:x %)) (= (inc y) (:y %))) cells)
               (create-dead-cell (dec x) (inc y)))
        lr (or (find-first #(and (= (inc x) (:x %)) (= (dec y) (:y %))) cells)
               (create-dead-cell (inc x) (dec y)))
        ll (or (find-first #(and (= (dec x) (:x %)) (= (dec y) (:y %))) cells)
               (create-dead-cell (dec x) (dec y)))]
    [up down right left ur ul lr ll]))
