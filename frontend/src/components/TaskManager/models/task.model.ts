import { JSONSchemaType } from "ajv";

export interface Task {
    id?: number;
    name: string;
    description?: string;
    created: string;
    finished?: string;
}

export const taskSchema: JSONSchemaType<Task> = {
    type: "object",
    properties: {
        id: {
            type: "number",
            nullable: true,
        },
        name: {
            type: "string",
            minLength: 3,
            errorMessage: { minLength: "Minimum length of name 3", require: "Name field is required" },
        },
        description: {
            type: "string",
            nullable: true,
        },
        created: {
            type: "string",
        },
        finished: {
            type: "string",
            nullable: true,
        },
    },
    required: ["name"],
    additionalProperties: false,
}
