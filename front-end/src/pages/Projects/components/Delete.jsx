import axios from "axios";
import React from "react";
import estilo from "./Delete.module.css";

const url_project = "http://localhost:8080/api";

export const Delete = ({ projectId }) => {
  function fechar() {
    var element = document.getElementById("Delete");
    element.classList.toggle(estilo.desabilitado);
  }

  const handleDeleteProject = async () => {
    try {
      const response = await axios.delete(
        `${url_project}/projects/${projectId}`
      );
      if (response.status === 204) {
        fechar();
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div id="Delete" className={estilo.container + " " + estilo.desabilitado}>
      <div className={estilo.retangulo}></div>
      <form>
        <h2>Are you sure you want to delete the project?</h2>
        <div>
          <input
            className={estilo.conf_button}
            type="button"
            value="Yes"
            onClick={handleDeleteProject}
          />
          <input
            onClick={fechar}
            className={estilo.conf_button}
            type="button"
            value="No"
          />
        </div>
      </form>
    </div>
  );
};
