(ns brains_brain.test-runner
  (:require
   [cljs.test :refer-macros [run-tests]]
   [brains_brain.core-test]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
       (run-tests
        'brains_brain.core-test))
    0
    1))
