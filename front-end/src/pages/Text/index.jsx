import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./style.css";

const baseUrl = "http://localhost:8080/api";

export const App = () => {
  const [texto, setTexto] = useState("");
  const [error, setError] = useState("");
  const [mensagemEnviada, setMensagemEnviada] = useState(false);
  const [userId, setUserId] = useState("");
  const [projectId, setProjectId] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    handleIdUpdate();
  }, []);

  const handleIdUpdate = () => {
    const storedUserId = localStorage.getItem("userId");
    const storedUserProject = localStorage.getItem("projectId");
    setUserId(storedUserId);
    setProjectId(storedUserProject);
  };

  const handleChange = (event) => {
    setTexto(event.target.value);
    setError("");
    setMensagemEnviada(false);
  };

  const handleClearText = () => {
    setTexto("");
    setMensagemEnviada(false);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    console.log(texto);
    console.log(userId);
    console.log(projectId);
    try {
      const response = await axios.post(
        `${baseUrl}/projects/${projectId}/texts`,
        {
          text: texto,
          type: "a",
        }
      );
      if (response.status === 200) {
        localStorage.setItem("textId", response.data.id);
        navigate("/label-management");
      }
    } catch (error) {
      console.error(error);
    }

    if (texto.trim() === "") {
      setError("O texto não pode estar vazio.");
      return;
    }
  };

  const handleBack = () => {
    navigate(-1); // Volta para a página anterior
  };

  return (
    <div>
      <div>
        <center>
          <h1>Text preview</h1>
        </center>
        <br />
        <br />
        <form>
          <center>
            <textarea
              value={texto}
              onChange={handleChange}
              style={{
                textAlign: "center",
                width: "50%",
                margin: "0 auto",
                resize: "none",
              }}
              rows={26}
              cols={180}
            />
          </center>
          <br />
          <center>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {mensagemEnviada && (
              <p style={{ color: "white" }}>Mensagem enviada com sucesso!</p>
            )}
          </center>
          <br />
          <center>
            <button type="submit" onClick={handleSubmit}>Enviar</button>
            <button type="button" onClick={handleBack} style={{ marginLeft: "10px" }}>Voltar</button>
          </center>
          <br />
          <center>
            <button type="button" className="clear-button" onClick={handleClearText}>
              <span role="img" aria-label="lixeira">🗑️</span> Excluir
            </button>
          </center>
        </form>
      </div>
    </div>
  );
};
