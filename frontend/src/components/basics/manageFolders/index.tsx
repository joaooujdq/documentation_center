import { useEffect, useState } from "react";
import api from "../../../services/api";
import React from 'react'
import Alert from 'react-popup-alert'
import '../manageFolders/index.css'
import { useHistory } from 'react-router-dom';
import { FiArrowDown, FiArrowUp, FiEdit, FiTrash } from "react-icons/fi";
import { Link } from "react-router-dom";
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
interface ifolder {
    codigo_folder: number,
    nome_folder: string,
    descricao_folder: string,
    data_folder: string,
    branchDTO: ibranch,
    _links_folder: i_links
}
interface i_links {
    self: iself
}
interface iself {
    href: string
}

const FolderBody: React.FC = () => {
    const [Msg, setMsg] = useState<ifolder[]>([]);
    const [Limit, setLimit] = useState<ifolder[]>([]);
    const [direction, setDirection] = useState('desc');

    const history = useHistory();
    const [ordenation, setOrdenation] = useState('codigo');
    const [page, setPage] = useState(0);
    const [codigo, setCodigo] = useState('');
    const [nome, setNome] = useState('');
    const [userId, setUserId] = useState('');
    const [userNome, setUserNome] = useState('');
    const [data, setData] = useState('');
    const [branchId, setBranchId] = useState('');
    const [branchNome, setBranchNome] = useState('');
    const [post, setPost] = useState(false);
    const [descricao, setDescricao] = useState("");
    const [descricao2, setDescricao2] = useState("");
    const [isOpen, setIsOpen] = useState(false);

    useEffect(() => {
        const loadMsg = async () => {
            const response = await api.get('/v1/ts/folders', { params: { page: page, limit: 4, direction: direction, ordenation: ordenation } });
            const limit = await api.get('/v1/ts/folders');
            if (Object.keys(response.data).length) {
                setMsg(response.data._embedded.folderDTOList);
            } else {
                setMsg([]);
            }
            setLimit(limit.data._embedded.folderDTOList);
        }
        loadMsg()
    }, [page]);

    const deleteMsg = async (codigo: string) => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        const responseDelete = await api.delete('/v1/ts/folders/' + codigo);
        window.location.reload()
        }
    }

    const updateMsg = async () => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        history.push('/updateFolder')
        }
    }

    const criarNovo = async () => {
        if(localStorage.getItem("admin") != 'true'){
            setDescricao("Você não tem permissão")
            setPost(!post)
        }else{
        history.push('/newfolder')
        }
    }

    const ExibirMsg = async (codigo: string) => {
        const response = await api.get('/v1/ts/folders/' + codigo);
        setCodigo(response.data.codigo_folder)
        setNome(response.data.nome_folder)
        setDescricao2(response.data.descricao_folder)
        setData(response.data.data_folder)
        setBranchId(response.data.branchDTO.codigo_branch)
        setBranchNome(response.data.branchDTO.nome_branch)
        setUserId(response.data.branchDTO.userDTO.codigo_user)
        setUserNome(response.data.branchDTO.userDTO.nome_user)
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
            <body id='FolderBody'>
                <div id='sidebar' >
                    
                        <button id='newObj' onClick={criarNovo}>Criar novo</button>
                    
                    <FiArrowUp id='carouselIcon' onClick={() => { if (page - 1 >= 0) setPage(page - 1) }} />
                    {
                        Msg.map(m => (
                            <button id='buttons' >
                                <button id='text' onClick={() => { ExibirMsg(m.codigo_folder.toString()) }}>
                                    <h6>{m.nome_folder}</h6>
                                    <h4>{m.descricao_folder}</h4>
                                </button>
                                <div id='iconsButtons'>
                                    
                                        <FiEdit id='editButton' onClick={updateMsg}></FiEdit>
                                
                                    <Popup trigger={<FiTrash id='deleteButton'></FiTrash>} position="center center" open={isOpen}>
                                        <h4 id='popupText'>Tem certeza que deseja excluir?</h4>
                                        <button id='confDelete' onClick={() => { deleteMsg(m.codigo_folder.toString()) }}>Sim</button>
                                        <button id='confDelete' onClick={() => setIsOpen(!isOpen)}>Nao</button>
                                    </Popup>
                                </div>
                            </button>
                        ))}
                    <FiArrowDown id='carouselIcon' onClick={() => { if (Msg.length == 4 && page + 1 < Limit.length / 4) { setPage(page + 1) } }} />
                </div>
                <div >
                    <h2 id='TitleBar'>Lista de folders:</h2>
                    <ul id='FolderUl'>
                        <div id='FolderForm'>
                            <div id='divH1'>
                                <li>
                                    <h1>Id do Folder: {codigo}</h1>
                                    <h1>Nome: {nome}</h1>
                                    <h1>Descrição: {descricao2}</h1>
                                    <h1>Data: {data}</h1>
                                    <h1>Id da Branch: {branchId}</h1>
                                    <h1>Nome da Branch: {branchNome}</h1>
                                    <h1>Id do User: {userId}</h1>
                                    <h1>Nome do User: {userNome}</h1>
                                </li>
                            </div>
                        </div>
                    </ul>
                </div>
            </body>
        </>
    );
}
export default FolderBody;
