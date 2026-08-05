#!/bin/bash
#
# Builds a native installer for the platform this script is running on.
#
# jpackage cannot cross-compile: a .dmg can only be produced on macOS, a .exe on
# Windows and a .deb on Linux. This script used to ask for all three on every
# machine, so two of the three always failed. It also passed
# --main-class com.mycompany.Main, a placeholder from the jpackage documentation
# rather than GraphTea's entry point, so the package it did build would not start.
#
set -euo pipefail

cd "$(dirname "$0")"

APP_NAME="GraphTea"
MAIN_JAR="graphtea-main.jar"
MAIN_CLASS="graphtea.platform.Application"
INPUT_DIR="binary"
# Deliberately outside INPUT_DIR: jpackage copies everything under --input into
# the app image, so an output directory nested inside it would fold each build's
# installer into the next one.
OUTPUT_DIR="packages"

MAC_ICON="src/presentation/graphics/GraphTea.icns"
WIN_ICON="src/presentation/graphics/GraphTea.ico"
LINUX_ICON="src/presentation/graphics/icon.PNG"

# Read the version from the source so the installer cannot drift from the
# version the application reports in its About dialog.
APP_VERSION="$(sed -n 's/.*VERSION = "\([^"]*\)".*/\1/p' \
    src/graphtea/platform/Application.java | head -1)"
if [ -z "$APP_VERSION" ]; then
    echo "Could not read VERSION from Application.java" >&2
    exit 1
fi

if [ ! -f "$INPUT_DIR/$MAIN_JAR" ]; then
    echo "$INPUT_DIR/$MAIN_JAR not found. Run 'ant' first." >&2
    exit 1
fi

if ! command -v jpackage >/dev/null 2>&1; then
    echo "jpackage not found. It ships with JDK 14 and later." >&2
    exit 1
fi

case "$(uname -s)" in
    Darwin)
        TYPE="dmg"
        ICON="$MAC_ICON"
        ;;
    Linux)
        TYPE="deb"
        ICON="$LINUX_ICON"
        ;;
    MINGW*|MSYS*|CYGWIN*)
        TYPE="exe"
        ICON="$WIN_ICON"
        ;;
    *)
        echo "Unsupported platform: $(uname -s)" >&2
        exit 1
        ;;
esac

mkdir -p "$OUTPUT_DIR"

echo "Building $TYPE package for $APP_NAME $APP_VERSION..."

ICON_ARGS=()
if [ -f "$ICON" ]; then
    ICON_ARGS=(--icon "$ICON")
else
    echo "Icon $ICON not found; building without one."
fi

# jpackage builds the launcher and, on Linux, the .desktop entry itself, so the
# hand-written desktop file this script used to emit is no longer needed.
jpackage \
    --input "$INPUT_DIR" \
    --main-jar "$MAIN_JAR" \
    --main-class "$MAIN_CLASS" \
    --name "$APP_NAME" \
    --app-version "$APP_VERSION" \
    --type "$TYPE" \
    --dest "$OUTPUT_DIR" \
    --vendor "Graph Theory Software" \
    --java-options "-Xmx1024m" \
    "${ICON_ARGS[@]}"

echo "Done. Package written to $OUTPUT_DIR/"
