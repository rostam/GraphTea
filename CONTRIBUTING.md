# Contributing to GraphTea

Thank you for your interest in contributing to GraphTea! This document explains how to get started.

---

## Table of Contents

- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [How to Contribute](#how-to-contribute)
- [Submitting a Pull Request](#submitting-a-pull-request)
- [Reporting Bugs](#reporting-bugs)
- [Requesting Features](#requesting-features)
- [Code Style](#code-style)

---

## Getting Started

1. **Fork** the repository on GitHub
2. **Clone** your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/GraphTea.git
   cd GraphTea
   ```
3. **Set upstream** remote:
   ```bash
   git remote add upstream https://github.com/rostam/GraphTea.git
   ```

---

## Development Setup

### Prerequisites

- Java JDK 8 or higher
- [Apache Ant](http://ant.apache.org/)

### Build & Run

```bash
# Build
cd src/scripts
ant

# Run
java -jar graphtea-main.jar
# or use the convenience script:
./run.sh        # Linux/macOS
run.bat         # Windows
```

### Writing a New Algorithm

The fastest way to contribute an algorithm is to copy and modify the sample:

1. Open [`src/graphtea/extensions/algorithms/SampleAlgorithm.java`](src/graphtea/extensions/algorithms/SampleAlgorithm.java)
2. Copy it to a new file with your algorithm name
3. Implement the `GraphAlgorithm` interface
4. Drop the compiled `.class` file in the `extensions/` directory

---

## How to Contribute

| Type | Branch naming |
|------|--------------|
| Bug fix | `fix/short-description` |
| New feature | `feature/short-description` |
| Documentation | `docs/short-description` |

Always branch off `master`:
```bash
git checkout master
git pull upstream master
git checkout -b feature/my-new-feature
```

---

## Submitting a Pull Request

1. Make sure your code builds (`ant`) and does not break existing functionality
2. Keep commits focused — one logical change per commit
3. Write a clear PR description: what it does and why
4. Reference any related issue: `Fixes #42`
5. Open the PR against the `master` branch

---

## Reporting Bugs

Use [GitHub Issues](https://github.com/rostam/GraphTea/issues/new) and include:

- GraphTea version (or commit SHA)
- Java version (`java -version`)
- Operating system
- Steps to reproduce
- Expected vs. actual behavior
- Screenshot or error output if applicable

---

## Requesting Features

Open an issue with the `enhancement` label. Describe:

- The use case (why is this useful?)
- A sketch of the expected behavior
- Any graph-theoretic background that would help

---

## Code Style

- Follow standard Java conventions (camelCase methods, PascalCase classes)
- Keep methods short and focused
- Add a one-line Javadoc comment to public methods
- No trailing whitespace

---

Questions? Open an issue or email [rostamiev@gmail.com](mailto:rostamiev@gmail.com).
