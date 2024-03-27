#!/bin/sh
# ----------------------------------------------------------------------------
# Maven Wrapper startup script, version 3.2.0
#
# Required environment variables:
#   JAVA_HOME (optional): Path to the JDK used to run Maven
#
# This script will download the Maven Wrapper JAR if missing and then
# download the Maven distribution configured in .mvn/wrapper/maven-wrapper.properties.
# ----------------------------------------------------------------------------

set -euf

BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
WRAPPER_PROPERTIES="$WRAPPER_DIR/maven-wrapper.properties"
DOWNLOADER="$WRAPPER_DIR/MavenWrapperDownloader.java"

if [ ! -f "$WRAPPER_PROPERTIES" ]; then
  echo "Missing $WRAPPER_PROPERTIES"
  exit 1
fi

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Maven Wrapper JAR not found, bootstrapping..."
  if [ ! -f "$DOWNLOADER" ]; then
    echo "Missing $DOWNLOADER"
    exit 1
  fi

  # Compile downloader with system javac
  if ! command -v javac >/dev/null 2>&1; then
    echo "javac is required to download the Maven Wrapper JAR"
    exit 1
  fi

  (cd "$WRAPPER_DIR" && javac MavenWrapperDownloader.java)
  (cd "$BASE_DIR" && java -cp "$WRAPPER_DIR" MavenWrapperDownloader "$BASE_DIR")
fi

exec java ${JAVA_HOME:+-Djava.home="$JAVA_HOME"} -jar "$WRAPPER_JAR" "$@"

