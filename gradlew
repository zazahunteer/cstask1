#!/bin/sh
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd) || exit 1
if [ -n "$JAVA_HOME" ]; then
    JAVA_CMD="$JAVA_HOME/bin/java"
else
    JAVA_CMD=java
fi
if ! command -v "$JAVA_CMD" >/dev/null 2>&1; then
    printf '%s\n' 'Java was not found. Set JAVA_HOME to JDK 17.' >&2
    exit 1
fi
exec "$JAVA_CMD" -Dorg.gradle.appname=gradlew -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
