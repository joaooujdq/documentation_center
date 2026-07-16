import { useEffect, useState } from "react";
import api from "../../../services/api";
import Alert from 'react-popup-alert'
import './index.css'
import React from 'react';
import 'reactjs-popup/dist/index.css';
import RichTextEditor from '../richTextEditor';

interface ifolder {
    codigo_folder: number,
    nome_folder: string,
    descricao_folder: string,
    data_folder: string,
}
interface i_links { self: { href: string } }

const HomeBody: React.FC = () => {
    const [inputCodigo, setInputCodigo] = useState('');
    const [inputNome, setInputNome] = useState('');
    const [inputDescricao, setInputDescricao] = useState('');
    const [inputThumbnail, setInputThumbnail] = useState('');
    const [folderId, setFolderId] = useState<ifolder[]>([]);
    const [inputFolderId, setInputFolderId] = useState('');
    const [post, setPost] = useState(false);
    const [descricao, setDescricao] = useState("");

    const postMsg = async () => {
        let flag2 = false;
        await api.put('/v1/ts/cards/' + inputCodigo, {
            "nome_card": inputNome,
            "descricao_card": inputDescricao,
            "thumbnail_card": inputThumbnail,
            "folderDTO": folderId
        }).then(response => response.data)
            .catch(async error => {
                if (error.response) {
                    await setDescricao(error.response.data.descricao);
                    flag2 = true;
                    setPost(!post);
                }
            });
        if (!flag2) window.location.reload();
    };

    useEffect(() => {
        if (post) onShowAlert('error');
    }, [post]);

    const [alert, setAlert] = React.useState({ type: 'error', text: descricao, show: false });

    function onCloseAlert() {
        setAlert({ type: '', text: '', show: false });
        setPost(!post);
    }
    function onShowAlert(type: string) {
        setAlert({ type, text: descricao, show: true });
    }

    useEffect(() => {
        const findFolderById = async () => {
            const response = await api.get('/v1/ts/folders/' + inputFolderId);
            setFolderId(response.data);
        };
        if (inputFolderId) findFolderById();
    }, [inputFolderId]);

    return (
        <>
            <Alert
                header={''} btnText={'Fechar'} text={alert.text} type={alert.type} show={alert.show}
                onClosePress={onCloseAlert} pressCloseOnOutsideClick={true} showBorderBottom={true}
                alertStyles={{
                    "background-color": "#f8f9fa", "width": "300px", "height": "100px",
                    "display": "flex", "flex-direction": "column", "align-items": "center",
                    "justify-content": "center", "left": "42%", "bottom": "30%",
                    "border-radius": "8px", "border": "2px solid #C4C4C4", "position": "absolute"
                }}
                headerStyles={{}} textStyles={{}}
                buttonStyles={{
                    "background-color": "#efefef", "border-radius": "8px", "margin-bottom": "10px",
                    "text-decoration": "none", "button-decoration": "none", "align-text": "center",
                    "width": "70px", "border": "2px solid #C4C4C4", "height": "30px",
                    "color": "#000", "padding-left": "10px"
                }}
            />
            <body id='CriarUserBody'>
                <h2 id='TitleBar'>Atualizar Card</h2>
                <ul id='UserUl'>
                    <div id='UserForm'>
                        <div id='divH1'>
                            <h1>Id*: </h1>
                            <h1>Nome*: </h1>
                            <h1>Thumbnail: </h1>
                            <h1>ID da Folder*: </h1>
                        </div>
                        <div id='divInput'>
                            <input id='input' type="text" value={inputCodigo} onChange={e => setInputCodigo(e.target.value)} required />
                            <input id='input' type="text" value={inputNome} onChange={e => setInputNome(e.target.value)} required />
                            <input id='input' type="text" value={inputThumbnail} onChange={e => setInputThumbnail(e.target.value)} />
                            <input id='input' type="text" value={inputFolderId} onChange={e => setInputFolderId(e.target.value)} required />
                        </div>
                    </div>

                    <div id='editor-section'>
                        <h3>Conteúdo:</h3>
                        <RichTextEditor content={inputDescricao} onChange={setInputDescricao} />
                    </div>

                    <button type="submit" onClick={postMsg}>Atualizar</button>
                </ul>
            </body>
        </>
    );
};

export default HomeBody;
