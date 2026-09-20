# Assessment Solution & Technical Responses

---

## 1. How would you split modules and share components?

Modularization is key to maintaining scalable, maintainable, and build-efficient Android applications. The project is structured into clear architectural layers:

### 🧩 **Core Modules (`:core:*`)**
Define the fundamental infrastructure and reusable capabilities that the entire application is built on:
- **`:core:network`**: API service definitions, HTTP clients, and MockWebServer data dispatchers.
- **`:core:datastore`**: Room database, DAOs, entities, and Jetpack DataStore preferences.
- **`:core:navigation`**: Centralized navigation routes, arguments, and screen contracts.
- **`:core:ui`**: Common UI components, design tokens, colors, typography, and base state containers.
- **`:core:string`**: Centralized string resources for localization.

### 📦 **Data Modules (`:data:*`)**
Encapsulate the business logic and repository pattern:
- **`:data:restaurant`**: Handles restaurant repositories, use cases (e.g., retrieving restaurant lists, marking favourites), and RemoteMediators.
- **`:data:menu`**: Handles restaurant menu use cases and network data requests.

### 🎨 **Feature Modules (`:feature:*`)**
Contain user-facing UI screens and screen-level ViewModels:
- **`:feature:home:restaurantTab`**: Restaurant list screen powered by Paging 3.
- **`:feature:home:favouriteTab`**: Favourite restaurants list tab.
- **`:feature:restaurant:detail`**: Restaurant menu and detail view.
- **`:feature:home:common`**: Shared UI components specific to home features (e.g., `RestaurantItem`).

### 📱 **App Module (`:app`)**
The entry point of the Android application that wires dependencies together, initializes global libraries (Hilt, MockWebServer), and sets up root navigation graphs (`NavHost`). 

> **Future Extensibility**: By isolating feature logic from application entry points, adding support for other platforms (e.g., Wear OS, Android TV) requires simply adding a new platform module while reusing the existing `:core`, `:data`, and `:feature` modules.

---

## 2. How would you approach testing for key parts of this feature?

A multi-layered testing strategy ensures reliability, correctness, and regression prevention:

1. **Unit Tests**:
   - Verify Use Cases and Repository logic independently of the UI framework.
   - Test data mapping (Entities to Domain Models) and caching policies in Room/DataStore.
2. **UI / Component Tests (Jetpack Compose Testing)**:
   - Verify UI state rendering under various conditions (Loading, Success, Empty, Error).
   - Test interactive behaviors, such as disabling buttons during asynchronous operations and toggling favourite state icons.
3. **Integration Tests**:
   - Validate interactions between `data` and `core` layers (e.g., testing Room DAOs with an in-memory database or verifying `RemoteMediator` integration with `PagingSource`).
4. **End-to-End (E2E) Tests**:
   - Verify complete user flows from launch to menu browsing using `MockWebServer` to validate network request handling and screen transitions.

---

## 3. How would you keep the UI smooth as the list grows much larger?

To maintain a smooth 60/120 FPS scrolling experience with large datasets:

- **Pagination (Paging 3)**: Fetch data in smaller, fixed-size chunks (pages) on demand rather than loading entire lists into memory at once.
- **Lazy Rendering (`LazyColumn`)**: Compose's `LazyColumn` efficiently reuses and renders only the items currently visible in the viewport.
- **Stable Keys**: Provide unique keys (`key = { it.id }`) in `LazyColumn` items to optimize Compose diffing and avoid unnecessary recompositions.
- **Performance Profiling & Monitoring**:
  - **Compose Compiler Reports & Layout Inspector**: Identify unstable parameters and unexpected recomposition loops.
  - **Android Studio Profilers & Perfetto**: Track memory allocations, GC pauses, and frame rendering jank during scrolling.

---

## 4. How would you incorporate AI-assisted development productively while maintaining quality?

AI tools enhance developer productivity when paired with strict engineering guardrails:

- **Automated PR / MR Code Reviews**:
  - Integrate AI bots into GitHub Actions / GitLab CI to flag hardcoded strings, architecture rule violations, missing test coverage, or edge-case bugs prior to human review.
- **Figma Design-to-Code Assistance**:
  - Use AI assistants to parse Figma components and generate baseline Jetpack Compose layouts, reducing manual effort on layout boilerplate, spacing, and typography matching.
- **Boilerplate & Test Generation**:
  - Leverage AI to draft unit test suites and mock data fixtures, allowing engineers to focus on core business logic and code quality.
