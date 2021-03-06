(ns leiningen.new.sinusoides
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def render (renderer "sinusoides"))

(defn binary [file]
  (io/input-stream
   (io/resource
    (str/join "/" ["leiningen" "new" "sinusoides" file]))))

(defn sinusoides
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)
              :uppercased (str/upper-case (name-to-path name))}]
    (main/info "Generating fresh 'lein new' sinusoides project.")
    (->files data
             [".gitignore" (render "gitignore" data)]
             ["AUTHORS" (render "AUTHORS" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["README.md" (render "README.md" data)]
             ["makefile" (render "makefile" data)]
             ["project.clj" (render "project.clj" data)]
             ["externs.js" (render "externs.js" data)]
             ["package.json" (render "package.json" data)]
             ["server.coffee" (render "server.coffee" data)]
             ["config/compass.rb" (render "compass.rb" data)]
             ["data/data.yaml" (render "data.yaml" data)]
             ["sass/main.scss" (render "main.scss" data)]
             ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
             ["resources/favicon.ico" (binary "favicon.ico")]
             ["resources/views/index.html" (render "index.html" data)]
             ["resources/views/release.html" (render "release.html" data)]
             ["resources/views/debug.html" (render "debug.html" data)])))
