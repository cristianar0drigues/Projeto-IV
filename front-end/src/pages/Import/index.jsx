import React, { useState } from "react";
import axios from "axios";
import "./import.css";

const api = axios.create({
    baseURL: "http://localhost:3000"
});

export const Import = () => {
    const [text, setText] = useState("");

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onload = (event) => {
            setText(event.target.result);
        };
        reader.readAsText(file);
    };

    const handleClick = () => {
        api.post("/text", { text })
            .then(response => {
                window.location.reload();
            })
            .catch(error => {
                console.error(error);
            });
    };

    return (
        <div className="import">
            <div>
                <h1 className="import__title">Import tool</h1>
                <form className="import__form">
                    <textarea className="import__form__textarea" onChange={handleFileChange} placeholder="Paste your text here"/>
                    <label className="import__form__file" htmlFor="select-file">Or import a .txt file</label>
                    <input type="file" id="select-file" accept=".txt" style={{display: "none"}}></input>
                    <button className="import__form__submit" id="Submit" onClick={handleClick}>SUBMIT</button>
                </form>
            </div>
        </div>
    );
};
