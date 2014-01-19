(ns substitution.core
  (:use fleet))



(defmacro fileet
  "Convinient way to define test templates"
  [args filename]
  `(fleet ~args (slurp (str "./" ~filename)) {:escaping :xml}))


(defn write-file
  "Writes a value to a file"
  [value out-file]
  (spit out-file "" :append false)
  (with-open [out-data (clojure.java.io/writer out-file)]
      (.write out-data (str value))))

(defn read-file [in-file]
  (with-open [rdr (clojure.java.io/reader in-file)]
    (reduce conj [] (line-seq rdr))))



;(use 'clojure.string)
(use '[clojure.string :as str :only [join trim split]])


(defn load-map [file] (into {} (filter #(= (count %) 2) (map #(split % #"=") (map trim (filter #(not (= % "")) (read-file file)))))))



(defn search-replace [sub-map file] (let [k (keys sub-map)
	   replace-one (fn [[k v] line] (clojure.string/replace line (re-pattern k) v))
	   replace-all (fn [sub-map line] (str (reduce #(replace-one %2 %1) line sub-map) "\n"))]
	(apply str (map #(replace-all sub-map %) (read-file file)))))

	
(defn -mainold
  "I don't do a whole lot."
  [templatefile instancefile envfile env]
  (let [sub-map (load-map envfile)]
    (println sub-map)
    (write-file (search-replace sub-map templatefile) instancefile)))



(defn -mainold2
  "I don't do a whole lot."
  [templatefile instancefile envfile env]
  (let [sub-map (read-string (apply str (read-file envfile)))]
    (println sub-map)
    (write-file ((fileet [node title] templatefile) sub-map env) instancefile)))
    
(defn -main
  "I don't do a whole lot."
  [templatefile instancefile envfile env]
  (let [sub-map (read-string (apply str (read-file envfile)))]
    (println sub-map)
    (write-file ((fleet [? env] (slurp (str "./" templatefile)) {:escaping :xml}) sub-map env) instancefile)))

