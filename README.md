# MrD_Assessment Android Application

A modern Android application built using **Jetpack Compose**, **Kotlin**, **Coroutines & Flow**, **Room Database**, **Paging 3**, **Hilt Dependency Injection**, and **MockWebServer**.

---

## 📱 Features

- **Restaurant Browsing**: Paged list of restaurants loaded from a local MockWebServer API, cached locally in Room DB using Paging 3 and `RemoteMediator`.
- **Favourites Management**: Mark or unmark restaurants as favourites, persisted locally in Room Database (`favouriteRestaurant` table).
- **Favourites Tab**: Dedicated paged list of favourited restaurants with real-time reactive updates across the app.
- **Restaurant Details & Menu**: Interactive menu categories and available food items for selected restaurants.
- **Scroll Position Persistence**: Remembers scroll position on the restaurant list using Jetpack DataStore Preferences.

---

## 🏗️ Architecture & Technology Stack

The project follows **Clean Architecture** principles in a **multi-module** structure:

- **UI Framework**: Jetpack Compose with Material 3 & Navigation Compose.
- **Dependency Injection**: Dagger Hilt.
- **Local Storage**:
  - **Room Database**: Offline cache for restaurants and local storage for favourite restaurants.
  - **DataStore (Preferences)**: Preserves UI state (e.g. scroll position).
- **Pagination**: Paging 3 library (`Pager`, `PagingSource`, `RemoteMediator`).
- **Networking & Mocking**: Retrofit 2, OkHttp, and an embedded `MockWebServer` with local JSON mock files.
- **Image Loading**: Coil for Compose.
- **Asynchronous Logic**: Kotlin Coroutines, Flow, and StateFlow.

---

## 📂 Project Structure

```
MrD_Assessment/
├── app/                        # Main Application module, Hilt setup, NavHost
├── core/
│   ├── datastore/             # Room Database, DAOs, Entities & DataStore preferences
│   ├── navigation/            # App navigation definitions and arguments
│   ├── network/               # Retrofit API, MockWebServer dispatcher & JSON resources
│   ├── string/                # Centralized string resources
│   └── ui/                    # Base UI state containers and reusable components
├── data/
│   ├── menu/                  # Menu use cases & network calls
│   └── restaurant/            # Restaurant repository, use cases & RemoteMediator
├── feature/
│   ├── home/
│   │   ├── common/            # Shared Composables (e.g., RestaurantItem)
│   │   ├── favouriteTab/      # Favourites Tab screen and ViewModel
│   │   ├── home/              # Main container with Bottom Navigation
│   │   └── restaurantTab/     # Restaurant list Tab screen and ViewModel
│   └── restaurant/
│       └── detail/            # Restaurant Detail and Menu screen
└── model/                     # Core Domain data models (Restaurant, Menu, Item, etc.)
```

---

## 📋 Requirements

- **Android Studio**: Android Studio Ladybug (2024.2.1+) or newer.
- **JDK Version**: Java 11 (or higher).
- **Android SDK**: `minSdk = 24`, `targetSdk / compileSdk = 37`.
- **Gradle**: 8.x with Foojay toolchain resolver.

---

## 🚀 How to Run the Project

### Option 1: Using Android Studio (Recommended)

1. **Clone or Open the Project**:
   - Open Android Studio.
   - Click **Open** and select the `MrD_Assessment` project folder.
2. **Gradle Sync**:
   - Android Studio will automatically run a Gradle sync. Wait for it to complete.
3. **Select Target Device**:
   - Choose an Android Emulator (API 24 or higher) or a connected physical device with USB Debugging enabled.
4. **Build and Run**:
   - Select the `app` run configuration in the top toolbar.
   - Click the **Run** button (`Shift + F10` on Windows/Linux, `Control + R` on macOS).

---

### Option 2: Using the Command Line (Gradle Wrapper)

1. Open a terminal and navigate to the project root directory:
   ```bash
   cd MrD_Assessment
   ```

2. **Build Debug APK**:
   ```bash
   ./gradlew assembleDebug
   ```

3. **Install & Run on Connected Device/Emulator**:
   ```bash
   ./gradlew installDebug
   ```

4. **Run Unit Tests**:
   ```bash
   ./gradlew test
   ```

---

## 📝 Notes

- **Mock Network Data**: The app runs an embedded `MockWebServer` locally that simulates network latency (5s delay) and returns JSON data from bundled resource assets (`core/network/src/main/resources/data/`).
- **Database Schema**: The Room database is configured with `fallbackToDestructiveMigration(dropAllTables = true)`.
