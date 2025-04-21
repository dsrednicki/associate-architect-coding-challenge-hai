import "@ui5/webcomponents/dist/Dialog.js";
import "@ui5/webcomponents/dist/Input.js";
import "@ui5/webcomponents/dist/TextArea.js";
import "@ui5/webcomponents/dist/Button.js";
import "@ui5/webcomponents/dist/Label.js";
import React, { useEffect, useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { ajvResolver } from "@hookform/resolvers/ajv"
import { taskService } from "./apis/task.api";
import { Task, taskSchema } from "./models/task.model";

interface EditTaskDialogProps {
    open: boolean;
    task: Task | null;
    onSave: (updatedTask: Task) => void;
    onCancel: () => void;
}

const EditTaskDialog: React.FC<EditTaskDialogProps> = (
    { open, task, onSave, onCancel }
)=> {
    const [error, setError] = useState<string | null>(null);
    const { register, handleSubmit, reset, formState: { errors } } = useForm<Task>({
        resolver: ajvResolver(taskSchema),
    });

    useEffect(() => {
        if (open && task) {
            reset({
                id: task.id,
                name: task.name || "",
                description: task.description || "",
                created: task.created ? new Date(task.created).toLocaleString() : "",
                finished: task.finished ? new Date(task.finished).toLocaleString() : "",
            });
        }
    }, [open, task, reset]);

    const onSubmit: SubmitHandler<Task> = async (formData) => {
        if (task) {
            const updatedTask: Task = { ...task, name: formData.name, description: formData.description };
            try {
                const result = await taskService.updateTask(updatedTask);
                onSave(result);
            } catch (error) {
                setError("Error updating task: " + error)
            }
        }
    };

    return (
        <ui5-dialog
            open={open}
            header-text="Edit Task"
            style={{ minWidth: "500px" }}
            onAfterClose={onCancel}
        >
            <form
                style={{ width: "100%", display: "grid", gap: "1rem" }}
                onSubmit={handleSubmit(onSubmit)}
            >
                <table style={{ width: "100%", borderSpacing: "0.5rem" }}>
                    <tbody>
                    <tr>
                        <td>
                            <ui5-label htmlFor="taskId">Task ID</ui5-label>
                        </td>
                        <td>
                            <ui5-input
                                id="taskId"
                                value={task?.id?.toString() ?? ""}
                                {...register("id", {disabled: true})}
                                readonly
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <ui5-label htmlFor="taskCreated">Created</ui5-label>
                        </td>
                        <td>
                            <ui5-input
                                id="taskCreated"
                                value={task?.created ? new Date(task.created).toLocaleString() : ""}
                                {...register("created", {disabled: true})}
                                readonly
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <ui5-label htmlFor="taskFinished">Finished</ui5-label>
                        </td>
                        <td>
                            <ui5-input
                                id="taskFinished"
                                value={task?.finished ? new Date(task.finished).toLocaleString() : ""}
                                {...register("finished", {disabled: true})}
                                readonly
                            />
                        </td>
                    </tr>
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
                    <ui5-button design="Emphasized" type="Submit">Save</ui5-button>
                    <ui5-button design="Transparent" onClick={onCancel}>Cancel</ui5-button>
                </div>
            </form>
        </ui5-dialog>
    );
}

export default EditTaskDialog;
