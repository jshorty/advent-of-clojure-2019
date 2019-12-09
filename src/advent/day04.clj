(ns advent.day04)

(def input-range (apply range (map read-string (clojure.string/split (slurp "resources/day04.txt") #"-"))))

(defn digits [n]
  (map read-string (clojure.string/split (str n) #"")))

(defn password-valid-for-part-1? [n]
  (and (= (digits n) (sort (digits n))) ; Digits must be in increasing order
       (not= (count (digits n)) (count (set (digits n)))))) ; Two digits must be the same (and adjacent, per rule 1)

(defn valid-password-count-part-1 []; Answer Part 1
  (count (filter password-valid-for-part-1? input-range)))

(defn password-valid-for-part-2? [n]
  (and (password-valid-for-part-1? n)
       (some #(= 2 (count %)) (partition-by identity (digits n)))))

(defn valid-password-count-part-2 [] ; Answer Part 2
  (count (filter #(password-valid-for-part-2? %) input-range)))
