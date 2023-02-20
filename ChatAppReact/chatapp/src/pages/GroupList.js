import React, { useEffect, useState, useCallback } from 'react';
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

const GroupList = ({ globalState, setGlobalState }) => {
    const navigate = useNavigate();
    const [cookies, setCookie] = useCookies();

    const [phoneNumbers, setPhoneNumbers] = useState([]);
    const [currentNumber, setCurrentNumber] = useState('');
    const [isDm, setIsDm] = useState(true);
    const [groupName, setGroupName] = useState('');
    const [openModal, setOpenModal] = useState(false);

    const [groups, setGroups] = useState([]);

    const toChatPage = useCallback(() => navigate('/chat', { replace: true }), [navigate]);

    const onGroupNameChanged = (e) => setGroupName(e.target.value);

    const onInputChanged = (e) => setCurrentNumber(e.target.value);

    const [userClicked, setUserClicked] = useState(false);

    const signOut = () => {
        setCookie("phoneNumber", null);
        setCookie("token", null);
        setCookie("username", null);
        navigate('/', { replace: true });
    };


    const toChat = (groupCode, groupName, groupAdmin) => {
        setGlobalState({
            groupCode: groupCode,
            groupName: groupName,
            groupAdmin: groupAdmin
        });
        navigate('/chat', { replace: true });
    };


    const addPhoneNumber = () => {
        setPhoneNumbers(current => [...current, currentNumber]);
        if ((phoneNumbers.length + 1) > 1) {
            setIsDm(false);
        }
        setCurrentNumber('');
    };


    async function createGroup() {
        let json = { "groupName": groupName, "userList": phoneNumbers };

        await fetch('http://localhost:8080/groups/add', {
            method: 'POST', credentials: 'include',
            body: JSON.stringify(json),
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
            },
        }).then(res => setOpenModal(false))
    }


    useEffect(() => {
        async function getGroups() {

            await fetch('http://localhost:8080/groups/getGroups', {
                method: 'GET', credentials: 'include',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                },
            }).then(res => res.json()).then(data => {
                data.forEach(group => {
                    if (group.dm) {
                        if (group.groupName == cookies.username.replace(/_/g, ' ')) {
                            group.groupName = group.groupAdminNumber;
                        }
                    }
                })
                setGroups(data);
            });
        }

        const interval = setInterval(() => {
            getGroups();
        }, 200);

        return () => clearInterval(interval);
    }, []);


    return (
        <div className='mainContainer'>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />

            <div className="mainHeader">
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
                <div className="groupsContainer">
                    {(groups != null) && (groups?.length >= 1) ? groups.map(group => {
                        if (group == null) return <div></div>
                        return <div onClick={() => toChat(group.groupCode, group.groupName, group.groupAdminNumber)} key={group.groupCode} className="group">
                            <div className="groupName">
                                {group.groupName}
                            </div>
                        </div>
                    }
                    ) : <div></div>}
                </div>
            }



            {openModal ?
                <div class="modal-content">
                    <div class="modal-header">
                        <span onClick={() => setOpenModal(false)} class="close">&times;</span>
                        <h2>Add person/people</h2>
                    </div>
                    <div class="modal-body">

                        <div className="newGroup">
                            <div className="phoneNumberInput">
                                <input placeholder='Phone number' value={currentNumber} onChange={onInputChanged} />
                                <button onClick={addPhoneNumber}>+ Add</button>
                            </div>
                            <div className="phoneNumbers">
                                {phoneNumbers.map(number =>
                                    <p key={number}>{number}</p>
                                )}
                            </div>

                            {isDm ?
                                <div></div>
                                :
                                <div className="groupName">
                                    <input value={groupName} onChange={onGroupNameChanged} placeholder='Group name' />
                                </div>
                            }


                            <button className='createGroupBtn' onClick={createGroup}>
                                Create {isDm ? "chat" : "group"}
                            </button>
                        </div>

                    </div>

                </div>
                : <div></div>}




            <div className="mainFooter">
                <button className='newGroupBtn' onClick={() => setOpenModal(true)}>
                    New Group
                </button>
            </div>

        </div>
    );
};

export default GroupList;