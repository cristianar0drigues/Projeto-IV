import axios from "axios";
import { useState } from "react";
import funcao from "./AddProjeto.module.css";
import estilo from "./Tela.module.css";

const url_project = "http://localhost:8080/api";

export const TelaCriacao = ({ userId }) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  const handleCreateProject = async () => {
    console.log(userId);
    try {
      const response = await axios.post(
        `${url_project}/users/${userId}/projects`,
        {
          name: name,
          description: description,
        }
      );
      if (response.status === 200) {
        fechar();
      }
    } catch (error) {
      console.error(error);
    }
  };

  function fechar() {
    var element = document.getElementById("telaCriada");
    var button = document.getElementById("criar");
    element.classList.toggle(estilo.desabilitado);
    button.classList.toggle(funcao.desabilitado);
  }
  return (
    <div id="telaCriada" className={estilo.tela + " " + estilo.desabilitado}>
      <div className={estilo.retangulo}></div>
      <div className={estilo.fechar}>
        <svg
          onClick={fechar}
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M6.4 19L5 17.6L10.6 12L5 6.4L6.4 5L12 10.6L17.6 5L19 6.4L13.4 12L19 17.6L17.6 19L12 13.4L6.4 19Z"
            fill="black"
          />
        </svg>
      </div>
      <form>
        <span className={estilo.titulo_2}>New project</span>
        <input
          onChange={(e) => setName(e.target.value)}
          className={estilo.text_proj}
          type="text"
          placeholder="Title"
          value={name}
        />
        <input
          onChange={(e) => setDescription(e.target.value)}
          className={estilo.text_proj}
          type="text"
          placeholder="Description"
        />
        <button
          onClick={handleCreateProject}
          className={estilo.sub_button}
          type="button"
        >
          CREATE
        </button>
      </form>
    </div>
  );
};
