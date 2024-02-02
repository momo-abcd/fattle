import React from "react";
import styles from "../../styles/main/FoodUpload.module.css";
import uploadbutton from "../../assets/images/main/uploadbutton.svg";
import { API } from '../../services/main/URL';

function FoodUpload() {
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.box}>
          <span className={styles.label1}>아침</span>
          <img src={uploadbutton} alt='' className={styles.positionAbsolute}/>
        </div>
        <div className={styles.box}>
          <span className={styles.label2}>점심</span>
          <img src={uploadbutton} alt='' className={styles.positionAbsolute}/>
        </div>
        <div className={styles.box}>
          <span className={styles.label3}>저녁</span>
          <img src={uploadbutton} alt='' className={styles.positionAbsolute}/>
        </div>
        <div className={styles.box}>
          <span className={styles.label4}>간식</span>
          <img src={uploadbutton} alt='' className={styles.positionAbsolute}/>
        </div>
      </div>
    </div>
  );
}

export default FoodUpload;
