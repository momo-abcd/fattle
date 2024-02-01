import React, { useEffect, useState } from 'react';
import { getMypage } from '../../services/mypage/api';
import { Link, useNavigate } from 'react-router-dom';
import styles from '../../styles/mypage/Mypage.module.css';
// SVG 관련
import ProfileImg from '../../assets/svg/mypage/ProfileImg.svg';
import Alarm from '../../assets/svg/mypage/Alarm.svg';
import Configure from '../../assets/svg/mypage/Configure.svg';
import Edit from '../../assets/svg/mypage/Edit.svg';
// Component 관련
import Calendar from '../../components/mypage/Calendar';
import Footer from '../../commons/Footer';
function Mypage(props) {
  const navigate = useNavigate();
  const [data, setData] = useState({});
  useEffect(() => {
    (async () => {
      try {
        setData(await getMypage(1));
      } catch (error) {
        console.log(error);
        navigate('/');
      }
    })();
  }, []);
  return (
    <>
      {/* 헤더바 시작 */}
      <header className={styles.header}>
        <div className={styles.nickname}>
          {data.nickname}
          <Link
            to="/mypageModify"
            state={{
              nickname: data.nickname,
              introduction: data.introduction,
              profileImg: ProfileImg,
            }}
          >
            <img src={Edit} alt="Edit" />
          </Link>
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
              <Link
                to="/mypage/follow"
                state={{
                  who: 'follower',
                  followerCnt: data.followerCnt,
                  followingCnt: data.followingCnt,
                }}
              >
                {data.followerCnt}
              </Link>
            </div>
          </div>
          <div className={styles.fDiv}>
            <div>팔로잉</div>
            <div className={styles.fText}>
              <Link
                to="/mypage/follow"
                state={{
                  who: 'following',
                  followerCnt: data.followerCnt,
                  followingCnt: data.followingCnt,
                }}
              >
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
            <div className={styles.gText}>{data.goalWeight}kg</div>
          </div>
          <div className={styles.gDiv}>
            <div>칼로리</div>
            <div className={styles.gText}>{data.goalCalory}</div>
          </div>
          <div className={styles.gDiv}>
            <div>탄수화물</div>
            <div className={styles.gText}>{data.goalCarbo}</div>
          </div>
          <div className={styles.gDiv}>
            <div>단백질</div>
            <div className={styles.gText}>{data.goalProtein}</div>
          </div>
          <div className={styles.gDiv}>
            <div>지방</div>
            <div className={styles.gText}>{data.goalFat}</div>
          </div>
        </div>
      </div>
      {/* 목표 부분 끝 */}

      {/* 캘린더 부분 시작 */}
      <div className={styles.headerText}>캘린더&#32;&#62;</div>
      {Object.keys(data).length !== 0 && <Calendar calendar={data.calendar} />}
      {/* 캘린더 부분 끝 */}
      <Footer />
    </>
  );
}

export default Mypage;
