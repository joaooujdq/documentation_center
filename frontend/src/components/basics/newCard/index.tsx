import { useEffect, useState } from "react";
import api from "../../../services/api";
import './index.css'
import React from 'react';
import Alert from 'react-popup-alert'
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
    const [inputNome, setInputNome] = useState('');
    const [inputDescricao, setInputDescricao] = useState('');
    const [inputThumbnail, setInputThumbnail] = useState('');
    const [folderId, setFolderId] = useState<ifolder[]>([]);
    const [inputFolderId, setInputFolderId] = useState('');
    const [post, setPost] = useState(false);
    const [descricao, setDescricao] = useState("");

    const [resumo, setResumo] = useState('');
    const [loadingResumo, setLoadingResumo] = useState(false);
    const [tags, setTags] = useState<string[]>([]);
    const [categoria, setCategoria] = useState('');
    const [loadingTags, setLoadingTags] = useState(false);

    const gerarResumo = async () => {
        if (!inputNome && !inputDescricao) return;
        setLoadingResumo(true);
        try {
            const response = await api.post('/v1/ts/ia/resumo', { titulo: inputNome, conteudo: inputDescricao });
            setResumo(response.data.resumo || '');
        } catch {
            setResumo('Não foi possível gerar o resumo.');
        } finally {
            setLoadingResumo(false);
        }
    };

    const sugerirTags = async () => {
        if (!inputNome && !inputDescricao) return;
        setLoadingTags(true);
        try {
            const response = await api.post('/v1/ts/ia/sugestoes', { titulo: inputNome, conteudo: inputDescricao });
            const json = JSON.parse(response.data);
            setTags(json.tags || []);
            setCategoria(json.categoria || '');
        } catch {
            setTags([]); setCategoria('');
        } finally {
            setLoadingTags(false);
        }
    };

    const removerTag = (tag: string) => setTags(tags.filter(t => t !== tag));

    const postMsg = async () => {
        if (folderId == null) {
            setDescricao("Não existe Branch com esse ID");
            setPost(!post);
        } else {
            let flag2 = false;
            await api.post('/v1/ts/cards/', {
                "nome_card": inputNome,
                "descricao_card": inputDescricao,
                "thumbnail_card": inputThumbnail,
                "resumo_card": resumo || null,
                "tags_card": tags.length > 0 ? tags.join(',') : null,
                "categoria_card": categoria || null,
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
        }
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
            <body id='CriarCardBody'>
                <h2 id='TitleBar'>Cadastro de Card</h2>
                <ul id='CardUl'>
                    <div id='CardForm'>
                        <div id='divH1'>
                            <h1>Nome*: </h1>
                            <h1>Thumbnail: </h1>
                            <h1>ID do Folder*: </h1>
                        </div>
                        <div id='divInput'>
                            <input id='input' type="text" value={inputNome} onChange={e => setInputNome(e.target.value)} required />
                            <input id='input' type="text" value={inputThumbnail} onChange={e => setInputThumbnail(e.target.value)} />
                            <input id='input' type="text" value={inputFolderId} onChange={e => setInputFolderId(e.target.value)} required />
                        </div>
                    </div>

                    <div id='editor-section'>
                        <h3>Conteúdo:</h3>
                        <RichTextEditor content={inputDescricao} onChange={setInputDescricao} />
                    </div>

                    <div id='ia-section'>
                        <h3>Resumo automático (TL;DR)</h3>
                        <button type="button" onClick={gerarResumo}
                            disabled={loadingResumo || (!inputNome && !inputDescricao)} id='btn-ia'>
                            {loadingResumo ? 'Gerando...' : '✨ Gerar Resumo via IA'}
                        </button>
                        {resumo !== '' && (
                            <div id='ia-resumo'>
                                <label>Resumo (editável antes de salvar):</label>
                                <textarea value={resumo} onChange={e => setResumo(e.target.value)} rows={3} id='textarea-resumo' />
                                <button type="button" id='btn-descartar' onClick={() => setResumo('')}>Descartar resumo</button>
                            </div>
                        )}
                    </div>

                    <div id='ia-section'>
                        <h3>Tags e Categoria (IA)</h3>
                        <button type="button" onClick={sugerirTags}
                            disabled={loadingTags || (!inputNome && !inputDescricao)} id='btn-ia'>
                            {loadingTags ? 'Analisando...' : '🏷️ Sugerir Tags via IA'}
                        </button>
                        {(tags.length > 0 || categoria) && (
                            <div id='ia-tags'>
                                {categoria && (
                                    <div id='categoria-box'>
                                        <label>Categoria: </label>
                                        <input type="text" value={categoria} onChange={e => setCategoria(e.target.value)} id='input-categoria' />
                                    </div>
                                )}
                                {tags.length > 0 && (
                                    <div id='tags-chips'>
                                        <label>Tags (clique para remover):</label>
                                        <div id='chips-container'>
                                            {tags.map(tag => (
                                                <span key={tag} className='tag-chip' onClick={() => removerTag(tag)} title='Clique para remover'>
                                                    {tag} ✕
                                                </span>
                                            ))}
                                        </div>
                                    </div>
                                )}
                            </div>
                        )}
                    </div>

                    <button type="submit" onClick={postMsg}>Cadastrar</button>
                </ul>
            </body>
        </>
    );
};

export default HomeBody;
