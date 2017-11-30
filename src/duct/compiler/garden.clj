(ns duct.compiler.garden
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [integrant.core :as ig]
            [garden.compiler :refer [compile-css]]))

(defmethod ig/init-key :duct.compiler/garden [_ {:keys [builds]}]
  (doseq [{:keys [compiler stylesheet id]} builds]
    (.mkdirs (.getParentFile (io/file (:output-to compiler))))
    (require (symbol (namespace stylesheet)) :reload)
    (compile-css compiler (var-get (resolve stylesheet))))
  (mapv #(get-in % [:compiler :output-to]) builds))
