const LOGIN_URL = "http://localhost:8080/api/login";
const LOGOUT_URL = "http://localhost:8080/api/logout";

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
