import { Navigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";

export const ProtectedRoute = ({ children }) => {
    const { userInfo } = useAuth();
    if (!userInfo) {
        return <Navigate to="/login" />;
    }
    return children;
};
