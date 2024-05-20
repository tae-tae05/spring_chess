# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

link to phase 2 assignment
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSnfoccKQCLAwwAIIgQKAM4TMAE0HAARsAkoYMhZkzowUAJ4TcRNAHMYABgB0ADkzGoEAK7YYAYjTAqumACUUxpBI6gkgQaK4A7gAWSGAciKikALQAfCzUlABcMADaAAoA8mQAKgC6MAD0DipQADpoAN5VlO4AtigANDC4UuHQMp0oLcBICAC+mBTpsClpbOJZUH4BsVAAFE1QrR1dyhK9UP0wg8MIAJQTrOwwMwJCouJSWcYoYACq1evVW+e3ImKSEmuqXUWTIAFEADJguBFGAbLYwABm9hacOqmF+9wB1xmkzmKCyaAcCAQF2oVxu8ixjxgIEWghQ70on2awDanW6ez6Pyp-ykQLUCiyAEkAHLg7yw+Fs7ac-aHY4jGBior5NG0PEU1KYvkSLJ0lAM4QOMCRFmbGU8u66gUg5XisGS9UW9kwYAmyJFCAAa3Q9tVbo9GN5D0BiVxl3mgdNXt9aDJ+JxqTxmWjnp96ATlCT8GQ2iyACYzGY6vV3TGM2hxugZJo7I5nC5oLxnjAIRB-GEXFEYnE80lk6xU3lCqUKiopCE0KXpa65X1xinpoPyVGEB2kGhzVsObt5edNeIBTrQ08Xky1rPZXvucHraHbULyJDoVKvjLdz0+kiUc6E1qYBPAF9XpWJjVNbdLTvP4HxmO0xQlN9WVdct0zjf01VQ6DqTDCNVwJNNY0zQ9VHDFcaCgLJUKI+MlxzeJ8xgIsSwaajK2rNBa1sewnFcOwUD9dtjEcZhu2iWJMAYgdZmHPgXyKMEynKCcJCnOo2LjLNl1mdgsnXYSTVWU51I9Gj-yPSl72AtQUAQF4UHAtD0CM7CbTgp85KhBSACofwgVFUJgKJKFUDS-QAXkIytXNgvD8ULYtzNIuLU2YrT6P7MAErMLROLrHjG0WGQ22WGAAHEZUBMTe0kzKBSXLJsjKsEx3KYwZRMitNLouLdJgZBAgqtoJBckjjxDaznjAIbJBcoD+Xc0EXxhXzkX8mB2uGmB6k2lBhT4Toe1iC8dxgOoFAQUBvROj8NplUUZXGebcPIvrmLOhpdr1HIdplfbDvExl31dc7LpAa7ge2XaHraUYSkXSNkvI1Liw+37hsa9G9oOoLAZukG0Auq78ah+7Hvh9KyNzBIsqY1HSy+zHdv+3HexJzpQeJyHOmh8mOK4+teJcbAHCgbA7PgUDVBmiJAdqmn6qHSicgKYolN2zqnOnT6ydhynXqjNAUHCGbVl5toD0R8arP5EDDViU3zZQK0YOxRb7UQu62hh7YjqB5COjqGAifB9mvexjDw+kZ6cxI7KGb+vgEfw2OlfjnW2n25OKIymn06xrPcoFgrXF0Wz13CGAACkIE3cqZVcEPvXl7RFamRrcleVqNfcUzK1LKSwDgCB1ygHnE+z7NeqjAArWu0FNwfh9H8fM74S2U8s12aTnzdF8y5foFX7GXZwx8RQdJ0l5H6BI6+3GQvD-aYrd6eCLSsaqYaumcp6mZB+ykXfKDY+JQGAF4RAhpYDAGwOLQgwRQiyxqoPNuFFGqeRaopCo6h9Y6SjDIWy9lRpWy3jhLIBC7IO0qnNCaC1gQeXkmCVav4vpJWttvb6FD7IXhGqfNy9CsgYJ8n5VEGxpCf21LQzhhCwIehoTbMMAjAKMOYetLCMcv6I0AZ-FKysP5K1zoxNKNZMBAA
