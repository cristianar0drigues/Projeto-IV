import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./style.css";

const urlAssociation = "http://localhost:8080/api";

export const Association = () => {
  const [textId, setTextId] = useState("");
  const [subTexts, setSubTexts] = useState([]);
  const [idsAnnotation, setIdsAnnotation] = useState([]);
  const [text, setText] = useState();
  const [annotationList, setAnnotationList] = useState([]);
  const [subText, setSubText] = useState("");
  const [annId, setAnnId] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate(); // Hook para navegação

  useEffect(() => {
    const getTextId = () => {
      let id = localStorage.getItem("textId");
      setTextId(id);
      fetchText(id);
      fetchAnnotations(id);
      fetchSubtexts(id);
      fetchIdAnnotation(34);
    };

    getTextId();
  }, []);

  const fetchText = async (id) => {
    try {
      const response = await axios.get(`${urlAssociation}/texts/${id}`);
      if (response.status === 200) {
        setText(response.data.text);
      }
    } catch (error) {
      console.error(error);
      setErrorMessage("Erro ao carregar o texto.");
    }
  };

  const fetchAnnotations = async (id) => {
    try {
      const response = await axios.get(`${urlAssociation}/texts/${id}/annotations`);
      if (response.status === 200) {
        setAnnotationList(response.data);
      }
    } catch (error) {
      console.error(error);
      setErrorMessage("Erro ao carregar as anotações.");
    }
  };

  const fetchSubtexts = async (id) => {
    try {
      const res = await axios.get(`${urlAssociation}/texts/${id}/subTexts`);
      if (res.status === 200) {
        setSubTexts(res.data);
        let ids = res.data.map(dado => dado.id);
        let annotationIds = await Promise.all(
          ids.map(async (eachId) =>
            (await axios.get(`${urlAssociation}/subTexts/${eachId}/annotations`)).data
          )
        );
        setIdsAnnotation(annotationIds.map((ann) => ann.map((id) => id.id)));
      }
    } catch (error) {
      console.error(error);
      setErrorMessage("Erro ao carregar os subtextos.");
    }
  };

  const fetchIdAnnotation = async (subId) => {
    try {
      const res = await axios.get(`${urlAssociation}/subTexts/${subId}/annotations`);
      if (res.status === 200) {
        console.log(res.data);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleAddSubText = () => {
    if (subText && text.includes(subText)) {
      const newSubText = { id: Date.now(), value: subText };
      setSubTexts([...subTexts, newSubText]);
      setSubText("");
      setErrorMessage("");
    } else {
      setErrorMessage("O subtexto não está contido no texto.");
    }
  };

  const handleRemoveSubText = (subTextId) => {
    setSubTexts((prevSubTexts) => {
      // Remove o subtexto
      const updatedSubTexts = prevSubTexts.filter(sub => sub.id !== subTextId);
      
      // Remove as anotações associadas ao subtexto removido
      const updatedIdsAnnotation = idsAnnotation.filter((idGroup) => {
        // Filtra os ids de anotações que estão associadas ao subtexto apagado
        return idGroup[0] !== subTextId;
      });

      // Atualiza o estado de idsAnnotation com as novas associações
      setIdsAnnotation(updatedIdsAnnotation);
      return updatedSubTexts;
    });
  };

  const handleCreateSubText = async () => {
    if (subText && text.includes(subText)) {
      const annIdArray = annId.split(',').map(id => id.trim());
      const allIdsPresent = annIdArray.every(id => annotationList.some(annotation => annotation.id == id));

      if (allIdsPresent) {
        try {
          const res = await axios.post(`${urlAssociation}/texts/${textId}/association`, [
            { value: subText, annotations: annIdArray }
          ]);
          if (res.status === 200) {
            console.log("Relação feita com sucesso");
            setErrorMessage("");
            // Atualizar o estado após o sucesso
            setSubText("");
            setAnnId("");
          }
        } catch (error) {
          console.error("Erro ao tentar salvar a relação:", error);
          setErrorMessage("Erro ao tentar salvar a relação. Tente novamente.");
        }
      } else {
        setErrorMessage("Alguns valores de annId não estão presentes em annotationList.");
      }
    } else {
      setErrorMessage("O subtexto não está contido no texto.");
    }
  };

  return (
    <div className="container_association">
      <div className="container_org">
        <div className="up">
          <div className="text">
            <h2>Text preview</h2>
            <textarea value={text} disabled></textarea>
          </div>
          <div className="annotations">
            <h2>Annotations</h2>
            <div className="table_annotation">
              {annotationList.map((annotation) => (
                <span key={annotation.id} style={{ background: annotation.color }} className="each_note">
                  {annotation.id} - {annotation.value}
                </span>
              ))}
            </div>
          </div>
        </div>

        <div className="middle">
          <h2>Subtext</h2>
          <h2>Id Annotations</h2>
        </div>

        <div className="down">
          <div className="subtext">
            <div className="table_subtext">
              <div>
                <span onClick={handleAddSubText} className="icon plus">+</span>
                <input onChange={(e) => setSubText(e.target.value)} value={subText} className="each_subtext" type="text" />
                
                {subTexts.map((sub) => (
                  <div key={sub.id}>
                    <input value={sub.value} disabled className="each_subtext" type="text" />
                    <span onClick={() => handleRemoveSubText(sub.id)} className="icon minus">-</span>
                  </div>
                ))}
              </div>

              <div>
                <input onChange={(e) => setAnnId(e.target.value)} className="each_id" type="text" />
                <span className="icon minus">-</span>
                {idsAnnotation.map((ann, index) => (
                  <input key={index} value={ann} disabled className="each_id" type="text" />
                ))}
              </div>
            </div>
          </div>
        </div>

        {errorMessage && <div className="error-message">{errorMessage}</div>}

        <button className="button_save" onClick={handleCreateSubText}>Save</button>

        {/* Botão atualizado para navegar para a tela de Project */}
        <button className="button_next" onClick={() => navigate("/project")}>
          Ir para Project
        </button>
      </div>
    </div>
  );
};
