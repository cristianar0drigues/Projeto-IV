// import '../App.css';
import axios from "axios";
import React, { useState } from "react";
import estilo from './Tela.module.css';

const url_project = "http://localhost:8080/api";

export const EditProjeto = ({projectId}) => {
    function fechar() {
        var element = document.getElementById("editProj");
        element.classList.toggle(estilo.desabilitado);
    }
    const [newTitle, setNewTitle] = useState("");
    const [newDescription, setNewDescription] = useState("");

    const handleEditProject = async () => {
        try {
            const response = await axios.put(`${url_project}/projects/${projectId}`, {
                name: newTitle,
                description: newDescription
            });
            if (response.status === 200) {
                setNewTitle("");
                setNewDescription("");
                fechar();
            }
        } catch (error) {
            console.error(error);
        }
    };


    return (
        <div id="editProj" className={estilo.tela + " " + estilo.desabilitado}>
            <div className={estilo.retangulo}></div>
            <div className={estilo.fechar} >
                <svg onClick={fechar} width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M6.4 19L5 17.6L10.6 12L5 6.4L6.4 5L12 10.6L17.6 5L19 6.4L13.4 12L19 17.6L17.6 19L12 13.4L6.4 19Z" fill="black" />
                </svg>
            </div>
            <form>
                <div className={estilo.centralizador}>
                    <h2 className={estilo.titulo_2}>Edit project</h2>
                    <input className={estilo.text_proj} type="text" placeholder="Title" value={newTitle} onChange={(e) => setNewTitle(e.target.value)} />
                    <input className={estilo.text_proj} type="text" placeholder="Description" value={newDescription} onChange={(e) => setNewDescription(e.target.value)} />
                    <input className={estilo.sub_button} type="button" value="UPDATE" onClick={handleEditProject} />
                </div>
            </form>
        </div>
    )
}
