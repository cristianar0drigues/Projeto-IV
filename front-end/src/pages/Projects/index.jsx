import React from "react";
import "../../assets/global.css";
import { AddProjeto } from "./components/AddProjeto";
import { Perfil } from "./components/Perfil";
import { Projetos } from "./components/Projetos";
import { Topo } from "./components/Topo";
import "./style.css";

export const Project = () => {
  return (
    <div className="container_projeto">
      <Topo />
      <AddProjeto />
      <Perfil />
      <Projetos />
    </div>
  );
};
