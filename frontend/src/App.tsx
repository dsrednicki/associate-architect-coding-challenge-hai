import React from 'react';
import { Routes, Route, Navigate } from "react-router-dom";
import Home from './components/Home/Home';
import Login from './components/Login/Login';
import { ProtectedRoute } from "./components/Login/ProtectedRoutes";
import { AuthProvider } from "./components/providers/AuthProvider";

const App: React.FC = () => {
  return (
    <AuthProvider>
    <Routes>
      <Route path="/" element={<ProtectedRoute> <Home /> </ProtectedRoute>} />
      <Route path="/login" element={<Login />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
    </AuthProvider>
  );
};

export default App;
