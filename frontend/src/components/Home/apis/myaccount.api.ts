import { UserInfo } from "../models/userinfo.model";

const BASE_URL = "http://localhost:8080/api/my-account";

export const myAccountService = {
    async getUserInfo(): Promise<UserInfo> {
        const response = await fetch(BASE_URL, {
            method: "GET",
            headers: {"Content-Type": "application/json"},
            credentials: "include",
        });
        if (!response.ok) {
            throw new Error("Failed to fetch user information");
        }
        return response.json();
    },
};
