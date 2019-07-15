(ns employees-api.core
  (:require [schema.core :as s]
            [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [toucan.db :as db]
            [toucan.models :refer [defmodel]])
  (:gen-class))

(use 'clojure.walk)

; Models
(defmodel Employee :employee)

; Helpers
(defn response-data [body]
  str (json/write-str {"data" body}))

(defn response-ok [body]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body    (response-data body)})

(defn response-created [body]
  {:status 201
   :headers {"Content-Type" "text/json"}
   :body    (response-data body)})

(defn response-bad-request [body]
  {:status 400
   :headers {"Content-Type" "text/json"}
   :body    (response-data body)})

(defn response-not-found [body]
  {:status 404
   :headers {"Content-Type" "text/json"}
   :body    (response-data body)})

; Routes
(defn list-employees-route [req]
  (let [employees (db/select Employee)]
    (response-ok employees)))

(defn detail-employee-route [req]
  (let [employee (->>
    (:email (:route-params req))
    (db/select Employee :email)
    (first)
  )]
    (if employee
      (response-ok employee)
      (response-not-found "Employee not found"))))

(defn delete-employee-route [req]
  (let [status (db/simple-delete! Employee :email (:email (:route-params req)))]
    (if status
      (response-ok "deleted")
      (response-not-found "employee not found"))))

(defn create-employee-route [req]
  (let [employee (->>
    (:form-params req)
    (keywordize-keys)
    (:email)
    (db/select Employee :email)
    (first)
  )]
    (if employee
      (response-bad-request "Employee already exists")
      (do
        (->>
          (:form-params req)
          (keywordize-keys)
          (db/insert! Employee))
        (response-ok "created")))))

(defroutes app-routes
  (GET "/employees" [] list-employees-route)
  (GET "/employees/:email" [email] detail-employee-route)
  (DELETE "/employees/:email" [email] delete-employee-route)
  (POST "/employees" [] create-employee-route))

(defn -main
  "Main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (db/set-default-db-connection!
      {:classname  "org.postgresql.Driver"
      :subprotocol "postgresql"
      :subname     "//127.0.0.1:5432/postgres"
      :user        "postgres"
      :password    "postgres"})

    (server/run-server (wrap-defaults #'app-routes api-defaults) {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
