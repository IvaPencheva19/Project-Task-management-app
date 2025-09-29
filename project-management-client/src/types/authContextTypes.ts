import { createContext, type ReactNode } from "react";

export interface User {
    id: number;
    email: string;
}

export interface AuthContextType {
    user: User | null;
    login: (token: string) => void;
    logout: () => void;
}

export const AuthContext = createContext<AuthContextType>({
    user: null,
    login: () => { },
    logout: () => { },
});

export interface Props {
    children: ReactNode;
}