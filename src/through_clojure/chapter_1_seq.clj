(ns through-clojure.chapter-1-seq)

;; Seqs
;; It is better to have 100 functions operate on one data abstraction than 10
;; functions on 10 data structures.



;; List
;; Empty list
()




(list)




;; Not list -> function call
(1 2 3)





(list 1 2 3)





(list* [1 2 3])




;; List of three elements
'(1 2 3)




;; Vectors
[1 2 3 :a]



(vector 1 2 3 :a)





(vec '(1 2 3 :a))






;; Maps
;; Ordinay hash map
{:dog "Bobby" :toy "Stick"}




(hash-map :dog "Bobby" :toy "Stick")





;; Array map
(array-map :dog "Bobby" :toy "Stick")





;; Sorted map
(sorted-map :dog "Bobby" :toy "Stick")


;; Sets
;; Ordinar set
#{1 2 3}





(hash-set 1 2 3)





(set [1 2 3])






;; Falles if duplicates
#{1 2 3 3}





;; Save for duplicates
(hash-set 1 2 3 3)






(set [1 2 3 3])


;; Partitions of collections:
;;  1. Sequential

(sequential? '(1 2 3))







(sequential? [1 2 3])








;;  2. Maps
(map? {:dog "Bobby"})





(associative? {:dog "Bobby"})







(associative? [1 2 3])





;;  3. Sets
(set? #{:a :b})



;; Vector as function
([1 2 3] 0)


(get [1 2 3] 0)


(nth [1 2 3] 0)




;; Map as function
({:one 1 :two 2} :one)







;; Set as function
(#{:one :two} :two)


(#{:one :two} :three)






;; Equality
;; Always true if belongs to one partition
(= '(1 2 3) '(1 2 3)) ; =>
(= '(1 2 3) [1 2 3])







;; Either false
(= '(1 2 3) #{1 2 3})






;; Order doesn't matters
(= #{1 2 3} #{3 2 1})






(= {:a 1 :b 2} {:b 2 :a 1})







;; Going into seq.
;; Seq - is basicaly an class that realizes interface called ISeq
;; It requires only two functions:
(first [1 2 3])
(rest [1 2 3])

;; And has a constructor for creating seq
;; From list
(seq '(1 2 3))





;; From vector
(seq [1 2 3])





;; From map
(seq {:a 1 :b 2})





;; From set
(seq #{:a :b :c})




;; From nil
(seq nil)





;; From empty collection
(seq '())





;; They all have different types.
(class (seq '(1 2 3)))



(class (seq [1 2 3]))



(class (seq {:a 1 :b 2}))

;; Mostly all work on collection done using seqs.
;; All functions working with collection always return seqs.
;; Before processing any collection seq is called.
(map inc [1 2 3])



(seq? *1)




;; Functions with examples
;; Make seq longer

(cons 1 '(2 3))


(cons 1 [2 3])


(cons [:a 1] {:b 2})


;; High level cons is conj


(conj '(:first :second) :third)
(conj [:first :second] :third)
(conj #{:first :second} :third)
(conj {:b 2} [:a 1])


;; Clojure has function map
(map inc (range 10))

;; Let's define our own map
(defn map-helper [f coll acc]
  (if (empty? coll)
    (reverse acc)
    (recur f (rest coll) (cons (f (first coll)) acc))))

(defn map' [f coll]
  (map-helper f coll ()))

;; Let's try it
(map' inc (range 10))

;; All is good but (reverse ...) isn't a good choice.
;; So conj we'll help us.

(defn map-helper2 [f coll acc]
  (if (empty? coll)
    acc
    (recur f (rest coll) (conj acc (f (first coll))))))

(defn map'2 [f coll]
  (map-helper2 f coll []))

(map'2 inc (range 10))


;; Look at basic functions

(def ten-numbers (range 10))


(count ten-numbers)


(first ten-numbers)

(ffirst [ten-numbers])

(second ten-numbers)


(rest ten-numbers)

(next ten-numbers)


(rest [])

(next [])

(= (next []) (seq (rest [])))


(last ten-numbers)

(butlast ten-numbers)


(empty? ten-numbers)

(empty? [])

(empty? nil)



;; All seqs in clojure is lazy

(def xs (map (fn [x] (print x "") x)
             (range 100)))

(first xs)


(first xs)


(second xs)



(nth xs 33)







;; Not lazy


(defn fib-fn [a b n]
  (if (zero? n)
    '()
    (conj (fib-fn b (+ a b) (dec n))
          (+ a b))))

(defn fib [n]
  (fib-fn 0M 1M n))

(dorun (fib 10000))






;; Lazy


(defn fib-fn-lazy [a b n]
  (lazy-seq
   (if (zero? n)
     nil
     (cons (+ a b) (fib-fn-lazy b (+ a b) (dec n))))))

(defn fib-lazy [n]
  (fib-fn-lazy 0M 1M n))


(dorun (fib-lazy 10000))





;; Creating seqs


(range 0 31 3)





(repeat 4 "Hi")






(iterate inc 1)







(take 10
      (iterate inc 1))








(defn mult-by-two [a] (* 2 a))
(take 10
      (iterate mult-by-two 1))





(take 10
      (cycle (range 5)))







(shuffle (range 10))




(take 10
      (interleave (cycle ["a" "b"])
                  (iterate inc 1)))





(take 10
      (interpose "," (cycle ["a" "b" "c"])))





(take 10
      (partition 2 (iterate inc 1)))





(take 10
      (partition 2 1 (iterate inc 1)))






;; Write a code that returns line like this:
;; "Aa, Bb, Cc, Dd,..., Zz

(defn join' [sep coll]
  (apply str (interpose sep coll)))



(let [small (map char (range 97 123))
      big   (map char (range 65 98))]
  (join' ", "
         (map #(apply str %)
              (partition 2 (interleave big small)))))







(let [small (map char (range 97 123))
      big   (map char (range 65 98))]
  (->> (interleave big small)
       (partition 2)
       (map #(apply str %))
       (join' ", ")))






;; Filtering seqs

(def naturals (iterate inc 0))


(take 10
      (filter even? naturals))





(filterv even? (take 10 naturals))





(take-while #(< % 31)
            (filter odd? naturals))






(take 10
      (remove odd? naturals))






(take 10
      (drop-while #(< % 31)
                  (filter odd? naturals)))







(distinct [1 2 3 1 1])





(flatten [1 2 3 [4 5 [6 7 [8 9]]]])







;; Seq predicates




(every? even?
        (filter even? (range 20)))







(some #{1 2} (shuffle (range 20)))




(contains? [4 3 2 1] 1)




(contains? [4 3 2 1] 4)




(contains? [4 3 2 1] 0)




(contains? {:a 1 :b 2} :a)






;; Transforming collections



(take 10
      (map float naturals))






(take 10
      (mapv float naturals))






(mapv float (take 10 naturals))








(reduce + (take 10 naturals))







(reduce-kv (fn [acc k v] (+ acc (* k v)))
           0
           [1 2 3 4 5])







(sort (reverse (take 10 naturals)))





(take 10
      (for [n naturals
            :when (even? n)]
        n))









(take 10
      (for [n naturals
            :while (even? n)]
        n))






(for [formt "ABCDEFG"
      rank (range 1 9)]
  (format "%c%d" formt rank))









;; Example
;; Write a function pos, that find position of element in collection.
;; It should
;;   - Work on any composite type returning indices corresponding to
;; some value
;;   - Return a numerical index for sequential collections or associated key for maps
;; and sets
;;   - Otherwise return nil



(defn pos [e coll]
  (let [cmp (if (map? coll)
              #(= (second %1) %2)
              #(= %1 %2))]
    (loop [s coll idx 0]
      (when (seq s)
        (if (cmp (first s) e)
          (if (map? coll)
            (ffirst s)
            idx)
          (recur (rest s) (inc idx)))))))




(pos 3 [:a 1 :b 2 :c 3 :d 4])


(pos :foo [:a 1 :b 2 :c 3 :d 4])


(pos 3 {:a 1 :b 2 :c 3 :d 4})


(pos 3 '(:a 1 :b 2 :c 3 :d 4))






;; Let's add ability to return on keys with specified values






[[id1 val1] [id2 val2] ...]




(defn index [coll]
  (cond
   (map? coll) (seq coll)
   (set? coll) (map vector coll coll)
   :else (map vector (iterate inc 0) coll)))





(index [:a 1 :b 2 :c 3 :d 4])


(index {:a 1 :b 2 :c 3 :d 4})


(index #{:a 1 :b 2 :c 3 :d 4})


(index '(:a 1 :b 2 :c 3 :d 4))



(defn pos [e coll]
  (for [[i v] (index coll) :when (= v e)] i))



(pos 3 [:a 1 :b 2 :c 3 :d 4])


(pos :foo [:a 1 :b 2 :c 3 :d 4])


(pos 3 {:a 1 :b 2 :c 3 :d 4})


(pos 3 '(:a 1 :b 3 :c 3 :d 4))


(pos 3 {:a 1 :b 3 :c 3 :d 4})






;; Reducers


(require '[clojure.string :as s])

(def cart [{:name "Tomato"
            :quantity 10
            :price 23}
           {:name "Potato"
            :quantity 12
            :price 10}
           {:name "Carrot"
            :quantity 4
            :price 15}])

(defn total [{pr :price q :quantity :as p}]
  (assoc p :total (* pr q)))




(map total cart)






(reduce +
        (map :total
             (map total cart)))






(->> cart
     (map total)
     (map :total)
     (reduce +))




(defn discount-10% [{t :total :as p}]
  (assoc p :total (- t (* 0.1 t))))







(defn total-cart [cart]
  (->> cart
       (map total)
       (map discount-10%)
       (map :total)
       (reduce +)))



(total-cart cart)



(do
  (println "classic:")
  (time (total-cart cart)))








(defn total-cart-transforming [cart]
  (reduce (fn [acc p]
            (+ acc
               (-> p
                   (total)
                   (discount-10%)
                   (:total))))
          0
          cart))



(do
  (println "transforming:")
  (time (total-cart-transforming cart)))






(defn generate-cart [n]
  (->> (fn [] {:name "Item"
              :price (rand-int 10)
              :quantity (rand-int 10)})
       (repeatedly)
       (take n)
       (vec)))


(count (generate-cart 10000))
(generate-cart 2)


;; 50 000 Items

(let [cart (generate-cart 5e4)]
  (println "classic:")
  (time (total-cart cart)))


(let [cart (generate-cart 5e4)]
  (println "transforming:")
  (time (total-cart-transforming cart)))


;; 5 000 000 items
(let [cart (generate-cart 5e6)]
  (println "classic:")
  (time (total-cart cart)))



(let [cart (generate-cart 5e6)]
  (println "transforming:")
  (time (total-cart-transforming cart)))




;; Introducing clojure reducers lib



(require '[clojure.core.reducers :as r])






(defn total-cart-reduced [cart]
  (->> cart
       (r/map total)
       (r/map discount-10%)
       (r/map :total)
       (reduce +)))


(let [cart (generate-cart 5e6)]
  (println "reduced:")
  (time (total-cart-reduced cart)))




;; Fork/Join



(defn total-cart-folded [cart]
  (->> cart
       (r/map total)
       (r/map discount-10%)
       (r/map :total)
       (r/fold +)))


(let [cart (generate-cart 5e6)]
  (println "reduced:")
  (time (total-cart-folded cart)))








;; Further reading
;;   - zipers
;;   - queues
;;   - reducers
