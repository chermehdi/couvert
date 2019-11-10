# Couvert

## Introduction

- *Couvert* is a code coverage java agent, that you can use to showcase the power 
of java agents, and to look at some code coverage data collection techniques.

## Usage

- The project is built using `maven` so you should have that installed.
- clone the repository.
- build the agent by running `mvn clean package`, This will generate a target directory containing the `couvert-1.0.0.jar` agent.
- Launch your application with `java -javaagent:couvert-1.0.0.jar -jar java-app.jar` and codecoverage will be generated as a `JSON` file.
 
 
## Contribution

- Bug reports, Enhancement requests, and Pull requests are all welcomed 🚀.