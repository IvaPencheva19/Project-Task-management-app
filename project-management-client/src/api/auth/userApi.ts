
import type { UpdateUserRequest, } from "../../types/userRequestsTypes";
import api from "../axiosInstance"

export const getMyUser = async () => {
    const response = await api.get("/user/me");
    return response.data;
};

export const getAllUsers = async () => {
    const response = await api.get("/user");
    return response.data;
};

export const getUserById = async (userId: number) => {
    const response = await api.get(`/user/${userId}`);
    return response.data;
};

export const deleteUser = async (userId: number) => {
    const response = await api.delete(`/user/${userId}`);
    return response.data;
};

export const updateUser = async (userId: number, data: UpdateUserRequest) => {
    const response = await api.post(`/user/${userId}`, data);
    return response.data;
};


