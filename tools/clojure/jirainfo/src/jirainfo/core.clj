(ns jirainfo.core
	(:use [clojure.string :only (split triml trim trim-newline)])
)
(require '[clojure.data.json :as json])
(require '[clj-http.lite.client :as client])
(defn call []
    (client/get "http://myhost.net/jira/rest/api/2/issue/DEVPROG-1330" {:basic-auth ["MYUSER" "MYPASSWORD"]}))
	
(defn retrieve-jira [jira]
    ;;(println "retrieving:" jira)
    (client/get (str "http://myhost.net/jira/rest/api/2/issue/" jira) {:basic-auth ["MYUSER" "MYPASSWORD"]}))

(def retrieve-jira-memoized (memoize retrieve-jira))
	
(defn retrieve-jira-msg [jira]
    (let [body (json/read-str (:body (retrieve-jira-memoized jira)))]
		(-> body (get "fields") (get "summary"))))
(defn retrieve-jira-status [jira]
    (let [body (json/read-str (:body (retrieve-jira-memoized jira)))]
		(-> body (get "fields") (get "status") (get "name"))))

(defn retrieve-jira-components [jira]
    (let [body (json/read-str (:body (retrieve-jira-memoized jira)))
		  components (-> body (get "fields") (get "components"))]
		(apply str (interpose "," (map #(get % "name") components)))))
;; (-> (json/read-str (:body (retrieve-jira-memoized "DEVPROG-1330"))) (get "fields") (get "status") (get "name"))
		
		
(defn write-file
  "Writes a value to a file"
  [value out-file]
  (spit out-file "" :append false)
  (with-open [out-data (clojure.java.io/writer out-file)]
      (.write out-data (str value))))

(defn read-file [in-file]
  (with-open [rdr (clojure.java.io/reader in-file)]
    (reduce conj [] (line-seq rdr))))
	
(defn perl-split [s]
    (split (triml s) #"\s+"))
	
;;(defn log-split-alt [line]
;;    (map (comp triml trim-newline) (rest (re-find #"(DEVPROG-\d+)\s+(.*)" line))))
	
(defn log-split [line]
    (let [[jira & msg] (split (trim line) #"\s+")
		  jira-found? #(re-find #"DEVPROG-\d+" %)
		  jira-found (jira-found? jira)
		  msg (trim (apply str (map #(apply str % " ") msg)))
		  msg2 (if jira-found msg line)]
		[jira-found msg2]))
	
(defn parse-log-debug [log]
	[(nth (map log-split log) 14)])	
	
(defn parse-log [log]
	(map log-split log))
	
(defn substitute-jira-in-log-line [[jira msg]]
	;;(println "substitute-jira-in-log-line:" jira)
	{:jira jira :title (retrieve-jira-msg jira) :msg msg :status (retrieve-jira-status jira) :components (retrieve-jira-components jira)})
	
(defn substitute-jira [parsed-log]
	(map substitute-jira-in-log-line parsed-log))	

(defn export-csv [log] (->> log parse-log substitute-jira (map #(str (:jira %) "\t" (:status %) "\t" (:components %) "\t" (:title %) "\t" (:msg %) "\n")) (apply str)))
	
(defn -main
  "I don't do a whole lot."
  []
  (println "starting!\n")
  (println (export-csv (read-file "svnlog.txt"))))
