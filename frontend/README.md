# Frontend - Task Management Tool

## 🚀 Overview
This is the **frontend** implementation of the **Task Management Tool** built with **React**, TypeScript, and UI5 Web Components. It provides an interface for user authentication, task creation, and management.

## 📂 Folder Structure
```
frontend/
│── cypress/               # End-to-end testing
│── public/                # Static assets
│── src/                   # Application source code
│   │── assets/            # Static assets (images, styles, etc.)
│   │── components/        # Reusable UI components
│   │   │── login/         # Login components
│   │   │── home/          # Home page components
│   │   │── taskmanager/   # Task management UI components
│   │── App.tsx           # Main application component
│   │── index.tsx         # Entry point
│── package.json          # Dependencies and scripts
│── README.md             # Project documentation
```

## 🛠️ Technologies Used
- **React** (v19) - UI Library
- **React Router** (v7) - Client-side routing
- **UI5 Web Components** - UI framework
- **TypeScript** - Type safety and maintainability
- **AJV** - JSON Schema validation
- **Cypress** - End-to-end testing
- **Jest & Testing Library** - Unit testing

## 📜 Installation & Setup
### 1️⃣ Prerequisites
Ensure you have **Node.js (>= 16.x.x)** and **npm (>= 8.x.x)** installed.

### 2️⃣ Clone Repository
```sh
git clone https://github.com/your-repo/frontend.git
cd frontend
```

### 3️⃣ Install Dependencies
```sh
npm install
```

### 4️⃣ Run the Development Server
```sh
npm start
```
The application will be available at **http://localhost:3000/**.

### 5️⃣ Build for Production
```sh
npm run build
```

### 6️⃣ Run Tests
#### End-to-End (E2E) Tests with Cypress
```sh
npm run cypress:open
```

#### AJV Error : Cannot find module 'ajv/dist/compile/codegen
The error "Cannot find module 'ajv/dist/compile/codegen'" typically occurs due to a mismatch or corruption in dependencies
You can fix it by:
Clear and Reinstall Dependencies
```sh
rm -rf node_modules package-lock.json
npm cache clean --force
npm install

npm install ajv@latest
```

## 🎯 Milestones
### ✅ Milestone 1: Receive Data from Backend
- Fetch task data from the backend service.
- Display task data in a simple list.

### ✅ Milestone 2: Implement User Authentication
- Create login and logout functionality.
- Authenticate users using JWT.

### ✅ Milestone 3: Implement Task Management Features
- Create new tasks and persist them in the backend.
- Edit and delete existing tasks.

### ✅ Milestone 4: Handle Errors & Validation
- Implement client-side validation using AJV.
- Display validation errors to the user.

### ✅ Milestone 5: Implement UI Enhancements & Modularize the Frontend for Scalability
- Refactor the application so that key features (e.g., authentication, task management) are self-contained modules.
- Add loading states and error handling in the UI.


---
🚀 Happy Coding! 🎯
