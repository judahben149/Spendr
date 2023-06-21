# Spendr
Spendr is a neat-looking budgeting app based on the neumorphic design style.
Built with Modern Android Development practices in mind, it adheres to clean architecture and keeps up with latest industry standards.

<!-- ## About the App
Spendr has a focus on simplicity and  -->

## Features
* Clean and simple UI.
* Create notification reminders for intending payments.
* Dark mode.
* Visualize expenses with charts.
* Export single budget entry or entire budget.

### Check out Spendr
[<img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" width="240">](https://play.google.com/store/apps/details?id=com.judahben149.spendr) 

<!-- [<img alt="Get it on F-Droid" src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" width="240">](https://f-droid.org/packages/me.zhanghai.android.files) -->

## Prerequisites
* Android Studio Flamingo | 2022.2.1
* Min SDK 23
* Target SDK 33
* Java 17
* AGP 8.0.0
* Kotlin 1.8.0


## Setup 🛠️
1. Clone the repository ```git clone https://github.com/judahben149/Spendr.git```
2. Open in Android Studio.
3. Configure prerequisites if any errors.
4. Sync the project.

## Build Tools and Patterns
* [Kotlin](https://kotlinlang.org/docs/android-overview.html) - 100% kotlin.

* [Neumorphism UI](https://github.com/fornewid/neumorphism) - Provides neumorphic-style views and UI elements.

* [MVVM](https://developer.android.com/topic/architecture) - Employs the officially recommended MVVM design pattern.

* [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) - Uses clean architecture to structure and abstract code, encouraging reusability.

* [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Simplifies managing and providing dependencies which promotes modularity and testability.

* [Navigation Component](https://developer.android.com/guide/navigation/get-started) - Simplifies screen transitions, handles navigation automatically to provide a consistent user experience.

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) & [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Presenter for persisting view state across config changes. LiveData is used to observe this state.

* [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Library support for coroutines, a lightweight framework for asynchronous programming in Kotlin.

* [Kotlin Flows](https://kotlinlang.org/docs/flow.html) - A reactive stream library in Kotlin for handling asynchronous data streams.

* [Room persistence library](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite for working with databases

* [Paging-3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Library for retrieving data in chunks/pages from either network or database, database in this case.

* [ViewPager-2](https://developer.android.com/reference/kotlin/androidx/viewpager2/widget/ViewPager2) - Used for creating swipeable screens and slide-based UIs in Android.

* ViewPager Dot Indicator - Library that adds a stylish dot indicator to ViewPager screens, mostly for visual appeal.
 
* [Epoxy](https://airbnb.io/projects/epoxy/) - Simplifies the creation of complex RecyclerView layouts by providing a more declarative approach. Great for handling dynamic data as well.

* [Lottie Animations](https://airbnb.io/projects/lottie-android/) - Enables seamless integration of vector-based animations.

* [MP Charts](https://github.com/PhilJay/MPAndroidChart) - Powerful charting library for Android. Supports various chart types with extensive customization.

* [Alarm Manager](https://developer.android.com/reference/android/app/AlarmManager) - Android component for scheduling time-based tasks and events.

* [Braodcast Receiver](https://developer.android.com/reference/android/content/BroadcastReceiver) -  Used to listen for system-wide or app-specific events.

* [Splash Screen API](https://developer.android.com/develop/ui/views/launch/splash-screen) - Provides a seamless and consistent way of applying splash screens in app.

* [Timber](https://github.com/JakeWharton/timber) - Lightweight and flexible logging library.

* [Shared Preferences](https://developer.android.com/reference/android/content/SharedPreferences) - Lightweight storage mechanism in Android for storing key-value pairs, such as user preferences, settings, and application state.

* [Preferences API](https://developer.android.com/reference/androidx/preference/package-summary) - Convenient API for managing and persisting user preferences in Android applications, particularly in settings screens.

* [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics) - A crash reporting tool for tracking and analyzing real-time app crashes.

* [Firebase Performance Monitor](https://firebase.google.com/docs/perf-mon) - A powerful tool for measuring, analyzing, and optimizing app performance.


## Architecture/Flow
![Spendr Architecture Flow](https://github.com/judahben149/Spendr/assets/71103838/6305b5e1-c617-4d7f-ba4a-1f06307ad008)


## Screenshots
### Onboarding Section
<img src="https://github.com/judahben149/Spendr/assets/71103838/ca5f11ed-5d67-410f-82be-6e3c7904ad2c" width="200" alt="Splash screen">
<img src="https://github.com/judahben149/Spendr/assets/71103838/8c7bd4ef-9b81-4ca7-84f3-9008da7392a9" alt="Onboarding 1 screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/9d65c1b2-1e11-44e5-8ac6-06c6e90d490e" alt="Onboarding 2 screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/2ff69598-8115-4d14-b291-749b4f3f76dc" alt="Onboarding 3 screen" width="200">

### Home Screen
<img src="https://github.com/judahben149/Spendr/assets/71103838/d5df7c84-2640-4c1e-89a2-713f55e7af82" alt="Home screen" width="200">

### Overview Section
<img src="https://github.com/judahben149/Spendr/assets/71103838/ea35cc6f-d21b-4d89-8794-29ec5b803287" alt="Overview: Summary screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/d0d12536-2692-407f-b02a-85413e235b8c" alt="Income list screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/2001d05e-f558-4ae5-a01a-ee2f2ba9c78a" alt="Expenditure list screen" width="200">

### Activity Section
<img src="https://github.com/judahben149/Spendr/assets/71103838/cc233eed-b311-406c-bba5-3d0ee9433d12" alt="Activity - Expenditure tab screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/6635a119-1aba-4b4d-bd4d-8fda27197161" alt="Activity - Income tab screen" width="200">

### Budget Section
<img src="https://github.com/judahben149/Spendr/assets/71103838/351f75b4-8403-4352-83d0-9c2c338a1672" alt="Budget screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/6b34eddb-8d8d-413b-8d58-894f6e9d9e09" alt="Visualizer screen" width="200">

### Extras Section
<img src="https://github.com/judahben149/Spendr/assets/71103838/df13e14a-e3c3-40a1-a331-bd8e9bf4aa9c" alt="Extras screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/245d3130-1c86-42e9-b092-e8017680020b" alt="New reminder dialog screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/e26eb467-b1a4-44ab-a89d-558cf387200a" alt="New reminder - choose date screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/1630953e-430c-425b-bc56-9038d66bee93" alt="New reminder choose time screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/bc824926-5552-4565-9e47-670cb6859334" alt="Reminder list screen" width="200">

### New Entry Section - Floating Action Button
<img src="https://github.com/judahben149/Spendr/assets/71103838/741c904a-81d9-4fad-85cc-70d5cce1292b" alt="New entry screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/e22eee78-49ed-479a-b7a4-225d64bb8816" alt="New entry - choose date screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/f48ea073-43c2-4382-a25d-956f2e2f4b16" alt="New entry - choose category screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/c4950ed1-10da-4cad-8513-a6412b054d76" alt="New entry - create category screen" width="200">

### Settings Section
<img src="https://github.com/judahben149/Spendr/assets/71103838/cf717ceb-b8b5-4057-852b-dbadf7b8c0dc" alt="Settings screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/4bc9e42b-c801-4fcf-aa3f-d8d32e837e8a" alt="Delete reminders dialog screen" width="200">
<img src="https://github.com/judahben149/Spendr/assets/71103838/b75e95d4-5f83-45d4-8c55-630d1cdfc3ba" alt="Delete budget entries dialog screen" width="200">

 
 ‎
## Spendr Application Flow Diagram
![Spendr - Flow](https://github.com/judahben149/Spendr/assets/71103838/d41bf832-adaa-4a3b-9332-c4f0feea142a)


 ‎
