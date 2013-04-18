(ns db-access.get-stat-from-db)


(require '[clojure.java.jdbc :as sql])
 
(def db {:classname "oracle.jdbc.driver.OracleDriver"  ; must be in classpath
           :subprotocol "oracle"
           :subname "thin:@host511.com:1521:database"
           :user "user"
           :password "password"})

(defn write-file
  "Writes a value to a file"
  [value out-file]
  (spit out-file "" :append false)
  (with-open [out-data (clojure.java.io/writer out-file)]
      (.write out-data (str value))))

(defn read-file [in-file]
  (with-open [rdr (clojure.java.io/reader in-file)]
    (reduce conj [] (line-seq rdr))))


(def last-session-insert-seq (atom (:last-session-insert (read-string (first (read-file "./state.txt"))))))
(def last-task-insert-seq (atom (:last-task-insert (read-string (first (read-file "./state.txt"))))))


(def rank (apply hash-map (interleave 
         '(
           :submit_time
           :create_time
           :st
           :data
           :app_name
           :client_host_name
           :end_time
           :last_error_time
           :num_task_canceled
           :num_task_done
           :num_task_error
           :num_task_pending
           :num_task_running
           :priority
           :reason
           :session_tag
           :session_id
           :session_state
           :session_name
           :session_type
           :service
           :service_instances
           :total_input_size
           :total_output_size
           :total_tasks_submit2start_time
           :total_tasks_runtime
           :total_unsuccess_tasksruntime
           :total_unsuccess_taskruns
           
           :task_id
           :task_state
           :start_time
           :exec_host
           :input_size
           :output_size
           :service_pid
           :rerun_count
           :task_tag
           :last_failure_reason
           :input_compressed
           :output_compressed

           :task_diff
           :insert_seq
           :sseq
           :taskseq
           

           ) (iterate inc 1))))


(def shortkey {
           :app_name :app_name
           :create_time :create_time
           :client_host_name :client_host_name
           :end_time :end_time
           :insert_seq :insert_seq
           :last_error_time :last_err_time
           :num_task_pending :num_t_pending
           :num_task_running :num_t_running
           :num_task_done :num_t_done
           :num_task_canceled :num_t_canceled
           :num_task_error :num_tsk_error
           :priority :prio
           :reason :reason
           :service_instances :svc_inst
           :session_id :s_id
           :session_tag :s_tag
           :service :svc
           :session_state :s_state
           :session_name :s_name
           :session_type :s_type
           :total_unsuccess_taskruns :tot_unsuc_t_runs
           :total_input_size :tot_in_size
           :total_tasks_runtime :tot_tsks_runtime
           :total_unsuccess_tasksruntime :tot_unsuc_truntime
           :total_tasks_submit2start_time :tot_t_sub2start_time
           :total_output_size :tot_out_size
           
           :task_id :t_id
           :task_state :t_state
           :submit_time :sub_time
           :start_time :start_time
           :exec_host :exec_host
           :input_size :in_size
           :output_size :out_size
           :service_pid :svc_pid
           :rerun_count :rerun_count
           :task_tag :t_tag
           :last_failure_reason :lst_fail_reason
           :input_compressed :in_comp
           :output_compressed :out_comp

           })

