# 🏀 Arbitrage Detector

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)
![Status](https://img.shields.io/badge/Status-Work_In_Progress-yellow?style=for-the-badge)

A Java-based application, currently being migrated to **Spring Boot**, that fetches game odds from a variety of leagues. It interfaces with the **SportsGameOdds API** to aggregate bookmaker data and algorithmically detects **arbitrage opportunities** where the variance in odds between different bookmakers guarantees a profit regardless of the game's outcome.

## 🧐 What is Arbitrage Betting?

Arbitrage betting (or "arbing") involves placing bets on all possible outcomes of an event at odds that guarantee a profit. This program automates the tedious math required to find these specific discrepancies.

**How it works:**
1.  **Fetch:** The app pulls live odds for upcoming games (NBA, NFL, MLB, etc.).
2.  **Compare:** It cross-references odds across multiple bookmakers.
3.  **Detect:** It calculates the implied probability. If the sum of probabilities is `< 100%`, an arbitrage opportunity exists.
4.  **Report:** It prints the specific matchup, the bookmakers to use, and the calculated profit margin to the console.

## 🛠 Tech Stack

* **Framework:** Spring Boot 3+
* **Language:** Java
* **Build Tool:** Gradle
* **HTTP Client:** OkHttp3
* **JSON Parsing:** Gson
* **Data Source:** SportsGameOdds API

## 🚀 Getting Started

### Prerequisites

* **Java Development Kit (JDK):** Version 17 or higher (Required for Spring Boot 3).
* **Gradle:** Ensure Gradle is installed (or use the included Gradle wrapper).
* **API Key:** A valid API Key from the SportsGameOdds service.

### Configuration (Work in Progress)

**⚠️ Note on API Keys:**
Currently, the API Key configuration is static as part of the initial Spring Boot migration. To set your key, you must manually edit the configuration file. Dynamic input is planned for a future update.

1.  Navigate to `src/main/client/SGOAPIClient.java`.
2.  Add or update the following line:
    ```SGOAPIClient
    .addHeader("X-Api-Key", "api-key-goes-here")
    ```

### Usage

Run the application directly from the terminal using the Spring Boot Gradle plugin:

```bash
# Linux/Mac
./gradlew bootRun

# Windows
gradlew.bat bootRun

```

## 🔮 Roadmap

The project is currently undergoing a major refactor to utilize the Spring ecosystem.

### **Interactive CLI (Spring Shell):**
* Allow users to exit the application gracefully at any moment.
* Highlight arbitrage opportunities with color-coded outputs and tables.

### **Automation:**
* Implement timed interval scanning (Cron jobs) to catch arbitrage opportunities that only exist for fleeting moments.

### **User Experience:**
* Generate direct links to the specific bookmakers when an opportunity is discovered.

### **Configuration:**
* Move API Key configuration to a secure, user-prompted setup to avoid hardcoding.

