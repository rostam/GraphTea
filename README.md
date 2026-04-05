[![Gitter](https://badges.gitter.im/JoinChat.svg)](https://gitter.im/graphtheorysoftware/GraphTea?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://app.travis-ci.com/rostam/GraphTea.svg?branch=master)](https://app.travis-ci.com/rostam/GraphTea)

![Preview](http://github.com/graphtheorysoftware/GraphTea/raw/master/src/presentation/peterson.png)

# GraphTea

[GraphTea](http://graphtheorysoftware.com) is a software framework for working on graphs and social networks. It helps you to:

- Draw a graph
- Get reports about it
- Run algorithms on it
- Visualize it

[DOWNLOAD GraphTea](https://github.com/graphtheorysoftware/GraphTea/zipball/master)

Runs on Windows / Linux / macOS (Java-based).

---

## GraphTea Ecosystem

GraphTea has grown into a family of related tools. Here is a map of all variants:

| Repository | Language | Status | Description |
|-----------|----------|--------|-------------|
| [GraphTea](https://github.com/rostam/GraphTea) | Java | ✅ Active | Core desktop application |
| [GTea](https://github.com/rostam/GTea) | Java | ✅ Active | Online/server version |
| [GTeaShell](https://github.com/rostam/GTeaShell) | Java | ✅ Active | Shell/CLI version |
| [GSearchTea](https://github.com/rostam/GSearchTea) | Java | ✅ Active | Big-data graph search (Flink/Spark) |
| [CGTea](https://github.com/rostam/CGTea) | C++ | ✅ Active | Lightweight C++ desktop version |
| [CGTeaQt](https://github.com/rostam/CGTeaQt) | C++ | 🔧 Experimental | Qt-based desktop version |
| [CGTeaWeb](https://github.com/rostam/CGTeaWeb) | C++ | 🔧 Experimental | Web version via Emscripten/WASM |
| [WASMTea](https://github.com/rostam/WASMTea) | WASM | 🔧 Experimental | Pure WebAssembly browser version |
| [GTeaJS](https://github.com/rostam/GTeaJS) | JavaScript | 🔧 Experimental | JavaScript/web port |
| [GTeaMinimal](https://github.com/rostam/GTeaMinimal) | Java | 📦 Minimal | Stripped-down Java version |
| [rust_gtea](https://github.com/rostam/rust_gtea) | Rust | 🚧 In progress | Rust rewrite |
| [SpringTea](https://github.com/rostam/SpringTea) | Java | 🚧 In progress | Spring Boot-based version |

---

## YouTube Demo

See this [video](http://www.youtube.com/watch?v=0gblxDCNsmY)

## Run

Execute `run.bat` (Windows), or `run.sh` (Linux/macOS).

Manually:
```
java -jar graphtea-main.jar
```

## Develop

1. Make your changes
2. Run `make.sh` (or type `./make.sh` in terminal)
3. It will compile your changes and run the application.

Manually:

1. Open terminal
2. `cd src/scripts`
3. `ant` — this will build the application in the binary folder.
4. For seeing your changes, follow the Run steps above.

Note: [Apache Ant](http://ant.apache.org/) must be installed.

## Write a Graph Algorithm

Modify [SampleAlgorithm.java](https://github.com/graphtheorysoftware/GraphTea/blob/master/src/graphtea/extensions/algorithms/SampleAlgorithm.java). You can also make reports, generators, file formats, and actions.

## What Can You Do with GraphTea?

- **Draw** graphs with a mouse or using predefined generators (trees, complete graphs, stars, generalized Petersen, etc.)
- **Analyze** graphs with built-in reports (connected components, chromatic number, independence number, girth, triangles, etc.)
- **Run algorithms** step by step with visual feedback — useful for teaching
- **Visualize** social networks and large graphs with layout algorithms
- **Export** to image files or LaTeX for papers and reports
- **Extend** with custom plugins (Java or MATLAB) by dropping files into the extensions directory

## Benchmark Graphs

GraphTea includes a curated collection of benchmark graphs under **Generate Graph > Benchmark Graphs**, covering classic named graphs and standard random graph models used in research:

| Graph | Vertices | Edges | Description |
|---|---|---|---|
| Grotzsch Graph | 11 | 20 | Triangle-free graph with chromatic number 4 (Mycielski of C5) |
| Heawood Graph | 14 | 21 | 3-regular bipartite graph; smallest 6-cage |
| Pappus Graph | 18 | 27 | 3-regular bipartite graph of girth 6 |
| Frucht Graph | 12 | 18 | Smallest 3-regular graph with trivial automorphism group |
| Zachary Karate Club | 34 | 78 | Classic social network benchmark for community detection (Zachary 1977) |
| Barabasi-Albert Graph | n | ~n·m | Scale-free graph with power-law degree distribution (params: n, m) |
| Watts-Strogatz Graph | n | ~n·k/2 | Small-world graph combining high clustering with short paths (params: n, k, betaPercent) |

## Need Help?

Post in [Issues](https://github.com/rostam/GraphTea/issues) and we will get back to you.

## Contribute

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create a new Pull Request

---

### Credits

- Current Developers:
  - Mohammad Ali Rostami — rostamiev [at] gmail [dot] com
  - Azin Azadi — aazadi [at] gmail [dot] com
  - E. Suresh — sureshkako [at] gmail [dot] com
