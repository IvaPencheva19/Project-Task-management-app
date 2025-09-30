
import type { LoginRequest, SignupRequest } from "../../types/authRequestsTypes";
import api from "../axiosInstance";

export const loginUser = async (data: LoginRequest) => {
    const response = await api.post("/auth/login", data);
    return response.data; // { token: string }
};

export const signupUser = async (data: SignupRequest) => {
    const response = await api.post("/auth/signup", data);
    return response.data; // maybe { message: string } or { token: string }
};