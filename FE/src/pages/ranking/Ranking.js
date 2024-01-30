import axios from 'axios';
import React, { useEffect, useState } from 'react'
import styles from '../../styles/ranking/ranking.module.css'
import Footer from '../../commons/Footer'
import rankingbg from '../../assets/images/ranking/rankingbg.png'
import rankingbar from '../../assets/images/ranking/rankingbar.png'


function Ranking() {
  const [rank, setRank] = useState([])
  const [myRank, setMyRank] = useState()

  const [visibleCount, setVisibleCount] = useState(5)

  const handleShowMore = () => {
    setVisibleCount((prevCount) => prevCount + 5)
  };

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
    <div className={styles.wrapper}>
      
      {rank.length > 0 && (
        <div className={styles.rankingcontainer} style={{ backgroundImage: `url(${rankingbg})` }}>
        <img src={rankingbar} alt='' className={styles.rankingbar} />
        <div className={styles.rankimagescontainer}>
          <div className={styles.rankimagesrow}>
            <p className={styles.rankimagescolumn}>
              <img src={rank[1].imgPath} alt='' className={styles.rankimages} />
            </p>
            <p className={styles.rankimagescolumn}>
              <img src={rank[0].imgPath} alt='' className={styles.rankimages} />
            </p>
            <p>
              <img src={rank[2].imgPath} alt='' className={styles.rankimages} />
            </p>
            <p className={styles.ranknicknames}>
              {rank[1].nickname} {'        '}
              {rank[0].nickname} {'        '}
              {rank[2].nickname}
            </p>
          </div>
        </div>
      </div>
      
      )}
  
      <ul className={styles.ranklistcontainer}>
        {rank.slice(0, visibleCount).map((item, index) => (
          <li key={index} className={styles.ranklistitem}>
            <span className={styles.rankitemcontent}>
              <span className={styles.rankposition}>{index + 1}위</span>
              <img src={item.imgPath} alt='' className={styles.rankimage} />
              <span>{item.nickname} {item.growthExp + item.stackExp}</span>
            </span>
          </li>
        ))}
      </ul>
      {visibleCount < rank.length && (
        <button onClick={handleShowMore} className={styles.showMoreButton}>
          더 보기
        </button>
      )}
  
      <p>
        {myRank && (
          <div className={styles.myrankcontainer}>
            내 랭킹: {myRank.rank}위
            <img src={myRank.imgPath} alt='' />
            {myRank.nickname} {myRank.growthExp + myRank.stackExp}
          </div>
        )}
      </p>

      <Footer/>
    </div>
  );
}  

export default Ranking
