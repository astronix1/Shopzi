
# 🛍️ Shopzi

**Shopzi** is a sleek and modern shopping app built with **Kotlin** and **Jetpack Compose**, designed for a fast and intuitive user experience. It supports category-based product listings, cart management, and seamless Firebase integration for real-time data.

---

## ✨ Features

- 🗂️ **Product Categories** — Browse products organized into intuitive categories.
- 🛒 **Cart Management** — Add, remove, and update items in your cart effortlessly.
- 🧾 **Product Listings** — See detailed product cards with price and image.
- 📢 **Promotional Banners** — Dynamic home banners for special deals.
- 🔐 **Firebase Integration** — Includes Firestore and Firebase Authentication.
- ⚡ **Jetpack Compose UI** — Built entirely with modern declarative UI.
- 🌙 **Lightweight & Responsive** — Smooth performance on all screen sizes.

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Backend:** Firebase (Authentication, Firestore)
- **Build System:** Gradle Kotlin DSL
- **Others:** Coil (for image loading), Material 3, Navigation-Compose

---


## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Android SDK 33+
- A Firebase project with Authentication and Firestore enabled

### Firebase Setup

1. Create a Firebase project in [Firebase Console](https://console.firebase.google.com/)
2. Enable **Email/Password Authentication**
3. Create a **Firestore Database**
4. Download the `google-services.json` file and place it in:
   ```
   app/google-services.json
   ```

### Clone & Run

```bash
git clone https://github.com/astronix1/shopzi.git
cd shopzi
```

Open in Android Studio and click **Run** ▶️

---

## 📁 Project Structure

```
Shopzi/
│
├── app/
│   ├── components/         # Reusable UI components (CartItem, Banner, etc.)
│   ├── model/              # Data models (ProductModel, CategoryModel, etc.)
│   ├── screens/            # Composable screen UIs
│   ├── AppUtil.kt          # Utility functions
│   ├── AppNavigation.kt    # Navigation graph
│   └── MainActivity.kt     # Main entry point
│
├── build.gradle.kts
├── settings.gradle.kts
└── ...
```

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repo and submit a pull request.

---

## 📝 Usage

Feel free to fork or reference this project for personal or educational purposes.  
Commercial use is not permitted without permission.

---

## 🙋‍♂️ Author

**Vishal Kumar**  
🛠️ Made with Jetpack Compose and Firebase

---
