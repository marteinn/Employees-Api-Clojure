(ns employees-ui.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))


(defonce app-state (r/atom {:employees []}))


;; -------------------------
;; Data

(defn load-employees []
  (go (let [response (<! (http/get "http://localhost:8080/employees"
                                   {:with-credentials? false
                                    :query-params {"since" 135}}))]
        (swap! app-state assoc :employees 
               (:data (:body response))))))

;; -------------------------
;; Views

(defn employee [employee]
  ^{:key (:id employee)} [:div.ui.card
                          [:div.content
                           [:a.header (:email employee)]
                           [:div.meta
                            [:span (:company employee)]]]])

(defn employee-list [employees]
  [:div.emplpyee-list
    [:h2.ui.center.aligned.header "Employees"]
    [:div.ui.cards
     (map (fn [x] (employee x)) employees)
    ]])

(defn app []
  [:div.app
   (employee-list (:employees @app-state))])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [app] (.getElementById js/document "app")))

(defn init! []
  (load-employees)
  (mount-root))
