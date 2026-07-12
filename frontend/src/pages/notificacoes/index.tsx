import React from 'react';
import Footer from '../../components/basics/footer';
import Header from '../../components/basics/header';
import Title from '../../components/basics/title';
import NotificacoesBody from '../../components/basics/notificacoes';

const Notificacoes: React.FC = () => {
    return (
        <>
            <Title />
            <Header />
            <NotificacoesBody />
            <Footer />
        </>
    );
}

export default Notificacoes;
