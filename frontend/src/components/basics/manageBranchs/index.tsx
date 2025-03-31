import { useEffect, useState } from "react";
import api from "../../../services/api";
import React from 'react'
import Alert from 'react-popup-alert'
import '../manageBranchs/index.css'
import { FiArrowDown, FiArrowUp, FiEdit, FiTrash } from "react-icons/fi";
import { Link, useHistory } from "react-router-dom";
import Popup from "reactjs-popup";

interface iuser {
    codigo_user: number,
    nome_user: string,
    descricao_user: string,
    senha_user: string,
    admin_user: string,
    data_user: string,
    _links_user: i_links
}
interface ibranch {
    codigo_branch: number,
    nome_branch: string,
    descricao_branch: string,
    data_branch: string,
    userDTO: iuser,
    _links_branch: i_links
}

interface i_links {
    self: iself
}
interface iself {
    href: string
}

const BranchBody: React.FC = () => {
    const [Msg, setMsg] = useState<ibranch[]>([]);
    const [Limit, setLimit] = useState<ibranch[]>([]);
    const [direction, setDirection] = useState('desc');
    const [ordenation, setOrdenation] = useState('codigo');
    const [page, setPage] = useState(0);
    const [codigo, setCodigo] = useState('');
    const [nome, setNome] = useState('');
    const [data, setData] = useState('');
    const [userCodigo, setUserCodigo] = useState('');
    const [userNome, setUserNome] = useState('');
    const [post, setPost] = useState(false);
    const [descricao, setDescricao] = useState("");
    const [isOpen, setIsOpen] = useState(false);
    const [descricao2, setDescricao2] = useState("");
    const history = useHistory();

    useEffect(() => {
        const loadMsg = async () => {
            const response = await api.get('/v1/ts/branchs', { params: { page: page, limit: 4, direction: direction, ordenation: ordenation } });
            const limit = await api.get('/v1/ts/branchs');
            if (Object.keys(response.data).length) {
                setMsg(response.data._embedded.branchDTOList);
            } else {
                setMsg([]);
            }
            setLimit(limit.data._embedded.branchDTOList);
        }
        loadMsg()
    }, [page]);

    const deleteMsg = async (codigo: string) => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        const responseDelete = await api.delete('/v1/ts/branchs/' + codigo);
        window.location.reload()
        }
    }

    const updateMsg = async () => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        history.push('/updateBranch')
        }
    }

    const criarNovo = async () => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        history.push('/newbranch')
        }
    }

    const ExibirMsg = async (codigo: string) => {
        const response = await api.get('/v1/ts/branchs/' + codigo);
        setCodigo(response.data.codigo_branch)
        setNome(response.data.nome_branch)
        setDescricao2(response.data.descricao_branch)
        setData(response.data.data_branch)
        setUserCodigo(response.data.userDTO.codigo_user)
        setUserNome(response.data.userDTO.nome_user)
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
            <body id='BranchBody'>
                <div id='sidebar' >
                   
                        <button id='newObj' onClick={criarNovo}>Criar novo</button>
                   
                    <FiArrowUp id='carouselIcon' onClick={() => { if (page - 1 >= 0) setPage(page - 1) }} />
                    {
                        Msg.map(m => (
                            <button id='buttons' >
                                <button id='text' onClick={() => { ExibirMsg(m.codigo_branch.toString()) }}>
                                    <h6>{m.nome_branch}</h6>
                                    <h4>{m.descricao_branch}</h4>
                                </button>
                                <div id='iconsButtons'>
                                    
                                        <FiEdit id='editButton' onClick={updateMsg}></FiEdit>
                               
                                    <Popup trigger={<FiTrash id='deleteButton'></FiTrash>} position="center center" open={isOpen}>
                                        <h4 id='popupText'>Tem certeza que deseja excluir?</h4>
                                        <button id='confDelete' onClick={() => { deleteMsg(m.codigo_branch.toString()) }}>Sim</button>
                                        <button id='confDelete' onClick={() => setIsOpen(!isOpen)}>Nao</button>
                                    </Popup>
                                </div>
                            </button>
                        ))}
                    <FiArrowDown id='carouselIcon' onClick={() => { if (Msg.length == 4 && page + 1 < Limit.length / 4) { setPage(page + 1) } }}/>
                </div>
                <div >
                    <h2 id='TitleBar'>Lista de branchs:</h2>
                    <ul id='BranchUl'>
                        <div id='BranchForm'>
                            <div id='divH1'>
                                <li>
                                    <h1>Id do Branch: {codigo}</h1>
                                    <h1>Nome: {nome}</h1>
                                    <h1>Descrição: {descricao2}</h1>
                                    <h1>Data de criação: {data}</h1>
                                    <h1>Id do User: {userCodigo}</h1>
                                    <h1>Nome do user: {userNome}</h1>
                                </li>
                            </div>
                        </div>
                    </ul>
                </div>
            </body>
        </>
    );
}
export default BranchBody;
