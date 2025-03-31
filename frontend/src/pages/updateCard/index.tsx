import Footer from '../../components/basics/footer';
import Header from '../../components/basics/header';
import Title from  '../../components/basics/title';
import HomeBody from '../../components/basics/updateCard'

const Home: React.FC = () => {
    return (

        <>
            <Title />
            <Header />
            <HomeBody />
            <Footer />
        </>
    );
}

export default Home;