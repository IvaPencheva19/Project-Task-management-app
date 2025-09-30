import api from "../axiosInstance";
import type { TaskRequest } from "../../types/taskRequestTypes";


export const createTask = async (columnId: number, data: TaskRequest) => {
    const response = await api.post(`/columns/${columnId}/tasks`, data);
    return response.data;
};

export const getTasksByProject = async (projectId: number) => {
    const response = await api.get(`projects/${projectId}/tasks`);
    return response.data;
};

export const updateTask = async (taskId: number, data: TaskRequest) => {
    const response = await api.put(`tasks/${taskId}`, data);
    return response.data;
};

export const deleteTask = async (taskId: number) => {
    const response = await api.delete(`tasks/${taskId}`);
    return response.data;
};