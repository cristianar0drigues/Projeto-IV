import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { LayoutComponents } from "../components/LayoutComponents";

const url_users = "http://localhost:8080/api/users";

export const Signup = () => {
  const [name, setName] = useState("");
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState([]);

  const navigate = useNavigate();

  const validations = async () => {
    const validationErrors = [];

    try {
      const response = await axios.post(`${url_users}/login`, {
        login: login,
      });

      if (response.data) {
        validationErrors.push("Este endereço de e-mail já está em uso");
      }
    } catch (error) {
      if (error.response?.status === 500) {
        validationErrors.push("Este endereço de e-mail já está em uso");
      }
    }

    if (password !== confirmPassword) {
      validationErrors.push("Senha e confirmação de senha diferentes");
    }
    if (password.length < 6) {
      validationErrors.push("A senha deve ter pelo menos 6 caracteres");
    }
    setErrors(validationErrors);

    if (validationErrors.length === 0) {
      handleSignup();
    }
  };

  const handleSignup = async () => {
    try {
      const newUser = {
        name: name,
        login: login,
        password: password,
      };
      const response = await axios.post(url_users, newUser);

      if (response.status === 200) {
        navigate("/");
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <LayoutComponents>
      <form className="form">
        <span className="form-title">Register</span>

        <div className="wrap-input">
          <input
            className="input"
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>

        <div className="wrap-input">
          <input
            className="input"
            type="email"
            placeholder="Email"
            value={login}
            onChange={(e) => setLogin(e.target.value)}
          />
        </div>

        <div className="wrap-input">
          <input
            className="input"
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <div className="wrap-input">
          <input
            className="input"
            type="password"
            placeholder="Confirm password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
        </div>

        <div className="errors-list">
          {errors.length > 0 && (
            <ul>
              {errors.map((error, index) => (
                <li key={index}>{error}</li>
              ))}
            </ul>
          )}
        </div>

        <button className="button-form" type="button" onClick={validations}>
          REGISTER
        </button>

        <span className="span">
          Already have an account?{" "}
          <Link className="link" to="/login">
            Sign in
          </Link>
        </span>
      </form>
    </LayoutComponents>
  );
};
