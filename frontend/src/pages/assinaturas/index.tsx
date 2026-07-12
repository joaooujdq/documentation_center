import React from 'react';
import Footer from '../../components/basics/footer';
import Header from '../../components/basics/header';
import Title from '../../components/basics/title';
import AssinaturasBody from '../../components/basics/assinaturas';

const Assinaturas: React.FC = () => {
    return (
        <>
            <Title />
            <Header />
            <AssinaturasBody />
            <Footer />
        </>
    );
}

export default Assinaturas;
