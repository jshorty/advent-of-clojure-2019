(ns advent.day03)

; Vector here meaning (dx, dy)

(defn all-pts-from-vector ; "vector" here meaning something like "R10", conventionally (dx = 10, dy = 0)
  "Returns all 2D points a vector intersects, including the destination."
  [origin, vector]
  (let [distance (read-string (apply str (rest vector))), direction (str (first vector))]
    (cond
      (= direction "R") (letfn [(next-pt [pt] [(inc (first pt)), (last pt)])]
                          (take distance (rest (iterate next-pt origin))))
      (= direction "L") (letfn [(next-pt [pt] [(dec (first pt)), (last pt)])]
                          (take distance (rest (iterate next-pt origin))))
      (= direction "U") (letfn [(next-pt [pt] [(first pt), (inc (last pt))])]
                          (take distance (rest (iterate next-pt origin))))
      (= direction "D") (letfn [(next-pt [pt] [(first pt), (dec (last pt))])]
                          (take distance (rest (iterate next-pt origin)))))))

(defn wire-vectors-from-file
  "Reads a file with each row containing comma-separated vectors for a wire"
  []
  (map #(clojure.string/split % #",") (clojure.string/split-lines (slurp "resources/day03.txt"))))

(defn all-pts-for-wire
  "Returns all 2D points accessed by a series of vectors, beginning from origin of (0, 0)"
  [wire]
  (let [points [[0, 0]]]
    (reduce #(concat %1 (all-pts-from-vector (last %1) %2)) points wire)))

(defn wire-intersections
  "Returns all 2D points where all wires found in a file intersect, exluding the origin (0, 0)"
  []
  (vec (apply clojure.set/intersection (map #(set (rest (all-pts-for-wire %))) (wire-vectors-from-file)))))

(defn manhattan-distance-to-closest-wire-intersection ; Answer Part 1
  "Returns the Manhattan distance from origin to the closest wire intersection"
  []
  (apply min (map #(+ (Math/abs (first %)) (Math/abs (last %))) (wire-intersections))))

(defn manhattan-earliest-wire-intersection ; Answer Part 2
  "Returns the combined Manhattan distance traveled by all wires before the *first* intersection from origin."
  []
  (let [wires (map all-pts-for-wire (wire-vectors-from-file))]
    (apply min (map #(+ (Math/abs (.indexOf (first wires) %)) (Math/abs (.indexOf (last wires) %))) (wire-intersections)))))
