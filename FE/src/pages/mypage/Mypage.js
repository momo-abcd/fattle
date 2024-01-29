import React, { useEffect, useState } from 'react';
import { getMypage } from '../../services/mypage/api';
import { Link } from 'react-router-dom';
import styles from '../../styles/mypage/Mypage.module.css';

// SVG 관련
import ProfileImg from '../../assets/mypage/ProfileImg.svg';
import Alarm from '../../assets/mypage/Alarm.svg';
import Configure from '../../assets/mypage/Configure.svg';
import Edit from '../../assets/mypage/Edit.svg';
// Component 관련
import Calendar from '../../components/mypage/Calendar';
function Mypage(props) {
  const [data, setData] = useState({});
  useEffect(() => {
    (async () => {
      setData(await getMypage(1));
    })();
  }, []);
  return (
    <>
      {/* 헤더바 시작 */}
      <header className={styles.header}>
        <div className={styles.nickname}>
          {data.nickname}
          <img src={Edit} alt="Edit" />
        </div>
        <div className={styles.linkContainer}>
          <div className={styles.linkItem}>
            <img src={Configure} alt="configure" />
          </div>
        </div>
      </header>
      {/* 헤더바 끝 */}

      {/* 프로필 정보 시작 */}
      <div className={styles.profileContainer}>
        <div>
          <img src={ProfileImg} alt="프로필사진" />
        </div>
        <div className={styles.followContainer}>
          <div className={styles.fDiv}>
            <div>
              <img src={Alarm} alt="alarm" />
              새로운 알림
            </div>
            <div className={styles.fText}>250</div>
          </div>
          <div className={styles.fDiv}>
            <div>팔로워</div>
            <div className={styles.fText}>
              <Link to="/mypage/follow" state={{ who: 'follower' }}>
                {data.followerCnt}
              </Link>
            </div>
          </div>
          <div className={styles.fDiv}>
            <div>팔로잉</div>
            <div className={styles.fText}>
              <Link to="/mypage/follow" state={{ who: 'following' }}>
                {data.followingCnt}
              </Link>
            </div>
          </div>
        </div>
      </div>
      <div className={styles.introduction}>{data.introduction}</div>
      {/* 프로필 정보 끝 */}
      {/* 목표 부분 시작 */}
      <div className={styles.headerText}>나의 목표&#32;&#62;</div>
      <div className={styles.goalBox}>
        <div className={styles.goalContainer}>
          <div className={styles.gDiv}>
            <div>목표 체중</div>
            <div className={styles.gText}>45kg</div>
          </div>
          <div className={styles.gDiv}>
            <div>목표 체지방률</div>
            <div className={styles.gText}>23.0%</div>
          </div>
          <div className={styles.gDiv}>
            <div>목표 골격근량</div>
            <div className={styles.gText}>26Kg</div>
          </div>
        </div>
      </div>
      {/* 목표 부분 끝 */}

      {/* 캘린더 부분 시작 */}
      <div className={styles.headerText}>캘린더&#32;&#62;</div>
      {Object.keys(data).length !== 0 && <Calendar calendar={data.calendar} />}
      {/* 캘린더 부분 끝 */}
    </>
  );
}

export default Mypage;
