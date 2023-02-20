import React, { useEffect, useState, useCallback } from 'react';
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

const Chat = ({ globalState }) => {

  const navigate = useNavigate();

  const [openModal, setOpenModal] = useState(false);

  const [userClicked, setUserClicked] = useState(false);

  const signOut = () => {
    setCookie("phoneNumber", null);
    setCookie("token", null);
    setCookie("username", null);
    navigate('/', { replace: true });
  }

  const toGroupsPage = () => {
    navigate('/groups', { replace: true });
  }

  const toLoginPage = useCallback(() => navigate('/', { replace: true }), [navigate]);
  const [cookies, setCookie] = useCookies();
  const [messageInput, setMessageInput] = useState("");
  const [messagesList, setMessagesList] = useState([]);
  const onMessageInputChanged = (e) => setMessageInput(e.target.value);

  useEffect(() => {
    async function getMessages() {
      let json = { "groupCode": globalState.groupCode };

      await fetch('http://localhost:8080/messages/getMessages', {
        method: 'POST', credentials: 'include',
        body: JSON.stringify(json),
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
        },
      }).then(res => res.json()).then(data => {
        setMessagesList([]);
        setMessagesList(data);
      });
    }

    const interval = setInterval(() => {
      getMessages();
    }, 200);

    return () => clearInterval(interval);
  }, []);



  async function sendMessage() {
    let json = { "content": messageInput, "groupCode": globalState.groupCode };

    await fetch('http://localhost:8080/messages/add', {
      method: 'POST', credentials: 'include',
      body: JSON.stringify(json),
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
    }).then(res => res.json()).then(data => {
      setMessageInput("");
    });
  }

  async function leaveGroup() {
    let json = { "groupCode": globalState.groupCode };

    await fetch('http://localhost:8080/groups/leave', {
      method: 'PATCH', credentials: 'include',
      body: JSON.stringify(json),
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
    }).then(res => toGroupsPage());
  }

  async function deleteGroup() {
    let json = { "groupCode": globalState.groupCode };

    await fetch('http://localhost:8080/groups/delete', {
      method: 'DELETE', credentials: 'include',
      body: JSON.stringify(json),
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
    }).then(res => toGroupsPage());
  }


  return (
    <div className='mainContainer'>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />

      <div className="mainHeader">

        <button onClick={toGroupsPage} className='backButton'><i class="fa-solid fa-arrow-left"></i></button>

        <div className="headerGroupName" onClick={() => setOpenModal(true)}>
          <h3>{globalState.groupName}</h3>
        </div>

        <div className="username">
          {
            userClicked ? <div></div> :
              <div className='normal' onClick={() => setUserClicked(true)}>
                <i class="fa-solid fa-user"></i>
                {cookies.username.replace(/_/g, ' ')}
              </div>
          }

          {
            userClicked ?
              <div className="userOptions">
                <button onClick={() => setUserClicked(false)} className='return'>
                  &#10096;
                </button>
                <button onClick={signOut} className='signOut'>Sign Out</button>
              </div>
              :
              <div></div>
          }

        </div>
      </div>


      {openModal ? <div></div> :
        <div className="messages">
          {messagesList.map((message) =>
            <div key={message.id} className={`singleMessage ${cookies.username.replace(/_/g, ' ') == message.author ? "userMessage" : ""}`}>
              <div className='messageUsername'>{cookies.username.replace(/_/g, ' ') == message.author ? "You" : message.author}</div>
              {message.content}
            </div>
          )}
        </div>
      }




      {
        openModal ?
          <div class="modal-content">
            <div class="modal-header">
              <span onClick={() => setOpenModal(false)} class="close">&times;</span>
              <p>Group options</p>
            </div>
            <div class="modal-body">
              <div className='groupOptions'>
                <button onClick={leaveGroup}>
                  Leave group
                </button>

                { /* cookies.username.replace(/_/g, ' ') == globalState.groupAdmin ? */}
                {(cookies.phoneNumber == globalState.groupAdmin || cookies.username.replace(/_/g, ' ') == globalState.groupAdmin) ?
                  <button onClick={deleteGroup}>
                    Delete group
                  </button>
                  :
                  <div></div>
                }

              </div>
            </div>

          </div>
          : <div></div>
      }




      <div className='newMessage'>

        <div className='inputBox'>
          <input type="text" placeholder='Message' value={messageInput} onChange={onMessageInputChanged} />
          <button onClick={sendMessage}>
            <i className="fa-solid fa-paper-plane"></i>
          </button>
        </div>

      </div>

    </div>
  );
};

export default Chat;