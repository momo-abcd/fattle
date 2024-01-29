import axios from 'axios';
import React, { useEffect, useState } from 'react'

function Ranking() {
  const [rank, setRank] = useState([])
  const [myRank, setMyRank] = useState()

  useEffect(() => {
    axios.get(`http://localhost:5000/rank`)
      .then(response => {
        const top10 = response.data.top10;
        const myRankIndex = response.data.myRank - 1
        const myRankInfo = top10[myRankIndex];
        myRankInfo.rank = response.data.myRank
        setRank(top10)
        setMyRank(myRankInfo)
      })
      .catch(error => {
        console.error('랭킹 데이터를 불러오는 중 에러 발생:', error)
      })
  }, [])

  return (
    <div>
      <h2>랭킹</h2>
      {rank.length > 0 && (
        <>
          <div>
            <p>
              1위
              <img src={rank[0].imgPath} alt='' />
              {rank[0].nickname}
            </p>
          </div>

          <div>
            <p>
              2위
              <img src={rank[1].imgPath} alt='' />
              {rank[1].nickname}
            </p>
          </div>

          <div>
            <p>
              3위
              <img src={rank[2].imgPath} alt='' />
              {rank[2].nickname}
            </p>
          </div>
        </>
      )}

      <ul style={{ maxHeight: '100px', overflowY: 'auto' }}>
        {rank.map((item, index) => (
          <li key={index}>
            {index + 1}위
            <img src={item.imgPath} alt='' />
            {item.nickname} {item.growthExp + item.stackExp}
          </li>
        ))}
      </ul>

      <p>
        {myRank && (
          <>
            내 랭킹 : {myRank.rank}위
            <img src={myRank.imgPath} alt='' />
            {myRank.nickname} {myRank.growthExp + myRank.stackExp}
          </>
        )}
      </p>
    </div>
  );
}

export default Ranking
 

