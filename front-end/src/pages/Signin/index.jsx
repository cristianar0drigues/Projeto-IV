import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { LayoutComponents } from '../components/LayoutComponents';

import './style.css';

const url_users = "http://localhost:8080/api/users";

export const Signin = () =>{
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState([]);
   
    const navigate = useNavigate();


    const handleLogin = async () => {
        const validationsErrors = [];
        try {
            const response = await axios.post(`${url_users}/login`, {
                login: login,
                password: password
            });

            if(response.data){
                localStorage.setItem("userId", response.data);
                navigate("/project");
            }
        } catch (error) {
            validationsErrors.push("Credenciais Inválidas!");
        } setErrors(validationsErrors);
    };

    return(
       <LayoutComponents>
        <form className='form'>
            <span className="form-title">Login</span>
            <div className="wrap-input">
                <input className="input" type="login"
                    placeholder="Email"
                    value={login}
                    onChange={e => setLogin(e.target.value)}
                />
            </div>
            <div className="wrap-input">
                <input className="input" type="password"
                    placeholder="Password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
            </div>
            <div className="errors-list">
                {errors.length > 0 && (
                <ul>
                    {errors.map((error, index) =>(
                        <li key={index}> {error} </li>
                    ))}
                </ul>
                )}
            </div>
                
                <span><Link className="link-password" to="#"> Forgot your password? </Link></span>
                <br />
                <button type="button"
                    onClick={handleLogin}
                    className="button-form-login">
                    LOGIN
                </button>
                <br />
                <span className="span">Don't have an account? <Link className="link" to="/signup" >Sign up</Link></span>
         </form>
        </LayoutComponents>
    );
}
