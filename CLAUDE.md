# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
# Full clean build + run (with JDWP debug port 8000)
./make.sh

# Build only
ant clean && ant

# Run the built JAR
java -jar binary/graphtea-main.jar

# Platform-specific launchers
./GraphTea-linux.sh
./GraphTea-mac.sh
./GraphTea-windows.bat

# Create distribution packages (macOS .dmg, Windows .exe, Linux .deb)
./create_packages.sh
```

Build output goes to `build/` (compiled classes) and `binary/` (JARs, plugins, extensions).

## Tests

Tests use JUnit 5 and live in `/test/`. There is no dedicated test runner script — run tests through your IDE or a JUnit runner with the classpath from `src/scripts/lib/`.

## Architecture

GraphTea is a **Java Swing desktop application** for graph theory, built around a plugin-extension architecture.

### Core Concepts

**BlackBoard** (`src/graphtea/platform/core/BlackBoard.java`)
The central event bus and state store. All inter-component communication flows through it. Components call `blackboard.setData(key, value)` to publish and `blackboard.addListener(key, listener)` to subscribe. This replaces direct dependencies between plugins.

**Plugin System** (`src/graphtea/platform/plugin/Plugger.java`)
Plugins are JAR files in `binary/plugins/`. Each JAR's manifest declares `plugin-name`, `plugin-version`, and `plugin-depends`. Plugger resolves load order via DFS topological sort. Each plugin has an `Init` class at `graphtea.plugins.<name>.Init` (or a custom manifest-specified class).

**Extension System** (`src/graphtea/platform/extension/`)
Extensions are JARs in `binary/extensions/`, dynamically loaded at startup. They implement typed interfaces:
- `AlgorithmExtension` — graph algorithms
- `GraphGeneratorExtension` — graph generators
- `ReportExtension` — computed graph properties/reports
- `ActionExtension` — graph transformations (products, line graphs, etc.)

### Module Layout

| Package | Role |
|---|---|
| `graphtea.platform` | Bootstrapping, BlackBoard, plugin/extension loading, preferences |
| `graphtea.graph` | Core graph model (`GraphModel`, `Vertex`, `Edge`), rendering, events |
| `graphtea.library` | Pure graph algorithm library, utilities |
| `graphtea.plugins` | Main UI, visualization, generators, algorithm animator, reports UI |
| `graphtea.extensions` | Concrete algorithms, generators, reports, actions (the bulk of domain logic) |
| `graphtea.ui` | Swing UI components: property editors, sidebars, menus |
| `graphtea.samples` | Example extension implementations |

### Entry Point

`graphtea.platform.Application.main()` → initializes BlackBoard → loads Preferences → loads Plugins (from `binary/plugins/`) → loads Extensions (from `binary/extensions/`) → fires `POST_INIT_EVENT`.

### Adding a New Extension

1. Implement the appropriate interface (e.g., `ReportExtension<GraphModel, String>`).
2. Build it as a JAR and drop it in `binary/extensions/`.
3. See `src/graphtea/samples/` for working examples.
