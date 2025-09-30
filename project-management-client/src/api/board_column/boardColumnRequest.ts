import api from "../axiosInstance";

import type { BoardColumnRequest } from "../../types/boardColumnTypes";


export const createColumn = async (projectId: number, data: BoardColumnRequest) => {
    const response = await api.post(`/projects/${projectId}/columns`, data);
    return response.data;
};


export const getColumns = async (projectId: number) => {
    const response = await api.get(`/projects/${projectId}/columns`);
    return response.data;
};

export const updateColumn = async (columnId: number, data: BoardColumnRequest) => {
    const response = await api.put(`/columns/${columnId}`, data);
    return response.data;
};