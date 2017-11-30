(ns compiler.garden-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [duct.compiler.garden :as garden]
            [integrant.core :as ig]))

(defmacro with-temp-files [fs & body]
  `(let [fs# ~fs]
     (try (doseq [f# fs#] (io/delete-file f# true))
          ~@body
          (finally (doseq [f# fs#] (io/delete-file f# true))))))

(def test-styles
  [[:body {:color "#fff"
           :height "100%"}]])

(def actual-style
  "body{color:#fff;height:100%}")

(def actual-pretty-style
  "body {\n  color: #fff;\n  height: 100%;\n}")

(def config
  {:duct.compiler/garden
   {:builds [{:id "main"
              :stylesheet 'compiler.garden-test/test-styles 
              :compiler {:output-to "target/test/output/test.css"
                         :pretty-print? false}}]}})

(deftest module-test
  (testing "normal output"
    (let [actual (io/file "target/test/output/test.css")]
      (with-temp-files [actual]
        (let [compiled (ig/init config)]
          (is (.exists actual))
          (is (= actual-style (slurp actual)))
          (is (= compiled #:duct.compiler{:garden [(.getPath actual)]}))))))
  (testing "pretty output"
    (let [actual (io/file "target/test/output/test.css")]
      (with-temp-files [actual]
        (ig/init (assoc-in config [:duct.compiler/garden :builds 0 :compiler :pretty-print?] true))
        (is (.exists actual))
        (is (= actual-pretty-style (slurp actual)))))))
