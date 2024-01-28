import React, { useEffect, useState } from 'react';
import { getMypage } from '../../services/mypage/api';
import { Link } from 'react-router-dom';
function Mypage(props) {
  const [data, setData] = useState({});
  useEffect(() => {
    (async () => {
      setData(await getMypage(1));
    })();
  }, []);
  let calendarId = 0;
  return (
    <>
      <Link
        to="/mypageModify"
        state={{
          nickname: data.weight,
          introduction: data.introduction,
          userCode: data.id,
        }}
      >
        마이페이지 수정
      </Link>
      {Object.keys(data).length !== 0 && (
        <>
          <p>팔로워 : {data.followerCnt}</p>
          <p>팔로잉 : {data.followingCnt}</p>
          <p>소개 : {data.introduction}</p>
          <p>체중 : {data.weight}</p>
          <p>목표 체중 : {data.goalWeight}</p>
          <ul>
            {data.calendar.map((date) => (
              <li key={calendarId++}>
                날짜 : {date.date}
                <br />
                퀘스트 스트릭 : {date.quest === 1 ? '성공' : '실패'}
              </li>
            ))}
          </ul>
        </>
      )}
    </>
  );
}

export default Mypage;
