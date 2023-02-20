import React, { useState } from 'react'
import { useNavigate } from "react-router-dom";

const Register = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");

    const onUsernameChanged = (e) => setUsername(e.target.value);
    const onPasswordChanged = (e) => setPassword(e.target.value);
    const onPhoneNumberChanged = (e) => setPhoneNumber(e.target.value);

    const navigate = useNavigate();

    async function registerUser() {
        let json = { "username": `${username}`, "password": `${password}`, "phoneNumber": `${phoneNumber}` };

        await fetch('http://localhost:8080/user/register', {
            method: 'POST', credentials: 'same-origin',
            body: JSON.stringify(json),
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
            },
        }).then(res => res.json()).then(data => {
            if (data.user != "exists") {
                navigate('/', { replace: true });
            }
        });
    }

    return (
        <div className='mainContainer account-container'>

            <div className="mainHeader"></div>

            <div className="account-content">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
                <h1><b>Register</b></h1>
                <input type="text" placeholder='Username' value={username} onChange={onUsernameChanged} />
                <input type="text" placeholder='Phone number' value={phoneNumber} onChange={onPhoneNumberChanged} />
                <input type="password" placeholder='Password' value={password} onChange={onPasswordChanged} />
                <button onClick={registerUser}>Register</button>
            </div>

            <div className="mainFooter"></div>
        </div>
    )
}

export default Register;