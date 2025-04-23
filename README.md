# SWIFT-BIC Project

This project provides a server application for managing SWIFT/BIC data.

## Prerequisites

Ensure you have the following installed on your system:

- **Java Development Kit (JDK)** (version 8 or higher)
- **Apache Maven** (version 3.6.0 or higher)

## Installation

### Step 1: Install Maven

1. Download Maven from the [official website](https://maven.apache.org/download.cgi).
2. Extract the downloaded archive to a directory of your choice.
3. Add the `bin` directory of Maven to your system's `PATH` environment variable.
4. Verify the installation by running:

    ```bash
    mvn -v
    ```

    You should see the Maven version and Java version displayed.

### Step 2: Clone the Repository

Clone this repository to your local machine:

```bash
git clone https://github.com/123pik1/swift-bic.git
```

## Running the Server

1. Navigate to the project directory:

    ```bash
    cd swift-bic
    ```
2. Build the project using Maven:

    ```bash
    mvn clean install
    ```

3. Start the server:

    ```bash
    mvn -q exec:java -Dexec.mainClass="pik.Server.Server"
    ```

4. The server will start on port 8080

## Additional Notes

- To stop server input `exit` command in the server's  terminal
- To customize the server port or other configurations, edit the `application.properties` file located in the `src/main/resources` directory.
- For troubleshooting, check the logs in the terminal or refer to the `logs` directory if configured.
