import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { LayoutComponents } from "../components/LayoutComponents";

import "./style.css";

const baseUrl = "http://localhost:8080/api";

export const LabelManagement = () => {
  const [value, setValue] = useState("");
  const [color, setColor] = useState("");
  const [annotations, setAnnotations] = useState([]);
  const navigate = useNavigate();

  const addLabel = (e) => {
    e.preventDefault(); // Previne o recarregamento da página

    if (value !== "" && color !== "") {
      const newItem = {
        value: value,
        color: color,
      };

      setAnnotations((prev) => [...prev, newItem]);
      setValue("");
      setColor("");
    }

    console.log("Anotações atuais:", annotations); // Verifique as anotações no console
  };

  const handleSubmit = async () => {
    console.log("Botão FINISH clicado");

    try {
      const textId = localStorage.getItem("textId");
      console.log("textId:", textId); // Verifique se o textId está correto

      if (!textId) {
        console.error("Text ID não encontrado");
        return;
      }

      const response = await axios.post(
        `${baseUrl}/texts/${textId}/annotations`,
        annotations
      );

      if (response.status === 200) {
        console.log("Anotações enviadas");
        console.log(response.data);
        navigate("/association");
      }
    } catch (error) {
      console.error("Erro ao enviar as anotações:", error);
    }
  };

  const deleteItemList = (index) => {
    const confirmed = window.confirm("Tem certeza que quer deletar?");
    if (confirmed) {
      setAnnotations((prevList) => prevList.filter((_, i) => i !== index));
    }
  };

  return (
    <LayoutComponents>
      <div className="all-label-management">
        <button className="return-button">
          <svg
            className="seta-label"
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
          >
            <path
              fill="currentColor"
              d="m12 20l-8-8l8-8l1.425 1.4l-5.6 5.6H20v2H7.825l5.6 5.6L12 20Z"
            />
          </svg>
        </button>
        <div className="label-management">
          <div className="form-label">
            <span className="label-title">Label management</span>
            <form onSubmit={addLabel}>
              <input
                className="label-input"
                type="text"
                placeholder="Label"
                value={value}
                onChange={(e) => setValue(e.target.value)}
              />
              <br />
              <div className="color-key">
                <input
                  className="color-input"
                  type="color"
                  value={color}
                  onChange={(e) => setColor(e.target.value)}
                />
              </div>
              <button className="button-add" type="submit">
                ADD
              </button>
            </form>
          </div>
          <div className="div-list-label">
            <ul className="list-label">
              {annotations.map((item, index) => (
                <li
                  className="item-list"
                  key={index}
                  style={{ backgroundColor: item.color }}
                >
                  <span className="span-descricao">{item.value}</span>
                  <button
                    className="delete-label"
                    onClick={() => deleteItemList(index)}
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="24"
                      height="24"
                      viewBox="0 0 24 24"
                    >
                      <path
                        fill="currentColor"
                        d="m12 13.4l-4.9 4.9q-.275.275-.7.275t-.7-.275q-.275-.275-.275-.7t.275-.7l4.9-4.9l-4.9-4.9q-.275-.275-.275-.7t.275-.7q.275-.275.7-.275t.7.275l4.9 4.9l4.9-4.9q.275-.275.7-.275t.7.275q.275.275.275.7t-.275.7L13.4 12l4.9 4.9q.275.275.275.7t-.275.7q-.275.275-.7.275t-.7-.275L12 13.4Z"
                      />
                    </svg>
                  </button>
                </li>
              ))}
            </ul>
            <button
              className="button-finish"
              onClick={handleSubmit}
              type="button"
            >
              FINISH
            </button>
          </div>
        </div>
      </div>
    </LayoutComponents>
  );
};