(defn queryredeploy [last-session] (str "select s.create_time,s.insert_seq
from
                SYZYGY.SESSION_HISTORY s
where                      s.app_name = 'MYAPP'
and                        s.session_id = 1
and                        s.insert_seq > " last-session "
order by s.insert_seq
"))


(defn querysession-by-deploy [from to last-session _] 
  (let [from* (if (nil? from) "to_date('01-jan-1999')" from)
        to* (if (nil? to) "to_date('01-jan-2999')" to)] 
    (str "select 'session' as data,s.CREATE_TIME,s.session_id, s.session_name, s.insert_seq
from
                SYZYGY.SESSION_HISTORY s
where                      s.app_name = 'MYAPP'
and                        s.CREATE_TIME >= " from* "
and                        s.CREATE_TIME < " to* "
and                        s.insert_seq > " last-session "
order by s.insert_seq
")))

(defn querysession-by-deploy-all [from to last-session _] 
  (let [from* (if (nil? from) "to_date('01-jan-1999')" from)
        to* (if (nil? to) "to_date('01-jan-2999')" to)] 
    (str "select 'session' as data,s.CREATE_TIME, s.SESSION_ID, s.SESSION_NAME, s.APP_NAME, s.PRIORITY, s.SESSION_STATE, s.NUM_TASK_PENDING, s.NUM_TASK_RUNNING, s.NUM_TASK_DONE, s.NUM_TASK_ERROR, s.NUM_TASK_CANCELED, s.SERVICE_INSTANCES, s.REASON, s.END_TIME, s.SESSION_TYPE, s.LAST_ERROR_TIME, s.SERVICE, s.TOTAL_UNSUCCESS_TASKRUNS, s.TOTAL_UNSUCCESS_TASKSRUNTIME, s.TOTAL_TASKS_RUNTIME, s.TOTAL_INPUT_SIZE, s.TOTAL_OUTPUT_SIZE, s.TOTAL_TASKS_SUBMIT2START_TIME, s.SESSION_TAG, s.CLIENT_HOST_NAME, s.insert_seq
from
                SYZYGY.SESSION_HISTORY s
where                      s.app_name = 'MYAPP'
and                        s.CREATE_TIME >= " from* "
and                        s.CREATE_TIME < " to* "
and                        s.insert_seq > " last-session "
order by s.insert_seq
")))

(defn rownum [n query]
  (str "select *
from  
( " query " ) 
where ROWNUM <= " n ""))

(defn querytask-by-deploy [from to last-session last-task]
  (let [from* (if (nil? from) "to_date('01-jan-1999')" from)
        to* (if (nil? to) "to_date('01-jan-2999')" to)] 
    (str "select 'task' as data,s.CREATE_TIME as st,t.submit_time,s.session_id, s.session_name, s.insert_seq as sseq, t.TASK_ID, t.insert_seq as taskseq, TO_NUMBER(TO_CHAR(t.END_TIME, 'SSSSS')) - TO_NUMBER(TO_CHAR(t.START_TIME, 'SSSSS')) + (TO_NUMBER(TO_CHAR(t.END_TIME, 'FF')) - TO_NUMBER(TO_CHAR(t.START_TIME, 'FF')))/1e6 as task_diff
from       SYZYGY.TASK_ATTRIBUTES t, SYZYGY.SESSION_HISTORY s
where                      t.app_name = 'MYAPP'
and                        t.app_name = s.app_name
and                        t.cluster_name = s.cluster_name
and                        t.session_id = s.session_id
and                        t.submit_time >= " from* "
and                        t.submit_time < " to* "
and                        s.CREATE_TIME >= " from* "
and                        s.CREATE_TIME < " to* "
and                        s.insert_seq > " last-session "
and                        t.insert_seq > " last-task "
order by t.submit_time ")))

(defn querytask-by-deploy-all [from to last-session last-task]
  (let [from* (if (nil? from) "to_date('01-jan-1999')" from)
        to* (if (nil? to) "to_date('01-jan-2999')" to)] 
    (str "select 'task' as data,s.SESSION_NAME, s.SESSION_TYPE, s.SERVICE, s.SESSION_TAG, s.CLIENT_HOST_NAME, t.TASK_ID, t.SESSION_ID, t.APP_NAME, t.TASK_STATE, t.SUBMIT_TIME, t.START_TIME, t.END_TIME, t.EXEC_HOST, t.INPUT_SIZE, t.OUTPUT_SIZE, t.SERVICE_PID, t.RERUN_COUNT, t.TASK_TAG, t.LAST_FAILURE_REASON, t.INPUT_COMPRESSED, t.OUTPUT_COMPRESSED, TO_NUMBER(TO_CHAR(t.END_TIME, 'SSSSS')) - TO_NUMBER(TO_CHAR(t.START_TIME, 'SSSSS')) + (TO_NUMBER(TO_CHAR(t.END_TIME, 'FF')) - TO_NUMBER(TO_CHAR(t.START_TIME, 'FF')))/1e6 as task_diff
from       SYZYGY.TASK_ATTRIBUTES t, SYZYGY.SESSION_HISTORY s
where                      t.app_name = 'MYAPP'
and                        t.app_name = s.app_name
and                        t.cluster_name = s.cluster_name
and                        t.session_id = s.session_id
and                        t.submit_time >= " from* "
and                        t.submit_time < " to* "
and                        s.CREATE_TIME >= " from* "
and                        s.CREATE_TIME < " to* "
and                        s.insert_seq > " last-session "
and                        t.insert_seq > " last-task "
order by t.submit_time ")))



(defn calldb-by-row [query f-each-row] (sql/with-connection db 
   (sql/with-query-results rs [query] 
     ; rs will be a sequence of maps, 
     ; one for each record in the result set. 
     (dorun (map  f-each-row rs)))))

(defn calldb [query f-result] (sql/with-connection db 
   (sql/with-query-results rs [query] 
     ; rs will be a sequence of maps, 
     ; one for each record in the result set. 
     (dorun (f-result rs)))))

(defn calldb-return [query] (sql/with-connection db 
   (sql/with-query-results rs [query] 
     ; rs will be a sequence of maps, 
     ; one for each record in the result set. 
     (doall rs))))

(defn calldb-test [query] (sql/with-connection db 
   (sql/with-query-results rs [query] 
     ; rs will be a sequence of maps, 
     ; one for each record in the result set. 
     (type rs))))


(defn rawformat [format1] (fn [row] (format1 (map (fn [[r k v]] [k v]) (sort-by first (map (fn [[k v]] [ (rank k) k v]) (into [] row)))))))

(defn niceprint [format1 timestamp-key] (fn [m] (println (apply str (map #(let [[k v] %, entry (str (name k) "=" v " " )] (if (= k timestamp-key) (str v " - " entry) entry)) ((rawformat format1) m))))))
(defn rawprint [format1 _] (fn [row] (println ((rawformat format1) row))))
(defn fastprint [format1 _] (fn [row] (print ".")))

(defn standardold [rs] (map (fn [column] column)  rs))

(defn get-class [obj] (.getName (class obj)))

(defn format-dates-iso [obj] 
  (if (= (get-class obj) "oracle.sql.TIMESTAMP") (clojure.string/replace obj #" " "T") obj))

(defn format-dates [obj] 
   (if (= (get-class obj) "oracle.sql.TIMESTAMP") (clojure.string/replace (clojure.string/replace (clojure.string/replace obj #" " ":")  #"-" "/")  #"\..*" "") obj))


(defn insert-seq? [[k v]] (not= k :insert_seq))
(defn insert-seq-debug? [[k v]] (or (= k :insert_seq) (= k :time_stamp)))
(defn filter-dates-iso [[k v]] [k (format-dates-iso v)])
(defn filter-dates [[k v]] [k (format-dates v)])
(defn filter-dates2 [[k v]] k)

(defn shorten-keys [[k v]] [(get shortkey k k) v])

(defn standard [atom-sequence filter-dates row] 
  (let [out (map filter-dates (map shorten-keys (filter insert-seq? (filter (fn [[k v]] (not (nil? v))) row))))
        insert-seq (:insert_seq (into {} row))
        new-insert-seq (when (not (nil? insert-seq)) (swap! atom-sequence #(if (< %1 %2) %2 %1) (.longValue insert-seq)))
        ]
         out))

(defn get-all-redeploy-times [column last-session] (reverse (reduce (fn [l {timestp column}] (cons timestp l)) '() (calldb-return (queryredeploy last-session) ))))
(defn get-redeploy-intervales [column last-session] (let [dtimes (map (fn [time] (str "to_date('" time "', 'yyyy/mm/dd:hh24:mi:ss')")) (map format-dates (get-all-redeploy-times column last-session)))]
    (map (fn [a b] [a b]) (cons nil dtimes)  (concat dtimes [nil]))))

(defn get-query-content [query insert-seq from to last-session last-task num printfct timestamp-key filter-dates printquery?] 
  (when printquery? (println (query from to last-session last-task))) 
  (calldb-by-row (rownum num (query from to last-session last-task)) (printfct (partial standard insert-seq filter-dates) timestamp-key)))

(defn query-for-intervales [intervals query last-insert-seq last-session last-task num printfct timestamp-key filter-dates printquery?] 
   (dorun (map (fn [[from to]] (get-query-content query last-insert-seq from to last-session last-task num printfct timestamp-key filter-dates printquery?)) intervals))
   )


(defn testflow [arg]
  (println "Testing starting:" arg)
 
  (swap! last-session-insert-seq (fn [_] (:last-session-insert (read-string (first (read-file "./statetest.txt"))))))
  (swap! last-task-insert-seq (fn [_] (:last-task-insert (read-string (first (read-file "./statetest.txt"))))))
  (let [last-session @last-session-insert-seq
        last-task @last-task-insert-seq
        intervals (get-redeploy-intervales :create_time last-session)]
    (println "Last session seq starting:" @last-session-insert-seq " Last task seq starting:" @last-task-insert-seq)
    (println intervals)
    (println "intervals to explore:" (count intervals))
    (query-for-intervales intervals querysession-by-deploy last-session-insert-seq last-session last-task 999999999 rawprint :create_time filter-dates true)
    (query-for-intervales intervals querytask-by-deploy last-task-insert-seq last-session last-task 1000 rawprint :sub_time filter-dates true)
    (println "Last session seq end:" @last-session-insert-seq " Last task seq end:" @last-task-insert-seq)
    )
  )
  
(defn normalflow [arg]
  (let [last-session @last-session-insert-seq
        last-task @last-task-insert-seq
        intervals (get-redeploy-intervales :create_time last-session)]
    (query-for-intervales intervals querysession-by-deploy-all last-session-insert-seq last-session last-task 999999999 niceprint :create_time filter-dates-iso false)
    (query-for-intervales intervals querytask-by-deploy-all last-task-insert-seq last-session last-task 999999999 niceprint :sub_time filter-dates-iso false)
  )
)

(defn -main [arg]
  (if (= arg "test") 
    (testflow arg)
    (normalflow arg))
  (write-file {:last-session-insert @last-session-insert-seq :last-task-insert @last-task-insert-seq} "./state.txt")
  
  )



