import "@ui5/webcomponents/dist/Dialog.js";
import "@ui5/webcomponents/dist/Input.js";
import "@ui5/webcomponents/dist/TextArea.js";
import "@ui5/webcomponents/dist/Button.js";
import "@ui5/webcomponents/dist/Label.js";
import { ajvResolver } from "@hookform/resolvers/ajv";
import React, { useState } from "react";

import { useForm } from "react-hook-form";
import { taskService } from "./apis/task.api";
import { Task, taskSchema } from "./models/task.model";

interface CreateTaskDialogProps {
    open: boolean;
    onCreate: (newTask: Task) => void;
    onCancel: () => void;
}

const CreateTaskDialog: React.FC<CreateTaskDialogProps> = (
    { open, onCreate, onCancel }
) => {
    const [error, setError] = useState<string | null>(null);
    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm<Omit<Task, "id" | "created" | "finished">>({
        defaultValues: { name: "", description: "" },
        resolver: ajvResolver(taskSchema),
    });

    const onSubmit = async (data: Omit<Task, "id" | "created" | "finished">) => {
        try {
            const newTask = await taskService.createTask(data);
            onCreate(newTask);
            reset();
        } catch (error) {
            setError("Error creating task: " + error)
        }
    };

    return (
        <ui5-dialog
            open={open}
            header-text="Create Task"
            style={{ minWidth: "500px" }}
            onAfterClose={() => {
                reset();
                onCancel();
            }}
        >
            <form
                style={{ width: "100%", display: "grid", gap: "1rem" }}
                onSubmit={handleSubmit(onSubmit)}
            >
                <table style={{ width: "100%", borderSpacing: "0.5rem" }}>
                    <tbody>
                    <tr>
                        <td>
                            <ui5-label htmlFor="taskName" required>Task Name</ui5-label>
                        </td>
                        <td>
                            <ui5-input
                                id="taskName"
                                {...register("name")}
                            />
                            {errors.name && <span style={{ color: "red" }}>{errors.name.message}</span>}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <ui5-label htmlFor="taskDescription">Description</ui5-label>
                        </td>
                        <td>
                            <ui5-textarea
                                id="taskDescription"
                                {...register("description")}
                            />
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div slot="footer" style={{ display: "flex", justifyContent: "flex-end", gap: "1rem", padding: "1rem" }}>
                    <ui5-button design="Emphasized" type="Submit">Create</ui5-button>
                    <ui5-button design="Transparent" onClick={onCancel}>Cancel</ui5-button>
                </div>
            </form>
        </ui5-dialog>
    );
}

export default CreateTaskDialog;
