🩺 Health Tracker App (Android – Kotlin)

Health Tracker App is an Android application that helps users track daily water intake, sleep duration, and BMI.
The app is designed with an offline-first approach, synchronizing data with Firebase when the network is available.

This project demonstrates clean architecture, MVVM, Dependency Injection, and real-world data handling, suitable for Android/Kotlin Intern or Junior positions.

📱 Features
💧 Water Intake Tracking

Quick add water amounts (+200ml, +300ml, etc.)

Calculate total daily water intake

Fully functional offline

Automatic sync to cloud when online

😴 Sleep Tracking

Record sleep time and wake-up time

Calculate total sleep duration

View sleep history by date

Local cache with Firebase backup

⚖️ BMI Calculator

Input height and weight

Calculate BMI and classification

Save BMI history

Results available even when offline

🔐 User Authentication

Login / Logout with Email & Password (Firebase Auth)

User-specific data isolation

Logout clears local database and UI state

🧠 Architecture & Design Thinking
📐 Architecture Overview

The app follows MVVM + Clean Architecture + Repository pattern:

UI (Activity / Fragment)
↓
ViewModel (State & Logic)
↓
UseCase (Business Logic)
↓
Repository (Interface)
↓
Local (Room) | Remote (Firebase)

🎯 Key Principles

UI only renders state

ViewModel does NOT depend on Room or Firebase

Local database is the Single Source of Truth

Offline-first by design

Easy to test, extend, and maintain

🗂 Project Structure
com.example.healthapp
│
├── di/                     # Hilt modules
│   ├── AppModule.kt
│   ├── DatabaseModule.kt
│   └── FirebaseModule.kt
│   └── RepositoryModule.kt
│   └── TimeModule.kt
│
├── data/
│   ├── local/              # Room
│   │   ├── entity/
│   │   ├── dao/
│   │   └── HealthDatabase.kt
│   │
│   ├── remote/             # Firebase
│   │   └── FirebaseService.kt
│   │
│   ├── mapper/
│   └── repository/
│
├── domain/
│   ├── model/
│   └── usecase/
│
├── ui/
│   ├── water/
│   ├── sleep/
│   └── bmi/
│
├── utils/
│   ├── SystemTimeProvider.kt
│   ├── TimeProvider.kt
│   └── TimeUtils.kt
│
└── HealthApplication.kt
└── MainActivity.kt

Clear separation of layers makes the codebase easy to review and scale.

🧩 Tech Stack
Component	Technology
Language	Kotlin
Architecture	MVVM + Clean Architecture
Dependency Injection	Hilt
Local Database	Room
Async Handling	Coroutines + StateFlow
Authentication	Firebase Authentication
Cloud Database	Firebase Firestore
Build System	Gradle
🔄 Data Flow (Offline-First)
Adding Data (Water / Sleep / BMI)
User Action
↓
ViewModel
↓
UseCase
↓
Repository
↓
Room (save locally)
↓
Firestore (sync when online)

Offline Mode

App reads data from Room

User can fully interact with the app

No UI blocking or crashes

Back Online

Repository automatically syncs local data to Firestore

🔐 Authentication Flow

User logs in via Firebase Authentication

Firebase UID is used as the Firestore document key

On logout:

Clear Room database

Reset UI state

🧪 State Management & Error Handling

UI state is managed using StateFlow:

Loading

Empty

Success

Error

UI does not handle business logic — it only reacts to state changes.

📸 Screenshots
/screenshots
├── login.png
├── water.png
├── sleep.png
└── bmi.png


(Add 4–6 screenshots for better presentation)

🚀 How to Run the Project

Clone the repository

Add google-services.json

Enable Firebase Authentication (Email/Password)

Enable Cloud Firestore

Run the app on Android Studio

🎯 Learning Goals

Apply MVVM correctly in a real project

Use Hilt for dependency management

Design an offline-first Android app

Work with Firebase Authentication & Firestore

Write clean, readable, and maintainable code

🙋‍♂️ Author
Name: minhtb105
Role: Android Intern Candidate
Email: kisuit4.0@gmail.com
GitHub: https://github.com/minhtb105