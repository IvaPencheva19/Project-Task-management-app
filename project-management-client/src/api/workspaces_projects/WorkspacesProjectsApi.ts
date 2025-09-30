import api from "../axiosInstance";
import type { ProjectRequest } from "../../types/workspacesProjectsRequestsTypes";

export const createProject = async (workspaceId: number, data: ProjectRequest) => {
    const response = await api.post(`/workspaces/workspaces/${workspaceId}/projects`, data);
    return response.data;
};


export const getProjects = async (workspaceId: number) => {
    const response = await api.get(`/workspaces/workspaces/${workspaceId}/projects`);
    return response.data;
};


export const updateProjectName = async (workspaceId: number, projectId: number, data: ProjectRequest) => {
    const response = await api.put(`/workspaces/workspaces/${workspaceId}/projects/${projectId}`, data);
    return response.data;
};





