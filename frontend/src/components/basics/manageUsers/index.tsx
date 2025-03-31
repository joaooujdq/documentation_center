import { useEffect, useRef, useState } from "react";
import api from "../../../services/api";
import React from 'react'
import Alert from 'react-popup-alert'
import '../manageUsers/index.css'
import { FiArrowDown, FiArrowLeft, FiArrowRight, FiArrowUp, FiDelete, FiEdit, FiTrash } from "react-icons/fi";
import { Link, useHistory } from "react-router-dom";
import Popup from "reactjs-popup";
import 'reactjs-popup/dist/index.css';

interface iuser {
    codigo_user: number,
    nome_user: string,
    descricao_user: string,
    senha_user: string,
    admin_user: string,
    data_user: string,
    _links_user: i_links
}
interface i_links {
    self: iself
}
interface iself {
    href: string
}

const UserBody: React.FC = () => {

    const [Msg, setMsg] = useState<iuser[]>([]);
    const [Limit, setLimit] = useState<iuser[]>([]);
    const [direction, setDirection] = useState('desc');
    const [ordenation, setOrdenation] = useState('codigo');
    const [page, setPage] = useState(0);
    const [codigo, setCodigo] = useState('');
    const [nome, setNome] = useState('');
    const [senha, setSenha] = useState('');
    const [admin, setAdmin] = useState('');
    const [date, setDate] = useState('');
    const [post, setPost] = useState(false);
    const [descricao, setDescricao] = useState("");
    const [descricao2, setDescricao2] = useState("");
    const [isOpen, setIsOpen] = useState(false);
    const history = useHistory();

    useEffect(() => {
        const loadMsg = async () => {
            const response = await api.get('/v1/ts/users', { params: { page: page, limit: 4, direction: direction, ordenation: ordenation } });
            const limit = await api.get('/v1/ts/users');
            if (Object.keys(response.data).length) {
                setMsg(response.data._embedded.userDTOList);
            } else {
                setMsg([]);
            }
            setLimit(limit.data._embedded.userDTOList);
        }
        loadMsg()
    }, [page]);

    const deleteMsg = async (codigo: string) => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        const responseDelete = await api.delete('/v1/ts/users/' + codigo);
        window.location.reload()
        }
    }

    const updateMsg = async () => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        history.push('/updateUser')
        }
    }

    const criarNovo = async () => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        history.push('/newuser')
        }
    }


    const ExibirMsg = async (codigo: string) => {
        const response = await api.get('/v1/ts/users/' + codigo);
        setCodigo(response.data.codigo_user)
        setNome(response.data.nome_user)
        setDescricao2(response.data.descricao_user)
        setSenha(response.data.senha_user)
        setAdmin(response.data.admin_user)
        setDate(response.data.data_user)
    }

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
            <body id='UserBody'>
                <div id='sidebar' >
                
                        <button id='newObj' onClick={criarNovo}>Criar novo</button>
                    
                    <FiArrowUp id='carouselIcon' onClick={() => { if (page - 1 >= 0) setPage(page - 1) }}/>
                    {
                        Msg.map(m => (
                            <button id='buttons' >
                                <button id='text' onClick={() => { ExibirMsg(m.codigo_user.toString()) }}>
                                    <h6>{m.nome_user}</h6>
                                    <h4>{m.descricao_user}</h4>
                                </button>
                                <div id='iconsButtons'>
                                    
                                        <FiEdit id='editButton' onClick={updateMsg}></FiEdit>
                       
                                    <Popup trigger={<FiTrash id='deleteButton'></FiTrash>} position="center center" open={isOpen}>
                                        <h4 id='popupText'>Tem certeza que deseja excluir?</h4>
                                        <button id='confDelete' onClick={() => { deleteMsg(m.codigo_user.toString()) }}>Sim</button>
                                        <button id='confDelete' onClick={() => setIsOpen(!isOpen)}>Nao</button>
                                    </Popup>
                                </div>
                            </button>
                        ))}
                    <FiArrowDown id='carouselIcon'  onClick={() => { if (Msg.length == 4 && page + 1 < Limit.length / 4) { setPage(page + 1) } }} />
                </div>
                <div >
                    <h2 id='TitleBar'>Lista de users:</h2>
                    <ul id='UserUl'>
                        <div id='UserForm'>
                            <div id='divH1'>
                                <li>
                                    <h1>Id do User: {codigo}</h1>
                                    <h1>Nome: {nome}</h1>
                                    <h1>Descrição: {descricao2}</h1>
                                    <h1>Senha: {senha}</h1>
                                    <h1>Admin: {admin}</h1>
                                    <h1>Data: {date}</h1>
                                </li>
                            </div>
                        </div>
                    </ul>
                </div>
            </body>
        </>
    );
}
export default UserBody;
