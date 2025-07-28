# Response to Android Coding Challenge
The objective was to build a native android application to show a list of restaurants and a detail page for each restaurant.

## Components/Libraries
The following components have been used to complete the assignment:
- Kotlin language
- Jetpack compose for UI implementation
- MVVM Architecture with Repository Pattern
- Hilt for dependency injection
- Navigation component for navigating from one screen to another
- Retrofit for network request
- Kotlin Serialisation
- Coroutines for IO operations
- Kotlin Flow
- Unit Tests

## Improvements
I have completed the coding challenge using the MVVM architecture. The app consists of two screens: one displaying a list of restaurants and another showing the details of each restaurant. The navigation between these screens is handled using the Navigation Component. To share the restaurant listing data between these screens, I have utilized a nested graph and a shared ViewModel. The shared ViewModel also supports displaying the list and details when a restaurant item is selected.

For dependency injection, Hilt has been implemented. The Retrofit service is injected into the repository, along with a dispatcher for executing data fetch requests from the server. 

In the ViewModel, I have managed the view state using `StateFlow` and handle single events like showing snack bars with `SharedFlow`. The view state includes a boolean for showing or hiding a circular progress bar during data loading, the list of restaurants from the response, and the selected item for the detail screen.

Compose Previews have been added to visualize the UI components. Since this is a small project, I have written a few unit tests to validate the ViewModel logic.
