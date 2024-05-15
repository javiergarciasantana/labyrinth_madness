# Labyrinth Madness

## Description

Labyrinth Madness is a Java application that solves mazes using different algorithms.

## Installation

1. Clone the repository:

```bash
git clone https://github.com/javiergarciasantana/labyrinth_madness.git
```

2. Navigate to the project directory:
cd labyrinth_madness

## Usage

1. Compile the Java files:

```bash
javac -cp "lib/core.jar" -source 1.8 -target 1.8 src/labyrinth_madness/*.java
```

2. Create the JAR file:

```bash
jar cfm labim.jar manifest.txt -C bin .
```

3. Run the application:

```bash
java -jar labim.jar
```