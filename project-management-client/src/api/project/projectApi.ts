import api from "../axiosInstance";


export const getAllProjectsForUser = async () => {
    const response = await api.get("/projects/my");
    return response.data;
};

export const deleteProject = async (projectId: number) => {
    const response = await api.delete(`/projects/${projectId}`);
    return response.data;
};

