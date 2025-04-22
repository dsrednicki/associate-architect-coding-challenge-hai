import { UserInfo } from "../models/userinfo.model";

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/my-account`;

export const myAccountService = {
    async getUserInfo(): Promise<UserInfo> {
        const response = await fetch(API_URL, {
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
