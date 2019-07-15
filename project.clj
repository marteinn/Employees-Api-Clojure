(defproject employees-api "0.1.0-SNAPSHOT"
  :description "A example api with orm built in clojure"
  :url "https://github.com/marteinn/Employees-Api-Clojure"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 ; Routing
                 [compojure "1.6.1"]
                 ; Http library for client/server
                 [http-kit "2.3.0"]
                 ; Ring defaults - for query params etc
                 [ring/ring-defaults "0.3.2"]
                 ; Clojure data.JSON library
                 [org.clojure/data.json "0.2.6"]
                 ; DB
                 [toucan "1.12.0"]
                 [org.postgresql/postgresql "42.2.4"]
                 ; data description and validation
                 [prismatic/schema "1.1.11"]]
  :main ^:skip-aot employees-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
