import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Routes, Route, Link} from 'react-router-dom'
import ExpHistory from './ExpHistory'

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


  return (
    <div>
      <h2>나의 상태</h2>
      {mainUserData && (
        <div>
          <p>랭킹: {mainUserData.ranking}</p>
          <img src={mainUserData.imgPath} alt="캐릭터 이미지" />
          <p>성장 경험치: {mainUserData.growthExp}</p>
          <p>스택 경험치: {mainUserData.stackExp}</p>
          <p>키: {mainUserData.height}cm</p>
          <p>몸무게: {mainUserData.weight}kg</p>
          <p>소모 칼로리: {mainUserData.calory}kcal</p>
          <p>목표 칼로리: {mainUserData.goalCalory}kcal</p>
          <Link to="/history">
            <button>경험치 히스토리 보기</button>
          </Link>
        </div>
      )}
        <Routes>
          <Route path="/history" element={<ExpHistory />} />
        </Routes>
    </div>
  );
}

export default Character
