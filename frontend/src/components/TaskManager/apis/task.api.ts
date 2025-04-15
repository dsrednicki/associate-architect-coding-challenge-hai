import { Task } from "../models/task.model"

const BASE_URL = "http://localhost:8080/api/tasks";

export const taskService = {
    async getAllTasks(): Promise<Task[]> {
        const response = await fetch(BASE_URL);
        if (!response.ok) {
            throw new Error("Failed to fetch tasks");
        }
        return response.json();
    },

    async getTask(taskId: number): Promise<Task> {
        const response = await fetch(`${BASE_URL}/${taskId}`, {
            method: "GET",
            headers: {"Content-Type": "application/json"}
        });
        if (!response.ok) {
            throw new Error("Failed to fetch tasks");
        }
        return response.json();
    },

    async createTask(task: Omit<Task, "id" | "created" | "finished">): Promise<Task> {
        const response = await fetch(BASE_URL, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(task),
        });
        if (!response.ok) {
            throw new Error("Failed to create task");
        }
        return response.json();
    },

    async updateTask(task: Task): Promise<Task> {
        const response = await fetch(`${BASE_URL}/${task.id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(task),
        });

        if (!response.ok) {
            throw new Error("Failed to update task");
        }
        return response.json();
    },


    async deleteTask(taskId: number): Promise<void> {
        const response = await fetch(`${BASE_URL}/${taskId}`, {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
        });

        if (response.status !== 204) {
            throw new Error(`Failed to delete task with the id '${taskId}'`);
        }
    },
};
