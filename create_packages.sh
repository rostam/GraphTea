#!/bin/bash

cd binary || exit

# Variables
APP_NAME="GraphTea"
MAIN_JAR="graphtea-main.jar"
MAC_ICON="../src/presentation/graphics/GraphTea.icns"
WIN_ICON="../src/presentation/graphics/GraphTea.ico"
LINUX_ICON="../src/presentation/graphics/icon.PNG"
OUTPUT_DIR="packages"
DESKTOP_FILE="$APP_NAME.desktop"

# Ensure the output directory exists
mkdir -p $OUTPUT_DIR

# Create a .desktop file for Linux
create_desktop_file() {
    echo "Creating .desktop file..."
    cat > $DESKTOP_FILE <<EOL
[Desktop Entry]
Name=$APP_NAME
Comment=GraphTea: A Graph Theory Software
Exec=/opt/$APP_NAME/$APP_NAME
Icon=/opt/$APP_NAME/$LINUX_ICON
Terminal=false
Type=Application
Categories=Utility;
EOL
    echo ".desktop file created!"
}

# Function to create a package
create_package() {
    local platform=$1
    local type=$2
    local icon=$3

    echo "Creating $platform package..."
    jpackage \
        --input . \
        --main-jar $MAIN_JAR \
        --name $APP_NAME \
        --type $type \
        --dest $OUTPUT_DIR \
        ${icon:+--icon $icon} \
        --main-class com.mycompany.Main \
        --java-options "-Xmx1024m" \
        --resource-dir .

    echo "$platform package created!"
}

# Create macOS package
create_package "macOS" "dmg" $MAC_ICON

# Create Windows package (requires cross-compiling or building on Windows)
create_package "Windows" "exe" $WIN_ICON

# Create Linux package
create_desktop_file
create_package "Linux" "deb" $LINUX_ICON

echo "All packages created successfully!"





