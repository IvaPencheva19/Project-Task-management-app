import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";

import { useContext } from "react";
import { AuthContext } from "./context/AuthContext";


function App() {
  const { user } = useContext(AuthContext);

  return (
    <Router>
      <Routes>
        <Route path="/login" element={!user ? <LoginPage /> : <Navigate to="/" />} />
        {/* <Route path="/" element={user ? <DashboardPage /> : <Navigate to="/login" />} />
        <Route path="/projects/:id" element={user ? <ProjectPage /> : <Navigate to="/login" />} /> */}
      </Routes>
    </Router>
  );
}

export default App;
