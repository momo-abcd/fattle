import axios from 'axios'
import React, { useEffect, useState } from 'react'

function Ranking() {
  const [top10, setTop10] = useState([])
  const [myRank, setMyRank] = useState('')
  const [myRankUser, setMyRankUser] = useState(null)

  useEffect(() => {
    
    axios.get(`http://127.0.0.1:8000/rank/list/usercode`)
      .then(response => {
        const { top10, myRank } = response.data

        setTop10(top10)
        setMyRank(myRank)

        const myRankIndex = findMyRankIndex()
        if (myRankIndex !== -1) {
          setMyRankUser(top10[myRankIndex])
        }
      })
      .catch(error => {
        console.error('데이터를 불러오는 중 에러 발생:', error)
      })
  }, [])

  const findMyRankIndex = () => {
    return top10.findIndex(user => user.index + 1 === myRank)
  }

  return (
    <div>
      <h1>랭킹</h1>
      <div>
        1등<img src={top10[0].imgPath} alt={top10[0].nickname} />{top10[0].nickname}
        2등<img src={top10[1].imgPath} alt={top10[1].nickname} />{top10[1].nickname}
        3등<img src={top10[2].imgPath} alt={top10[2].nickname} />{top10[2].nickname}
      </div>

      <ul>
        {top10.map((user, index) => (
          <li key={index}>
            <p>{index + 1}등</p>
            <img src={user.imgPath} alt=''/>
            <p>{user.nickname}</p>
            <p>성장Exp: {user.growthExp}</p>
            <p>스택Exp: {user.stackExp}</p>
          </li>
        ))}
      </ul>
      <p>내 랭크: {myRank}</p>
        <div>
          <img src={myRankUser.imgPath} alt={myRankUser.nickname} />
          <p>{myRankUser.nickname}</p>
          <p>성장Exp: {myRankUser.growthExp}</p>
          <p>스택Exp: {myRankUser.stackExp}</p>
        </div>
    </div>
  );
}

export default Ranking
 

