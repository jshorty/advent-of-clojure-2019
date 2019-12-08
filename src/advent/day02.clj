(ns advent.day02)

(defn intcode-op
  "Applies one step to an Intcode program"
  [intcode, op, pos1, pos2, pos3]
  (if (= op 99)
    intcode
    (if (= op 1)
      (assoc intcode pos3 (+ (intcode pos1) (intcode pos2)))
      (assoc intcode pos3 (* (intcode pos1) (intcode pos2))))))

(defn run-intcode
  "Returns the result of an Intcode program"
  ([intcode] (apply run-intcode [intcode 0]))
  ([intcode, step]
    (let [args (take 4 (drop (* 4 step) intcode))]
      (if (or (= 99 (first args)) (< (count args) 4))
        intcode
        (run-intcode (apply intcode-op intcode args) (inc step))))))

(defn intcode-from-file
  "Reads a list of comma-separated integers from a file as a Vector"
  [path]
  (apply vector (map #(int (read-string %)) (clojure.string/split (slurp path) #","))))

(defn repair-program
  "Fixes a broken Intcode program"
  [intcode]
  (assoc intcode 1 12 2 2))

(defn repair-and-run-intcode-from-file ; Answer Part 1
  []
  (first (-> "resources/day02.txt" intcode-from-file repair-program run-intcode)))

(defn run-with-inputs
  [intcode noun verb]
  (first (run-intcode (assoc intcode 1 noun 2 verb))))

(defn solve-for-output ; Answer Part 2: (solve-for-output 19690720 ((-> "resources/day02.txt" intcode-from-file))
  "Tries to solve the inputs for a given output for all combinations up to 100, 100, and transforms the result"
  [output, intcode]
  (loop [noun 0 verb 0]
    (println (str "Trying with noun: " noun " verb: " verb))
    (if (= 19690720 (run-with-inputs intcode noun verb))
      (+ (* 100 noun) verb)
      (recur (if (= 100 verb) (inc noun) noun) (if (= 100 verb) 0 (inc verb))))))
