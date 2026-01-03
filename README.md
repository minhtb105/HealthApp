
# 🩺 Health Tracker App (Android – Kotlin)

A production-oriented Android application demonstrating **offline-first** architecture, clean separation of layers, 
and modern Android development practices that helps users track **daily water intake**, **sleep duration**, and **BMI**.  
The app syncs seamlessly with **Firebase** when the network is available and works fully offline otherwise.

---

## 📱 Features

### 💧 Water Intake Tracking
- Quick add water amounts (+200ml, +300ml, etc.)
- Calculate total daily water intake
- Fully functional offline
- Automatic cloud sync when online

### 😴 Sleep Tracking
- Record sleep & wake-up time
- Calculate total sleep duration
- View sleep history by date
- Local cache with Firebase backup

### ⚖️ BMI Calculator
- Input height & weight
- Auto BMI calculation + WHO classification
- Save BMI history
- Works offline

### 🔐 Authentication (Guest-first)
- Guest-first experience (no forced login on app start)
- Firebase Authentication (Google Sign-In)
- Login required only for user-specific features
- Logout clears:
    - Firebase auth session
    - Local Room database
    - In-memory session state

---

## 🧠 Architecture & Design

### 📐 Architecture Overview

```
UI (Activity / Fragment)
        ↓
ViewModel (State & Logic)
        ↓
UseCase (Business Rules)
        ↓
Repository (Interface)
        ↓
Local (Room) | Remote (Firebase)
```

### 🎯 Key Principles
- UI only renders state
- ViewModel does NOT depend on Room or Firebase
- Local database is the **Single Source of Truth**
- Offline-first by design
- Easy to test, extend, and maintain

---

### 🧭 Navigation Architecture

- Single-Activity architecture
- Navigation Component + Fragment
- Bottom Navigation with preserved fragment state
- Session-aware UI rendering (guest vs logged-in)


## 🗂 Project Structure

```
com.example.healthapp
│
├── di/                     # Hilt modules
│   ├── AppModule.kt
│   ├── DatabaseModule.kt
│   ├── FirebaseModule.kt
│   ├── RepositoryModule.kt
│   └── TimeModule.kt
│
├── data/
│   ├── local/              # Room
│   │   ├── entity/
│   │   ├── dao/
│   │   └── HealthDatabase.kt
│   │
│   ├── remote/             # Firebase
│   │   └── FirebaseAuthService.kt
│   │
│   ├── mapper/
│   └── repository/
│
├── domain/
│   ├── model/
│   └── usecase/
│   └── repository/
│
├── ui/
│   ├── main/        # MainActivity, BottomNavigation
│   ├── home/        # HomeFragment
│   ├── profile/     # ProfileFragment
│   ├── auth/        # AuthActivity
│   └── water/       # Water tracking feature
│
├── utils/
│   ├── SystemTimeProvider.kt
│   ├── TimeProvider.kt
│   └── TimeUtils.kt
│
├── HealthApplication.kt
└── MainActivity.kt
```

---

## 🧩 Tech Stack

| Component            | Technology |
|---------------------|------------|
| Language             | Kotlin |
| Architecture         | MVVM + Clean Architecture |
| Dependency Injection | Hilt |
| Local Database       | Room |
| Async Handling       | Coroutines + StateFlow |
| Authentication       | Firebase Authentication |
| Cloud Database       | Firebase Firestore |
| Build System         | Gradle |

---

## 🔄 Offline-First Data Flow

### Adding Data (Water / Sleep / BMI)

```
User Action
   ↓
ViewModel
   ↓
UseCase
   ↓
Repository
   ↓
Room (local save)
   ↓
Firestore (sync when online)
```

### Offline Mode
- App reads data from Room
- User can fully interact with the app
- No UI blocking or crashes

### Back Online
- Repository automatically syncs local data to Firestore

---

## 🔐 Authentication Flow
- User logs in via Firebase Authentication
- Firebase UID is used as Firestore document key
- On logout:
    - Clear Room database
    - Reset UI state

---

## 🧪 State Management
UI state is modeled using immutable data classes and sealed classes:

- Loading
- Empty
- Success
- Error

State is exposed via StateFlow and updated using copy() to ensure immutability and predictable UI rendering.
---

## 🧠 Key Architectural Decisions

- **Derived data is not persisted**  
  BMI is calculated dynamically from height & weight records to avoid data inconsistency.

- **Room as Single Source of Truth**  
  UI always reads from local database. Firebase is used only for backup & synchronization.

- **User-scoped data isolation**  
  All local and remote data is scoped by Firebase UID via a SessionManager abstraction.

- **Strict layer separation**  
  Room entities, domain models, and UI models are separated using mapper classes.

## 📸 Screenshots

```
/screenshots
├── login.png
├── water.png
├── sleep.png
└── bmi.png
```

_(Add 4–6 screenshots for better presentation)_

---

## 🚀 How to Run

1. Clone the repository
2. Add `google-services.json`
3. Enable **Firebase Authentication (Email/Password)**
4. Enable **Cloud Firestore**
5. Run the app in Android Studio

---

## 🎯 What This Project Demonstrates

- Applying Clean Architecture in a real Android project
- Designing offline-first data flow
- Managing immutable UI state with StateFlow
- Reducing coupling using dependency injection (Hilt)
- Writing production-oriented code suitable for scaling

---

### 🚶 Step Tracking (Planned)

- Integrate with Google Fit / Health Connect API
- Read daily step count from system health data
- Merge step data into existing offline-first architecture
- Cache step data locally for fast UI rendering

### 🤖 Health Knowledge Assistant (RAG – Planned)

- Retrieval-Augmented Generation (RAG) chatbot
- Knowledge base built from trusted medical sources:
    - WHO
    - HealthCareMagic
- Provides **informational health guidance**, not medical diagnosis
- Clearly states that content is for reference only
- Designed with prompt & response safety constraints

## 🙋‍♂️ Author

- **Name:** minhtb105
- **Role:** Android Intern Candidate
- **Email:** kisuit4.0@gmail.com
- **GitHub:** https://github.com/minhtb105
