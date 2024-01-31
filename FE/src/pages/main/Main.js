import Character from "../../components/main/Character";
import Footer from "../../commons/Footer"
import styles from '../../styles/main/main.module.css'


const Main = () => {
    return (
    <div className={styles.wrapper}>
        <Character />
        <Footer />

    </div>
  )
};
  
  export default Main; 
