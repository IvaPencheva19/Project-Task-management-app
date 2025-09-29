import { useEffect, useState } from "react";
import {jwtDecode} from "jwt-decode";
import { type User, type Props, AuthContext } from "../types/authContextTypes";


export const AuthProvider: React.FC<Props> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decoded: any = jwtDecode(token);
      setUser({ id: decoded.id, email: decoded.sub }); // adjust if your JWT fields differ
    }
  }, []);

  const login = (token: string) => {
    localStorage.setItem("token", token);
    const decoded: any = jwtDecode(token);
    setUser({ id: decoded.id, email: decoded.sub });
  };

  const logout = () => {
    localStorage.removeItem("token");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
export { AuthContext };

