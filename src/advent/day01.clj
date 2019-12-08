(ns advent.day01)

(defn fuel-required-for-mass
  "Given a mass, returns the mass of its fuel requirements"
  [mass]
  (int (max (- (Math/floor (/ mass 3)) 2) 0)))

(defn numbers-from-file
  "Reads a list of newline-separated numbers from a file as a LazySeq"
  [path]
  (map read-string (clojure.string/split-lines (slurp path))))

(defn fuel-required-for-mass-of-modules ; Answer Part 1
  "Returns the fuel requirement for the mass of the spacecraft's modules (excluding the mass of this fuel)"
  []
  (reduce + (map fuel-required-for-mass (numbers-from-file "resources/day01.txt"))))

(defn fuel-required-for-mass-of-module-and-fuel
  "Returns the fuel requirement for the mass of a module (including the mass of the fuel requirement itself)"
  [mass]
  (reduce + (take-while #(not= 0 %) (drop 1 (iterate fuel-required-for-mass mass)))))

(defn fuel-required-for-spacecraft ; Answer part 2
  "Returns the total fuel requirement for the spacecraft"
  []
  (reduce + (map fuel-required-for-mass-of-module-and-fuel (numbers-from-file "resources/day01.txt"))))
