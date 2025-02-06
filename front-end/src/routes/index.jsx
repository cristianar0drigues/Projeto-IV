import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Association } from "../pages/Association";
import { Import } from "../pages/Import";
import { LabelManagement } from "../pages/LabelManagement";
import { Project } from "../pages/Projects";
import { Signin } from "../pages/Signin";
import { Signup } from "../pages/Signup";
import { App } from "../pages/Text";

const RoutesApp = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route exact path="/import" element={<Import />} />
        <Route path="/label-management" element={<LabelManagement />} />
        <Route path="/association" element={<Association />} />
        <Route path="/" element={<Signin />} />
        <Route exact path="/signup" element={<Signup />} />
        <Route path="/text" element={<App />} />
        <Route path="/signin" element={<Signin />} />
        <Route exact path="/project" element={<Project />} />
        <Route path="*" element={<Signin />} />
      </Routes>
    </BrowserRouter>
  );
};

export default RoutesApp;
