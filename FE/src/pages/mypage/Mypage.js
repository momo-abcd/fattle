import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import asyncGetMypage from '../../services/mypage/asyncGetMypage';
function Mypage(props) {
  const { mypageGetStatus, mypageGetData } = useSelector(
    (state) => state.mypage,
  );
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(asyncGetMypage());
  }, []);
  let calendarId = 0;
  const {
    followerCnt,
    followingCnt,
    introduction,
    weight,
    goalWeight,
    calendar,
  } = mypageGetData;
  return (
    <>
      {mypageGetStatus === '받아옴' ? (
        <>
          <p>현재 상태 : {mypageGetStatus}</p>
          <p>팔로워 : {followerCnt}</p>
          <p>팔로잉 : {followingCnt}</p>
          <p>소개 : {introduction}</p>
          <p>체중 : {weight}</p>
          <p>목표 체중 : {goalWeight}</p>
          <ul>
            {calendar.map((date) => (
              <li key={calendarId++}>
                날짜 : {date.date}
                <br />
                퀘스트 스트릭 : {date.quest === 1 ? '성공' : '실패'}
              </li>
            ))}
          </ul>
        </>
      ) : (
        ''
      )}
    </>
  );
}

export default Mypage;
