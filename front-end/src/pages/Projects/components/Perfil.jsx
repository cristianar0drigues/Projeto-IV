// import '../App.css';
import axios from "axios";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import estilo from './Perfil.module.css';

const url_project = "http://localhost:8080/api/users";

export const Perfil = () => {
    const [newName, setNewName] = useState("");
    const [newEmail, setNewEmail] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [ConfPassword, setConfPassword] = useState("");

    const handleEditUser = async (event) => {
        event.preventDefault();
        
        try {
            const response = await axios.put(`${url_project}/1`, {
                name: newName,
                login: newEmail,
                password: newPassword
            });
            if (response.status === 200) {
                window.location.href = "/project";
            }
        } catch (error) {
            console.error(error);
        }
    };

    const handleDeleteUser = async (event) => {
        event.preventDefault();
        var resposta = confirm("Are you sure you want to delete the account?");
        if (resposta == true) {
            try {
                const response = await axios.delete(`${url_project}/1`);
                if (response.status === 204) {
                    console.log("deletado");
                    window.location.href = "/login";
                }
            } catch (error) {
                console.error(error);
            }
        }
    };


    function fechar() {
        var element = document.getElementById("Perfil");
        element.classList.toggle(estilo.desabilitado);
    }
    return (
        <div id='Perfil' className={estilo.container + " " + estilo.desabilitado}>
            <div className={estilo.retangulo}></div>
            <div className={estilo.fechar} >
                <svg onClick={fechar} width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M6.4 19L5 17.6L10.6 12L5 6.4L6.4 5L12 10.6L17.6 5L19 6.4L13.4 12L19 17.6L17.6 19L12 13.4L6.4 19Z" fill="black" />
                </svg>
            </div>
            <form onSubmit={handleEditUser}>
                <h2>Profile</h2>
                <input placeholder='Name' className={estilo.campo_texto} type='text' name='name' value={newName} onChange={(e) => setNewName(e.target.value)} />
                <input placeholder='Email' className={estilo.campo_texto} type='email' name='email' value={newEmail} onChange={(e) => setNewEmail(e.target.value)} />
                <input placeholder='New password' className={estilo.campo_texto} type='password' name='new_password' value={newPassword} onChange={(e) => setNewPassword(e.target.value)}/>
                <input placeholder='Confirm password' className={estilo.campo_texto} type='password' name='conf_password' value={ConfPassword} onChange={(e) => setConfPassword(e.target.value)}/>
                <input className={estilo.sub_button} type="submit" value="SAVE" />
                <div>
                    <Link className="link" to="/login">Logout</Link>
                    <a className="link" onClick={handleDeleteUser}>Delete account</a>
                </div>
            </form>
        </div>
    )
}
