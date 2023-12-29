# Custom Monaco Editor with Kotlin/React

Welcome to the custom Monaco Editor project! This project utilizes Kotlin and React to provide a Monaco code editor in the browser. 

![image](https://github.com/SKom2/kotlin-js-text-editor/assets/103752057/ef49f0c8-22f9-4fb8-92b2-c5d3b8488998)


## Features

- Integration with the Monaco Editor.
- Implementation of a RPC protocol to write and save a file.

## Project Structure

- src/commonMain/kotlin: Shared Kotlin codebase that can be used across different platforms.
  - FileContent.kt: Handles the content of files that should be saved.
- src/commonMain/resources: Resources such as properties files and configuration that are common across platforms.
- src/jsMain/kotlin: Kotlin code that targets JavaScript runtime. This is where the React components and bindings are implemented.
  - App.kt: The main React component that sets up the editor.
  - Main.kt: The entry point of the Kotlin/React application.
- src/jvmMain/kotlin: Kotlin code that targets the JVM, potentially for server-side logic.
  - Server.kt: Entry point for a server-side logic.
- build.gradle.kts: Kotlin DSL script for defining the Gradle build configuration.

## Getting Started

After cloning the repository, run the following command:

`./gradlew run`

## Saving Files

The editor comes with a "Save File" feature that allows you to persist your changes. This feature connected to backend logic for storing files on a server.

## References
The structure of the project and its initialization is borrowed from this helpful video:
https://www.youtube.com/watch?v=HEH57g-UP4Q&pp=ygUNa290bGluIGpzIGFwcA%3D%3D
