import { useState } from "react";
import "@ui5/webcomponents/dist/Card.js";
import "@ui5/webcomponents/dist/CardHeader.js";
import "@ui5/webcomponents/dist/Title.js";
import "@ui5/webcomponents/dist/Label.js";
import "@ui5/webcomponents/dist/Icon.js";
import "@ui5/webcomponents-icons/dist/AllIcons.js";
import "@ui5/webcomponents/dist/Button.js";
import "@ui5/webcomponents-fiori/dist/Timeline.js";
import "@ui5/webcomponents-fiori/dist/TimelineItem.js";

const tasksData = {
  tasks: [
    {
      name: "Design Database Schema",
      description: "Design and model the database schema for the new project.",
      created: "2025-03-18T09:55:18.819Z",
      finished: "",
    },
    {
      name: "Develop REST API",
      description: "Implement the REST API endpoints for user management.",
      created: "2025-03-19T10:10:00.000Z",
      finished: "2025-03-20T14:30:00.000Z",
    },
    {
      name: "Create Wireframes",
      description: "Develop initial wireframes for the landing page.",
      created: "2025-03-21T11:15:00.000Z",
      finished: "",
    },
    {
      name: "Implement UI",
      description: "Build the user interface using React components.",
      created: "2025-03-22T12:20:00.000Z",
      finished: "2025-03-23T16:45:00.000Z",
    },
    {
      name: "Set up CI/CD",
      description: "Configure the continuous integration and deployment pipeline.",
      created: "2025-03-24T08:30:00.000Z",
      finished: "",
    },
    {
      name: "Write Unit Tests",
      description: "Create comprehensive unit tests for all critical modules.",
      created: "2025-03-25T09:40:00.000Z",
      finished: "2025-03-26T13:00:00.000Z",
    },
    {
      name: "Conduct Code Review",
      description: "Perform a thorough code review and ensure quality standards.",
      created: "2025-03-27T10:50:00.000Z",
      finished: "",
    },
    {
      name: "Deploy Application",
      description: "Deploy the application to the production environment.",
      created: "2025-03-28T11:55:00.000Z",
      finished: "2025-03-29T15:15:00.000Z",
    },
  ],
};

export default function TaskManager() {
  const [tasks, setTasks] = useState(tasksData.tasks);

  const handleRemoveTask = (indexToRemove) => {
    setTasks((prevTasks) => prevTasks.filter((_, index) => index !== indexToRemove));
  };

  return (
    <>
      <div className="new-task-section">
        <ui5-button 
          icon="add" 
          design="Emphasized" 
          tooltip="Create New Task"
          class="new-task-button"
        >Add</ui5-button>
      </div>
      <div className="tasks-container">
        {tasks.map((task, index) => (
          <ui5-card
            key={index}
            header-text={task.name}
            style={{ marginBottom: "1rem" }}
          >
            <ui5-card-header slot="header" title-text={task.name}>
              <ui5-icon name="clinical-task-tracker" slot="avatar"></ui5-icon>
              <ui5-button 
                icon="decline"
                design="Transparent"
                slot="action"
                tooltip="Remove Task"
                onClick={() => handleRemoveTask(index)}
              ></ui5-button>
            </ui5-card-header>
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
    </>
  );
}
