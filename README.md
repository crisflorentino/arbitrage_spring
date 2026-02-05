# 🏀 Arbitrage Detector

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)
![Status](https://img.shields.io/badge/Status-Active_Development-brightgreen?style=for-the-badge)

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
* **HTTP Client:** OkHttp3 (Wrapped in Spring Component)
* **JSON Parsing:** Gson
* **Data Source:** SportsGameOdds API

## 🚀 Getting Started

### Prerequisites

* **Java Development Kit (JDK):** Version 17 or higher.
* **Gradle:** Ensure Gradle is installed (or use the included Gradle wrapper).
* **API Key:** A valid API Key from the [SportsGameOdds](https://sportsgameodds.com) service.

### Configuration

You can configure your API key in two ways. The application prioritizes the configuration file but falls back to interactive input.

**Option 1: Permanent Config (Recommended)**
1.  Navigate to `src/main/resources/application.properties`.
2.  Add the following line:
    ```properties
    odds.api.key=YOUR_ACTUAL_API_KEY_HERE
    ```

**Option 2: Interactive Input**
If no key is configured, the application will prompt you to enter your API key manually in the terminal upon startup.

### Usage

Run the application directly from the terminal using the Spring Boot Gradle plugin.

```bash
# Linux/Mac
./gradlew bootRun -q

# Windows
gradlew.bat bootRun -q
