
## Project Overview
The Meal App is designed to help users discover and explore various meals. It integrates with TheMealDB API (https://www.themealdb.com/api.php) to retrieve a wide array of meals.
The app uses a modern Android architecture following the **MVVM** and **Clean Architecture** principles.

- **Libraries Used:**
  - **Kotlin Coroutines**: For asynchronous operations
  - **Dagger Hilt**: For dependency injection
  - **Retrofit**: For making network requests to the API
  - **Glide**: For image loading (meal images)
  - **Jetpack Compose**: For building the UI declaratively
  - **Navigation Component**: For handling navigation between screens
  - **ViewModel & StateFlow**: For managing UI state and observing changes
  - **Network Caching**: Using OkHttp cache headers to manage API responses
  - **Chucker** : For debugging network requests
  - **Timber** : For a logging
