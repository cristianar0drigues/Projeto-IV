import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Delete } from "./Delete";
import funcao from "./Delete.module.css";
import { EditProjeto } from "./EditProjeto";
import estilo from "./Projetos.module.css";
import funcao3 from "./Tela.module.css";
import { TelaCriacao } from "./TelaCriacao";

const url_project = "http://localhost:8080/api";

export const Projetos = () => {
  const [projectList, setProjectList] = useState([]);
  const [selectedProjectId, setSelectedProjectId] = useState(null);
  const [userId, setUserId] = useState("");
  const navigate = useNavigate();

  const updateProjectList = (updatedProject) => {
    const updatedList = projectList.map((project) =>
      project.id === updatedProject.id ? updatedProject : project
    );
    setProjectList(updatedList);
  };

  useEffect(() => {
    const handleUserIdUpdate = () => {
      const storedUserId = localStorage.getItem("userId");
      setUserId(storedUserId);
      fetchProjects(storedUserId);
    };

    handleUserIdUpdate();
  }, [userId, selectedProjectId, projectList]);

  const fetchProjects = async (id) => {
    try {
      const response = await axios.get(`${url_project}/users/${id}/projects`);
      if (response.status === 200) {
        const projects = response.data;
        setProjectList(projects);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleOpenDelete = (projectId) => {
    setSelectedProjectId(projectId);
    abrir_delete();
  };

  const handleOpenText = (projectId) => {
    localStorage.setItem("projectId", projectId);
    navigate("/text");
  };

  const handleOpenEdit = (projectId) => {
    setSelectedProjectId(projectId);
    abrir_edit();
  };

  function abrir_delete() {
    var element = document.getElementById("Delete");
    element.classList.toggle(funcao.desabilitado);
  }

  function abrir_edit() {
    var element = document.getElementById("editProj");
    element.classList.toggle(funcao3.desabilitado);
  }

  return (
    <main>
      {projectList.map((project) => (
        <div key={project.id} className={estilo.info_projetos}>
          <div className={estilo.proj_title}>{project.name}</div>
          <div className={estilo.proj_desc}>{project.description}</div>
          <div className={estilo.proj_op}>
            <span onClick={() => handleOpenText(project.id)}>Open</span> |
            <span onClick={() => handleOpenEdit(project.id)}>Edit</span> |
            <span onClick={() => handleOpenDelete(project.id)}>Delete</span>
          </div>
        </div>
      ))}
      <EditProjeto projectId={selectedProjectId} />
      <Delete projectId={selectedProjectId} />
      <TelaCriacao userId={userId} updateProjectList={updateProjectList} />
    </main>
  );
};
