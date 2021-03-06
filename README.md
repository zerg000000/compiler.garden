# compiler.garden

[![Build Status](https://travis-ci.org/zerg000000/compiler.garden.svg?branch=master)](https://travis-ci.org/zerg000000/compiler.garden)

[Integrant][] methods for compiling [Garden][] files to CSS in
the [Duct][] framework.

[integrant]: https://github.com/weavejester/integrant
[garden]:    https://github.com/noprompt/garden
[duct]:      https://github.com/duct-framework/duct

## Installation

To install, add the following to your project `:dependencies`:

```clojure
[compiler.garden "0.1.3"]
```

## Usage

The library provides the `:duct.compiler/garden` key, and is used to compile Garden styles in namespaces to CSS in development and production environments.

```clojure
:duct.compiler/garden
 {:builds [{:id "main"
            :stylesheet example.style/screen
            :compiler {:output-to "target/resources/example/public/css/style.css"
                       :pretty-print? false}}]}
```

The `:builds` option is mandatory. `:stylesheet` is a var contain the garden stylesheet datastructure. The `[:compiler :output-to]` option is where the garden compiler output the CSS file to. You can have multi CSS files compiled, just by defining more build. the `:compiler` will pass to garden compiler directly, you can get more detail about configuration from [Garden][] 

 
In order to get auto css refresh in development mode, the css must be either output to the duct default css path `resources/baywatch/public/css` or define the css path explicitly in `dev.edn`.

```clojure
:duct.server/figwheel
{:css-dirs ["target/resources/example/public/css" "resources/example/public/css"]
 :builds [...]}
```

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
