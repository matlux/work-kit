(ns jirainfo.issuesreport
	(:use [clojure.string :only (split triml trim trim-newline)]))


(require '[clojure.data.json :as json])
(require '[clj-http.lite.client :as client])

(defn call []
    (client/get "http://myhost.net/jira/rest/api/2/issue/DEVPROG-1330" {:basic-auth ["MYUSER" "MYPASSWORD"]}))

(defn slurp-jira [jira]
    ;;(println "retrieving:" jira)
    (try
        (client/get (str "http://myhost.net/jira/rest/api/2/issue/" jira) {:basic-auth ["MYUSER" "MYPASSWORD"]})
    (catch Throwable e ;;(str "caught exception: " (.getMessage e))
    	{:body (str "{\"result\" \"not-found\", \"jira\" \"" jira "\" }")})
    ))

(def retrieve-jira-memoized (memoize slurp-jira))

(defn retrieve-jira [jira]
    (let [body (json/read-str (:body (retrieve-jira-memoized jira)))]
      body))

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
		jira-found))

(defn parse-log [log]
	(map log-split log))

(defn substitute-jira-in-log-line [jira]
    ;;(println "substitute-jira-in-log-line:" jira)
	(let [body (retrieve-jira jira)]
		{	:jira jira
			:summary (-> body (get "fields") (get "summary"))
			:assignee (-> body (get "fields") (get "assignee") (get "displayName"))
			:status (-> body (get "fields") (get "status") (get "name"))
			}))


(defn format-jira [jira-map]
	(str (:jira jira-map) "\t" (:summary jira-map) "\t" (:assignee jira-map) "\t" (:status jira-map)))

(defn substitute-jira [parsed-log]
	(map substitute-jira-in-log-line parsed-log))


(defn format-jira-report [parsed-log]
  ;;(println parse-log)
  (pmap (fn [jira]
         ;;(println jira)
		    (->> (substitute-jira-in-log-line jira)
		    	format-jira
		    )
		 )
	 	parsed-log))

;;(format-jira (substitute-jira-in-log-line "DEVPROG-1"))

(defn export-csv [log]
	(->> log parse-log format-jira-report))

(defn display [x]
	(doseq [line x]
		(println line)))

(defn -main
  "I don't do a whole lot."
  []
  (println "starting!\n")
  (time (display (export-csv (read-file "jira-issues.txt")))))

;;(export-csv (read-file "jira-issues.txt"))
