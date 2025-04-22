const LOGIN_URL = `${process.env.REACT_APP_API_BASE_URL}/login`;
const LOGOUT_URL = `${process.env.REACT_APP_API_BASE_URL}/logout`;

export const authService = {
    async login(username: string, password: string): Promise<void> {
        const response = await fetch(LOGIN_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({
                username: username,
                password: password,
            }),
            credentials: "include",
        });

        if (!response.ok) {
            throw new Error("Failed to login");
        }
    },

    async logout(): Promise<void> {
        const response = await fetch(LOGOUT_URL, {
            method: "GET",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            credentials: "include",
        });

        if (!response.ok) {
            throw new Error("Failed to login");
        }
    },
};
