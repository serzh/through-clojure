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
(list* '(1 2 3))

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
