import { useState } from "react";

export function useLocalStorage<T>(keyName: string, defaultValue: T): [T, (newValue: T) => void] {
    const [storedValue, setStoredValue] = useState<T>(() => {
        try {
            const item = window.localStorage.getItem(keyName);
            if (item !== null) {
                return JSON.parse(item) as T;
            } else {
                window.localStorage.setItem(keyName, JSON.stringify(defaultValue));
                return defaultValue;
            }
        } catch (err) {
            console.warn(`Error reading localStorage key "${keyName}":`, err);
            return defaultValue;
        }
    });

    const setValue = (newValue: T): void => {
        try {
            window.localStorage.setItem(keyName, JSON.stringify(newValue));
            setStoredValue(newValue);
        } catch (err) {
            console.error(`Error setting localStorage key "${keyName}":`, err);
        }
    };

    return [storedValue, setValue];
}
