(ns game-of-life.rules-test
    (:require [clojure.test :refer :all]
              [game-of-life.rules :as rules]
              [game-of-life.util :as util]))

(deftest test-survives? 
  (testing "underpopoulation, no neighbors"
    (let [cell (util/create-live-cell 0 0)
          cells []]
      (is (= false (rules/survives? cell cells)))))
  (testing "underpopulation, one neighbor"
    (let [cell (util/create-live-cell 0 0)
          other-cell (util/create-live-cell 0 1)
          cells [cell other-cell]]
      (is (= false (rules/survives? cell cells)))))
  (testing "survives, two neighbors"
    (let [cell (util/create-live-cell 0 0)
          neighbor-one (util/create-live-cell 0 1)
          neighbor-two (util/create-live-cell -1 -1)
          cells [cell neighbor-one neighbor-two]]
      (is (= true (rules/survives? cell cells)))))
  (testing "underpopulation, two neighbors but one is dead"
    (let [cell (util/create-live-cell 0 0)
          neighbor-cell (util/create-live-cell 0 1)
          dead-neighbor-cell (util/create-dead-cell -1 -1)
          cells [cell neighbor-cell dead-neighbor-cell]]
      (is (= false (rules/survives? cell cells)))))
  (testing "survives, three neighbors"
    (let [cell (util/create-live-cell 0 0)
          neighbor-one (util/create-live-cell 0 1)
          neighbor-two (util/create-live-cell 1 0)
          neighbor-three (util/create-live-cell -1 1)
          cells [cell
                 neighbor-one
                 neighbor-two
                 neighbor-three]]
      (is (= true (rules/survives? cell cells)))))
  (testing "overpopulation, four neighbors"
    (let [cell (util/create-live-cell 0 0)
          neighbor-one (util/create-live-cell 0 1)
          neighbor-two (util/create-live-cell 1 0)
          neighbor-three (util/create-live-cell -1 1)
          neighbor-four (util/create-live-cell 1 1)
          cells [cell
                 neighbor-one
                 neighbor-two
                 neighbor-three
                 neighbor-four]]
      (is (= false (rules/survives? cell cells))))))
