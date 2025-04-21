import { createContext, ReactNode } from "react";
import { useNavigate } from "react-router-dom";
import { useLocalStorage } from "../../hooks/useLocalStorage";
import { myAccountService } from "../Home/apis/myaccount.api";
import { UserInfo } from "../Home/models/userinfo.model";
import { authService } from "../Login/apis/login.api";

export interface AuthContextType {
    userInfo: UserInfo | null;
    login: (username: string, password: string) => Promise<void>;
    logout: () => Promise<void>
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
    const [userInfo, setUserInfo] = useLocalStorage<UserInfo | null>("user", null);
    const navigate = useNavigate();

    const login = async (username: string, password: string) => {
        await authService.login(username, password)
        await myAccountService.getUserInfo().then(setUserInfo).catch(() => {
            navigate("/login");
        });
        navigate("/");
    };

    const logout = async () => {
        setUserInfo(null);
        await authService.logout()
        navigate("/login");
    };

    return <AuthContext.Provider value={{
        userInfo,
        login,
        logout,
    }}>{children}</AuthContext.Provider>;
};
