# SE302 - Automated Tests Project
This repository contains the project for Software Testing and Maintenance course. The goal of the project is to write automation tests for a web application using Playwright. Web Application that is tested is [YGOProDeck](https://ygoprodeck.com). 
YGOProDeck is a database of all Yu-Gi-Oh! cards released as well as a tool for building and sharing decks among Yu-Gi-Oh! players.

## Dependencies
- [Java](https://adoptium.net/temurin/releases/)
- [Maven](https://maven.apache.org/)

## Setup
1. Clone the repository: ``` git clone https://github.com/VenomTS/SE302-Project.git ```

## Running Tests
### Run All Tests
1. Navigate to the directory where ``` pom.xml ``` file is located
2. Run ``` mvn test ```

### Run Specific Test File
1. Navigate to the directory where ``` pom.xml ``` file is located
2. Run ``` mvn test -Dtest={FileName} ```

