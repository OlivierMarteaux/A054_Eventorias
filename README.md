# 📱 Eventorias - a Special Events Management app


## 🚀 About the Project

Eventorias is an Android application dedicated to events management. It allows its users to browse, read and add some special events to be gathered with community.
The application is build following modern Android coding standard (Kotlin language, Jetpack Compose layout and MVVM architecture).
It uses Firebase as autenthication, database and media storage functions.

The project was developed as part of an educational and demonstration initiative showcasing how to structure Android apps with strong architectural boundaries.

[Check the preview video on my Dev Freelance Site](https://oliviermarteaux.dev/projects/eventorias)


## ✨ Features

👤 User Authentication – Login, registration, and password reset via Firebase Auth.

🏠 Special Events Feed – Displays community posts dynamically updated from Firestore.

➕ Add Events – Create and publish new events posts with all details.

⚙️ Settings & Profile – Manage personal information and preferences.

🔔 Push Notifications – Powered by Firebase Messaging.

🧭 Navigation Graph – Declarative, type-safe navigation between screens.

🌙 Material 3 UI Design – Fully Compose-based adaptive theming with rounded shapes, elevation, and animations.


## 🧰 Tech Stack

| Layer                  | Technology                                         |
|------------------------|----------------------------------------------------|
| Language               | Kotlin                                             |
| UI                     | Jetpack Compose, Material 3                        |
| Architecture           | MVVM, ViewModel, State Management                  |
| Navigation             | Jetpack Navigation Compose                         |
| Authentication         | Firebase Auth, Google Credential                   |
| Backend Storage        | Firebase FireStore and Storage                     |
| Push notifications     | Firebase Cloud Messaging and Preferences DataStore |
| Crash Analyses         | Firebase CrashLytics                               |
| Background Work        | Kotlin Coroutines, Flows                           |
| Image Handling         | URI-based storage in internal memory using Coil    |
| Image Capture          | Camera X                                           |
| Static map display     | GoogleMaps API                                     |
| Dependency Injection   | Dagger/Hilt                                        |
| Build                  | Gradle (KTS)                                       |
| Testing                | JUnit4, MockK, Espresso, Cucumber, Jacoco          |
| Continuous Integration | SonarQube, GitHub Action                           |
| Continuous Delivery    | Firebase App Distribution, GitHub Action           |


## 📂 Project Structure

```
A054_Eventorias/
├── data/
│   ├── repository/           # Repositories wrapping data sources
│   ├── service/              # Firebase API implementations (PostApi, UserApi)
│   └── mapper/               # DTO ↔ Domain model mappers
├── domain/
│   ├── model/                # Business entities (Post, User, Comment)
│   └── usecase/              # (Future) domain logic
├── ui/
│   ├── screen/               # Compose screens (login, home, detail, etc.)
│   ├── navigation/           # NavGraph and route definitions
│   └── components/           # Shared Compose UI components
├── di/                       # Hilt modules and providers
├── EventoriasApp.kt          # Top-level composable
├── EventoriasApplication.kt
└── MainActivity.kt
```

<!--
## 📸 Screenshots

Coming soon.



Visit the following link to browse screenshots of the Hexagonal Games application:  
🔗 [Hexagonal Games App Screenshots](screenshots/)
-->

<!--
## 📲 Install

Coming soon.



To install the Eventorias application on your physical Android device:

1. **Download the APK from your smartphone**
   - Go to the [Releases](https://github.com/OlivierMarteaux/A052_HexagonalGames/releases) section of this repository.
   - Download the latest ` HexagonalGames.apk ` file.

2. **Enable Unknown Sources**
   - On your device, go to `Settings` > `Security`.
   - Enable **Install from unknown sources** (you can disable it again after installation).

3. **Install the APK**
   - Use a file explorer app on your device to locate the APK file.
   - Tap on it and follow the prompts to install the app.

4. **Launch the App**
   - Once installed, open the app from your launcher and start using Hexagonal Games!

> ⚠️ Note: You may need to allow permissions during the first launch.

--> 

<!--
## ⚙️ Setup

Coming soon.


⚙️ Setup

1. Create a Firebase project and enable:

   - Authentication (Email/Password)

   - Firestore Database

   - Cloud Storage

   - Cloud Messaging (optional)

2. Download google-services.json and place it in
app/src/main/

3. (Optional) Set your own API keys in local.properties or a secure Gradle config – never commit secrets!

4. Build & Run 🚀
--> 

## 👨‍💼 Author

_Olivier Marteaux_  
Former aerospace engineer turned Android developer.

Read more about my transition on [LinkedIn](https://linkedin.com/in/olivier-marteaux).  
Check out my journey and projects:
- 📱 [My Dev Freelance Site](https://oliviermarteaux.dev)
- 🔗 [Google Developer Profile](https://g.dev/OlivierMarteaux)
- 💻 [GitHub Projects](https://github.com/OlivierMarteaux)
- 📢 [LinkedIn Post – Career Change](https://www.linkedin.com/posts/olivier-marteaux_androidbasics-careerchange-androiddevelopment-activity-7351370158369628164-FmqZ?utm_source=share&utm_medium=member_desktop&rcm=ACoAACynrz8BkrhJFrStq3CEX6rQIEfnG7goFdg)


## 🤝 Acknowledgments

Special thanks to OpenClassrooms for providing the educational framework, and to the open-source community for libraries that make modern Android development elegant.

- [OpenClassrooms Android Pathway](https://openclassrooms.com/fr/paths/527/projects/1647/1900-mission---option-b,-scenario-fictif---creez-une-application-android-complexe)
- [Google Android Basics](https://developer.android.com/courses/android-basics-compose/course)
- JetBrains & Jetpack Compose Community


## 📄 License

This project is for educational and demonstration purposes. Not licensed for commercial use. For inquiries, please contact me.

---