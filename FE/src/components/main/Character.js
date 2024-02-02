import styles from "../../styles/main/Character.module.css"
import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Routes, Route, Link} from 'react-router-dom'
import ExpHistory from './ExpHistory'
import Frame from '../../assets/images/main/Frame.png'
import Frame2 from '../../assets/images/main/Frame2.png'
import panda from '../../assets/images/main/panda.png'
import carbon from '../../assets/images/main/carbon.png'
import protein from '../../assets/images/main/protein.png'
import fat from '../../assets/images/main/fat.png'


function Character() {
  const [mainUserData, setMainUserData] = useState(null)

  useEffect((usercode) => {
      axios.get(`http://localhost:5000/mainuser`)
        .then(response=>{
            setMainUserData(response.data)
        })
        .catch(error => {
            console.error('메인 데이터를 불러오는 중 에러 발생:', error)
          })
  }, [])

  const maxGrowthExp = 200
  const calculateCircumference = (radius) => 2 * Math.PI * radius;

  const radius = 150; // 반지름 설정
  const circumference = calculateCircumference(radius);

  
  return (
    <div className={styles.wrapper}>
      {mainUserData && (
        <div>
          <p>{mainUserData.nickname}</p>
          {/* <p>{mainUserData.ranking}등</p> */}
          {/* <img src={mainUserData.imgPath} alt="캐릭터 이미지" /> */}

          <svg height={radius * 2} width={radius * 2} className={styles.progressbar2}>
          <circle
            className={styles.circle}
            stroke="#ffffff"
            strokeWidth="15"
            fill="transparent"
            r={radius}
            cx={radius}
            cy={radius}
            />
          <image
            href={panda}
            x={radius - 100} 
            y={radius - 100} 
            width="200"
            height="200"
            />
          <circle
            className={styles.filledcircle}
            stroke="#98FF87"
            strokeWidth="15"
            fill="transparent"
            r={radius}
            cx={radius}
            cy={radius}
            strokeDasharray={circumference}
            strokeDashoffset={circumference - (mainUserData.growthExp / maxGrowthExp) * circumference}
            />
        </svg>
          <p>성장 경험치: {mainUserData.growthExp}</p>
          <p>스택 경험치: {mainUserData.stackExp}</p>


          <Link to="/history">
            <button>경험치 히스토리</button>
          </Link>

          <p>{mainUserData.calory} / {mainUserData.goalCalory}kcal</p>
          <div className={styles.progressbar}>
            <div className={styles.remainingbar} style={{ width: `${100 - (mainUserData.calory / mainUserData.goalCalory) * 100}%` }}></div>
          </div>

          <div className={styles.nutrienticons}>
            <div className={styles.nutrienticon}>
              <img src={carbon} alt='' />
              <div className={styles.nutrientbar}>
                <div className={styles.remainingbar2} style={{ width: `${(100 / mainUserData.goalcarbo) * 100}%` }}></div>
                <p>100 / {mainUserData.goalcarbo}g</p>
              </div>
              <img src={protein} alt='' />
              <div className={styles.nutrientbar}>
                <div className={styles.remainingbar3} style={{ width: `${(70 / mainUserData.goalprotein) * 100}%` }}></div>
              </div>

              <img src={fat} alt='' />
              <div className={styles.nutrientbar}>
                <div className={styles.remainingbar4} style={{ width: `${(50 / mainUserData.goalfat) * 100}%` }}></div>
              </div>
            </div>
          </div>


          
          <div className={styles.infobar}>
            <p>
              <img src={Frame} alt='' />
              신장: {mainUserData.height}cm 체중: {mainUserData.weight}kg
              <img src={Frame2} alt='' />
            </p>
          </div>
        </div>
      )}

        
        <Routes>
          <Route path="/history" element={<ExpHistory />} />
        </Routes>
    </div>
  );
}

export default Character