import api from "../axiosInstance";
import type { WorkspaceRequest, AddMemberRequest } from "../../types/workspaceRequestsTypes";

export const createWorkspace = async (data: WorkspaceRequest) => {
    const response = await api.post("/workspaces", data);
    return response.data;
};


export const updateWorkspace = async (workspaceId: number, data: WorkspaceRequest) => {
    const response = await api.put(`/workspaces/${workspaceId}`, data);
    return response.data;
};
export const getUserWorkspaces = async () => {
    const response = await api.get("/workspaces");
    return response.data;
};
export const getWorkspaceOwner = async (workspaceId: number) => {
    const response = await api.get(`/workspaces/${workspaceId}/owner`);
    return response.data;
};
export const addMember = async (workspaceId: number, data: AddMemberRequest) => {
    const response = await api.post(`/workspaces/${workspaceId}/members`, data);
    return response.data;
};
export const getWorkspaceMembers = async (workspaceId: number) => {
    const response = await api.get(`/workspaces/${workspaceId}/members`);
    return response.data;
};