(ns net.matlux.common-tools
 (:refer-clojure :exclude [name parents])
 (:require [clojure.java.io :as io]
            [clojure.zip :as zip]
            [clojure.string :as string]
            [clojure.java.shell :as sh]
            [clojure.data.json :as json])
 (:use fs.core)
 (:use fleet)
 (:import [java.io File FilenameFilter])
 (:import [java.io File])
 (:import java.util.zip.ZipInputStream)
 (:import java.util.zip.ZipEntry)
 (:import java.io.FileOutputStream))

(use '[clojure.java.shell :only [sh]])

(defn download-binary [to from]
  (with-open [in  (io/input-stream (io/as-url from))]
    (io/copy in (File. to))))
        
(defn write-file
  "Writes a value to a file"
  [value out-file]
  (spit out-file "" :append false)
  (with-open [out-data (clojure.java.io/writer out-file)]
      (.write out-data (str value))))

(defn read-file [in-file]
  (with-open [rdr (clojure.java.io/reader in-file)]
    (reduce conj [] (line-seq rdr))))

(defn search-replace [sub-map file] (let [k (keys sub-map)
	   replace-one (fn [[k v] line] (clojure.string/replace line (re-pattern k) v))
	   replace-all (fn [sub-map line] (str (reduce #(replace-one %2 %1) line sub-map) "\n"))]
	(apply str (map #(replace-all sub-map %) (read-file file)))))    

(defn search-replace! [sub-map file]
  (write-file (search-replace sub-map file) file))
      
(defn directory-path [file-path]
    (let [parent-seq (fn [file] (let [dirs-file (clojure.string/split file #"/")] 
                                    (take (dec (count dirs-file)) dirs-file)))
          parent-path (parent-seq file-path)]
        (apply str (map #(str %1 %2) parent-path (conj (vec (take (dec (count parent-path)) (repeatedly #(str "/")))) "")))))
        

(defn perl-split [s]
    (split (string/triml s) #"\s+"))
       
(defn unzip-entry [^ZipInputStream zs ^ZipEntry ze target-path]
                    (let [filesize (.getSize ze)
                          filename (.getName ze)
                          target-filename (str target-path "/" filename)
                          dir (directory-path target-filename)
                          buffer  (byte-array filesize)]
                          (if (.isDirectory ze)
                            (mkdir target-filename)
                            (with-open [output (FileOutputStream. target-filename)]
                                (println "extracting:" target-filename)
                                (io/copy zs output)))))
       

       
(defn unzip [zip-file target-path]
    (when (not (directory? target-path))
        (do (println "mkdir" target-path)
            (mkdirs target-path)))
    (with-open [zs (ZipInputStream. (io/input-stream zip-file))]
        (loop [ze (.getNextEntry zs)]
            (when (not (nil? ze))
                (do
                    (unzip-entry zs ze target-path)
                    (recur (.getNextEntry zs)))))))

(defn instantiate-template-from-clojure-file
  "instantiate a Fleet template."
  [^String templatefile ^String instancefile ^String envfile ^String env]
  (let [sub-map (read-string (apply str (read-file envfile)))]
    (println sub-map)
    (write-file ((fleet [? env] (slurp (str templatefile)) {:escaping :xml}) sub-map env) instancefile)))

(defmacro --> [m firstkey & keys]
  (let [a (map #(list 'get %) keys)]
    `(-> (~m ~firstkey ) ~@a)))


(defn merge-map [^java.util.Map sub-map1 ^java.util.Map sub-map2]
  (merge sub-map1 sub-map2))

(defn load-json-and-merge [^String envfile ^java.util.Map sub-map]
  (merge (json/read-str  (apply str (read-file envfile))) sub-map))

(defn load-json [^String jsonfile]
  (json/read-str  (apply str (read-file jsonfile))))  ;; :key-fn keyword


(defn write-str-json [^java.util.Map sub-map]
  (merge (json/write-str sub-map )))  ;; :key-fn #(.toUpperCase %)

(defn instantiate-template-from-file
  "instantiate a Fleet template."
  [^String templatefile ^String instancefile ^String envfile ^java.util.Map sub-map2]
  (let [sub-map (merge (json/read-str  (apply str (read-file envfile))) sub-map2)]  ;; :key-fn keyword
    (println "Instanciating" templatefile "into" instancefile)
    (write-file ((fleet [?] (slurp (str templatefile)) {:escaping :xml}) sub-map) instancefile)
    sub-map
    ))
                    
(defn instantiate-template
  "instantiate a Fleet template."
  [^String templatefile ^String instancefile ^java.util.Map sub-map]
    (println "Instanciating" templatefile "into" instancefile)
    (write-file ((fleet [?] (slurp (str templatefile)) {:escaping :xml}) (into {} sub-map)) instancefile))

(defn load-map [file] (into {} (filter #(= (count %) 2) (map #(string/split % #"=") (map string/trim (filter #(not (= % "")) (read-file file)))))))

(defn find-search-replace
  ([path file-pattern pattern replace-with]
    (find-search-replace path file-pattern pattern replace-with (partial find-all-files ["target" ".idea" ".svn"])))
  ([path file-pattern pattern replace-with search-fn]
    (doseq [file (search-fn path (re-pattern file-pattern))]
      (let [absfile (.getAbsolutePath file)]
        (println "find and replace in " absfile)
      (search-replace! {pattern replace-with} absfile)))))