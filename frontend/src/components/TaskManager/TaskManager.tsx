import "@ui5/webcomponents/dist/Card.js";
import "@ui5/webcomponents/dist/CardHeader.js";
import "@ui5/webcomponents/dist/Title.js";
import "@ui5/webcomponents/dist/Label.js";
import "@ui5/webcomponents/dist/Icon.js";
import "@ui5/webcomponents/dist/Button.js";
import "@ui5/webcomponents-icons/dist/AllIcons.js";
import "@ui5/webcomponents-fiori/dist/Timeline.js";
import "@ui5/webcomponents-fiori/dist/TimelineItem.js";
import "@ui5/webcomponents/dist/Toast.js";

import { useEffect, useState } from "react";
import { taskService } from "./apis/task.api";
import { Task } from "./models/task.model";

import CardHeaderWithEditAndRemoveActions
    from "../commons/CardHeaderWithEditAndRemoveActions/CardHeaderWithEditAndRemoveActions";
import CreateTaskDialog from "./CreateTaskDialog";
import EditTaskDialog from "./EditTaskDialog";

export default function TaskManager() {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [selectedTask, setSelectedTaskToEdit] = useState<Task | null>(null);
    const [isDialogOpen, setIsDialogOpenToEditTask] = useState(false);
    const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false);

    useEffect(() => {
        taskService.getAllTasks()
            .then(setTasks)
            .catch(err => setError(err.message));
    }, []);

    const handleRemoveTask = async (indexToRemove: number) => {
        const taskToDelete = tasks[ indexToRemove ];

        if (!taskToDelete?.id) {
            setError("Cannot delete task without valid ID.");
            return;
        }

        try {
            await taskService.deleteTask(taskToDelete.id);
            setTasks((prevTasks) => prevTasks.filter((_, index) => index !== indexToRemove));
        } catch (err) {
            setError("Failed to delete task. Please try again.");
        }
    };

    const handleEditTask = (indexToEdit: number) => {
        const taskToEdit = tasks[ indexToEdit ];
        if (taskToEdit) {
            setSelectedTaskToEdit(taskToEdit);
            setIsDialogOpenToEditTask(true);
        }
    };

    const handleUpdateTask = (updatedTask: Task) => {
        setTasks((prevTasks) =>
            prevTasks.map((task) =>
                task.id === updatedTask.id ? { ...task, ...updatedTask } : task
            )
        );
        setIsDialogOpenToEditTask(false);
    };

    const handleCancelEdit = () => {
        setIsDialogOpenToEditTask(false);
    };

    const handleOpenCreateDialog = () => {
        setIsCreateDialogOpen(true);
    };

    const handleCreateTask = (newTask: Task) => {
        setTasks(prev => [...prev, newTask]);
        setIsCreateDialogOpen(false);
    };

    const handleCancelCreate = () => {
        setIsCreateDialogOpen(false);
    };

    return (
        <>
            { error &&
                <div>{error}</div>
            }
            <div className="new-task-section">
                <ui5-button
                    icon="add"
                    design="Emphasized"
                    tooltip="Create New Task"
                    class="new-task-button"
                    onClick={handleOpenCreateDialog}
                >Add
                </ui5-button>
            </div>
            <div className="tasks-container">
                {tasks.map((task, index) => (
                    <ui5-card
                        key={index}
                        header-text={task.name}
                        style={{marginBottom: "1rem"}}
                    >
                        <CardHeaderWithEditAndRemoveActions
                            title={task.name}
                            onEditButtonClick={() => handleEditTask(index)}
                            onRemoveButtonClick={() => handleRemoveTask(index)}
                        />
                        <div className="card-content">
                            <h4>Description</h4>
                            <p>{task.description}</p>
                            <div className="timeline-section">
                                <ui5-timeline>
                                    <ui5-timeline-item
                                        title-text={`${new Date(task.created).toLocaleString()}`}
                                        icon="calendar"
                                    ></ui5-timeline-item>
                                    {task.finished && (
                                        <ui5-timeline-item
                                            title-text={`${new Date(task.finished).toLocaleString()}`}
                                            icon="accept"
                                        ></ui5-timeline-item>
                                    )}
                                </ui5-timeline>
                            </div>
                        </div>
                    </ui5-card>
                ))}
            </div>
            <EditTaskDialog
                open={isDialogOpen}
                task={selectedTask}
                onSave={handleUpdateTask}
                onCancel={handleCancelEdit}
            />
            <CreateTaskDialog
                open={isCreateDialogOpen}
                onCreate={handleCreateTask}
                onCancel={handleCancelCreate}
            />
        </>
    );
}
