(ns employees-ui.prod
  (:require
    [employees-ui.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
