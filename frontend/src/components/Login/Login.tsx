import React, { useState } from "react";
import "@ui5/webcomponents/dist/Input.js";
import "@ui5/webcomponents/dist/Button.js";
import "@ui5/webcomponents/dist/Label.js";
import "./Login.css";

const Login: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
      e.preventDefault();
    console.log("Username:", username, "Password:", password);
  };

  const handleUsernameInput = (e: Event) => {
    setUsername((e.target as any).value);
  };

  const handlePasswordInput = (e: Event) => {
    setPassword((e.target as any).value);
  };
  return (
    <div className="login-container">
      <form className="login-form">
        <h1>Login</h1>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <ui5-input
            id="username"
            placeholder="Enter username"
            onInput={handleUsernameInput}
          ></ui5-input>
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <ui5-input
            id="password"
            type="Password"
            placeholder="Enter password"
            onInput={handlePasswordInput}
          ></ui5-input>
        </div>
        <ui5-button design="Emphasized" onClick={handleSubmit}>
          Login
        </ui5-button>
      </form>
    </div>
  );
};

export default Login;
