import { useEffect, useState } from "react";
import api from "../../../services/api";
import './index.css'
import React from 'react';
import Alert from 'react-popup-alert'
import 'reactjs-popup/dist/index.css';

interface iuser {
    codigo_user: number,
    nome_user: string,
    senha_user: string,
    admin_user: boolean,
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
const HomeBody: React.FC = () => {
    const [inputNome, setInputNome] = useState('');
    const [inputDescricao2, setInputDescricao2] = useState('');
    const [inputImageLink, setInputImageLink] = useState('');
    const [inputThumbnail, setInputThumbnail] = useState('');
    const [folderId, setFolderId] = useState<ifolder[]>([]);
    const [inputFolderId, setInputFolderId] = useState('');
    const [post, setPost] = useState(false);
    const [descricao, setDescricao] = useState("");


    const postMsg = async () => {
        if(folderId == null){
            setDescricao("Não existe Branch com esse ID")
            setPost(!post)
        }else{
            let flag2 = false;
        const response = await api.post('/v1/ts/cards/', {
            "nome_card": inputNome,
            "descricao_card": inputDescricao2,
            "imageLink_card": inputImageLink,
            "thumbnail_card": inputThumbnail,
            "folderDTO": folderId
        }).then(response => response.data)
            .catch(async error => {
                if (error.response) {
                    await setDescricao(error.response.data.descricao)
                    flag2 = true;
                    setPost(!post)
                }
            });
        if (!flag2) {
            window.location.reload();
        }
        }
        
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
    function onShowAlert(type: string) {
        setAlert({
            type: type,
            text: descricao,
            show: true
        })
    }

    useEffect(() => {
        const findFolderById = async () => {
            const response = await api.get('/v1/ts/folders/' + inputFolderId)
            setFolderId(response.data)
        }
        findFolderById()
    }, [inputFolderId])

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
            <body id='CriarCardBody'>
                <h2 id='TitleBar'>Cadastro de Card</h2>
                <ul id='CardUl'>
                    <div id='CardForm'>
                        <div id='divH1'>
                            <h1>Nome*: </h1>
                            <h1>Descrição: </h1>
                            <h1>Image Link: </h1>
                            <h1>Thumbnail: </h1>
                            <h1>ID do Folder*: </h1>
                        </div>
                        <div id='divInput'>
                            <input id='input' type="text" value={inputNome} onChange={e => setInputNome(e.target.value)} required />
                            <input id='input' type="text" value={inputDescricao2} onChange={e => setInputDescricao2(e.target.value)} required />
                            <input id='input' type="text" value={inputImageLink} onChange={e => setInputImageLink(e.target.value)} required />
                            <input id='input' type="text" value={inputThumbnail} onChange={e => setInputThumbnail(e.target.value)} required />
                            <input id='input' type="text" value={inputFolderId} onChange={e => setInputFolderId(e.target.value)} required />
                        </div>
                    </div>
                    <button type="submit" onClick={postMsg}>Cadastrar</button>
                </ul>
            </body>
        </>
    );
}
export default HomeBody;
