(ns game-of-life.life-test
    (:require [clojure.test :refer :all]
              [game-of-life.life :as life]
              [game-of-life.util :as util]))

(defn- generate-dead-cell-block 
  []
  (for [x [-1 0 1]
        y [1 0 -1]]
    (util/create-dead-cell x y)))

(defn- generate-live-cell-block
  []
  (for [x [-1 0 1]
        y [1 0 -1]]
    (util/create-live-cell x y)))

(deftest test-tick
  (testing "all dead seed"
    (let [start-world {:cells (generate-dead-cell-block)
                       :time  0}
          end-world   (life/tick start-world)]
      (is (= (:cells start-world) (:cells end-world)))))
  (testing "all live seed"
    (let [start-world {:cells (generate-live-cell-block)
                       :time  0}
          world-time-one (life/tick start-world)
          world-time-two (life/tick world-time-one)]
      (is (= (:cells world-time-one) [{:x -1, :y 1, :alive? true}
                                      {:x -1, :y 0, :alive? false}
                                      {:x -1, :y -1, :alive? true}
                                      {:x 0, :y 1, :alive? false}
                                      {:x 0, :y 0, :alive? false}
                                      {:x 0, :y -1, :alive? false}
                                      {:x 1, :y 1, :alive? true}
                                      {:x 1, :y 0, :alive? false}
                                      {:x 1, :y -1, :alive? true}]))
      (is (= (:cells world-time-two) (generate-dead-cell-block))))))

