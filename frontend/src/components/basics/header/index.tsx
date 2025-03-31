import './index.css'
import {Link} from 'react-router-dom'
import Alert from 'react-popup-alert'
import { useEffect, useState } from 'react'
import React from 'react'
import Popup from 'reactjs-popup'
import { useHistory } from 'react-router-dom';
import api from 'services/api'


const Header = () => {
    const history = useHistory();
  const [post, setPost] = useState(false);
  const [descricao, setDescricao] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const [nome, setNome] = useState("");
  const [codigo, setCodigo] = useState("");
  const [senha, setSenha] = useState("");
  const [logado, setLogado] = useState("");


  const login = async () => {
    const response = await api.get('/v1/ts/users/nomes', { params: {  nomes: nome, senhas: senha} });
    setCodigo(response.data.admin_user)
    if(response != null){
        localStorage.setItem("login", nome);
        localStorage.setItem("admin", response.data.admin_user);
        setLogado(nome)
    }
    
}

useEffect(() => {
    if(localStorage.getItem("login")?.toString === null){        
    }else{
        setLogado(localStorage.getItem("login") || '' )
    }

}, [])

  useEffect(() => {
    if (post) {
        onShowAlert('error')
    }
}, [post])
const [alert, setAlert] = React.useState({
    type: 'error',
    text: descricao,
    show: false
})
function onCloseAlert() {
    setAlert({
        type: '',
        text: '',
        show: false
    })
    setPost(!post)
}
async function onShowAlert(type: string) {
    await setAlert({
        type: type,
        text: descricao,
        show: true
    })
}

const permissionFolder = async () => {
    if(localStorage.getItem("admin") != 'true'){
        setDescricao("Você não tem permissão")
        setPost(!post)
    }else{
        history.push('/managefolders')
    }
}

const permissionBranch = async () => {
    if(localStorage.getItem("admin") != 'true'){
        setDescricao("Você não tem permissão")
        setPost(!post)
    }else{
        history.push('/managebranchs')
    }
}

const permissionUser = async () => {
    if(localStorage.getItem("admin") != 'true'){
        setDescricao("Você não tem permissão")
        setPost(!post)
    }else{
        history.push('/manageusers')
    }
}


const permissionCard = async () => {
    if(localStorage.getItem("admin") != 'true'){
        setDescricao("Você não tem permissão")
        setPost(!post)
    }else{
        history.push('/')
    }
}

    return (
        <>
        <Alert
                header={''}
                btnText={'Fechar'}
                text={alert.text}
                type={alert.type}
                show={alert.show}
                onClosePress={onCloseAlert}
                pressCloseOnOutsideClick={true}
                showBorderBottom={true}
                alertStyles={{
                    "background-color": "#f8f9fa",
                    "width": "300px",
                    "height": "100px",
                    "display": "flex",
                    "flex-direction": "column",
                    "align-items": "center",
                    "justify-content": "center",
                    "left": "42%",
                    "bottom": "30%",
                    "border-radius": "8px",
                    "border": "2px solid #C4C4C4",
                    "position": "absolute"
                }}
                headerStyles={{}}
                textStyles={{}}
                buttonStyles={{
                    "background-color": "#efefef",
                    "border-radius": "8px",
                    "margin-bottom": "10px",
                    "text-decoration": "none",
                    "button-decoration": "none",
                    "align-text": "center",
                    "width": "70px",
                    "border": "2px solid #C4C4C4",
                    "height": "30px",
                    "color": "#000",
                    "padding-left": "10px"
                }}
                />
         <header className="header mt-auto py-3 ">
     
  <div className="container" id="container-header">

    <button onClick={permissionBranch} >Manage Branchs</button>

    <button onClick={permissionFolder}>Manage Folders</button>

    <button onClick={permissionUser}>Manage Users</button>
    
    <button onClick={permissionCard}>Manage Cards</button>
 

    <Popup trigger={<button >  Change Account</button>} position="center center" open={isOpen}>
                                            <h4 id='popupText'>Entre com seu perfil: </h4>
                                            <input id='username' value={nome} onChange={e => { setNome(e.target.value) }}/>
                                            <input id='password' value={senha} onChange={e => { setSenha(e.target.value) }}/>
                                            <button id='login' onClick={login}>Login</button>
                                        </Popup>
  <h2 id='logado'>
    <h1>Usuario logado: </h1>
    <input value={logado}></input>
  </h2>
  </div>
     </header>
        </>
    );
}

export default Header;