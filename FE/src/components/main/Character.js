
import styles from "../../styles/main/Character.module.css"
import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Routes, Route, Link} from 'react-router-dom'
import ExpHistory from './ExpHistory'
import Frame from '../../assets/images/main/Frame.png'
import Frame2 from '../../assets/images/main/Frame2.png'
import panda from '../../assets/images/main/panda.png'
import 탄수화물 from '../../assets/images/main/탄수화물.png'
import 단백질 from '../../assets/images/main/단백질.png'
import 지방 from '../../assets/images/main/지방.png'


function Character() {
  const [mainUserData, setMainUserData] = useState(null)

  useEffect(() => {
      axios.get(`http://localhost:5000/mainuser`)
        .then(response=>{
            setMainUserData(response.data)
        })
        .catch(error => {
            console.error('랭킹 데이터를 불러오는 중 에러 발생:', error)
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
          <p>{mainUserData.ranking}등</p>
          {/* <img src={mainUserData.imgPath} alt="캐릭터 이미지" /> */}
          {/* <img src = {panda} alt=''/> */}


          <svg height={radius * 2} width={radius * 2} className={styles.progressbar2}>
          <circle
            className={styles.circle}
            stroke="#ffffff"
            strokeWidth="20"
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
            strokeWidth="20"
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
            <img src={탄수화물} alt='' />
            <img src={단백질} alt='' />
            <img src={지방} alt='' />
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
