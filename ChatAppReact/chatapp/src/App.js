import { Routes, Route } from "react-router-dom";
import Chat from "./pages/Chat.js";
import Login from "./pages/Login";
import Register from "./pages/Register";
import GroupList from "./pages/GroupList.js";
import './css/chat.css';
import './css/modal.css';
import './css/singleGroup.css';
import { useState } from "react";


function App() {
  const [globalState, setGlobalState] = useState({});

  return (
    <Routes>
      <Route index element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/chat" element={<Chat globalState={globalState} />} />
      <Route path="/groups" element={<GroupList  globalState={globalState} setGlobalState={setGlobalState} />} />
    </Routes>
  );
}

export default App;