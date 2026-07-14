# 🛍️ Shopzi  

**Shopzi** is a sleek and modern shopping app built with **Kotlin** and **Jetpack Compose**, designed to deliver a fast and intuitive shopping experience. It supports category-based product browsing, cart management, promotional banners, and real-time data sync with Firebase.  

Alongside this buyer-facing app, a companion project — **[Shopzi Seller Dashboard](https://github.com/astronix1/Shopzi-Seller)** — is also available for sellers to manage their products, categories, and offers seamlessly. Together, they form a complete e-commerce ecosystem.  

---

## ✨ Features  

- 🗂️ **Product Categories** — Browse products organized into intuitive categories.  
- 🛒 **Cart Management** — Add, remove, and update items in your cart effortlessly.  
- 🧾 **Product Listings** — View detailed product cards with prices and images.  
- 📢 **Promotional Banners** — Dynamic home banners to showcase special deals.  
- 🔐 **Firebase Integration** — Real-time Firestore sync & Firebase Authentication.  
- ⚡ **Jetpack Compose UI** — Entirely built with modern declarative UI.  
- 🌙 **Lightweight & Responsive** — Smooth performance across all devices.  

---

## 🛠️ Tech Stack  

- **Language:** Kotlin  
- **UI Framework:** Jetpack Compose  
- **Backend:** Firebase (Authentication, Firestore)  
- **Image Loading:** Coil  
- **Navigation:** Navigation-Compose  
- **Design:** Material 3  
- **Build System:** Gradle Kotlin DSL  

---

## 🚀 Getting Started  

### Prerequisites  

- Android Studio **Hedgehog** or newer  
- Android SDK 33+  
- A Firebase project with **Authentication** and **Firestore** enabled  

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

## 🧑‍💼 Shopzi Seller Dashboard  

📦 **[Shopzi Seller Dashboard](https://github.com/astronix1/Shopzi-Seller)** is a companion app built for **sellers** to manage their store on Shopzi.  

### Key Features:  
- Add, update, or remove products  
- Organize items by categories  
- Manage promotional banners & offers  
- Real-time Firebase sync with buyer app  

This project complements **Shopzi**, enabling a full **buyer-seller ecosystem**.  

---

## 🤝 Contributing  

Contributions are welcome! Feel free to fork the repo and submit a pull request.  

---

## 📝 License & Usage  

This project is open for **personal or educational use**.  
For **commercial usage**, please contact the author for permission.  

---

## 🙋‍♂️ Author  

**Vishal Kumar**  
🛠️ Made with Jetpack Compose & Firebase  
