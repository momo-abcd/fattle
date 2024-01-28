import React, { useEffect, useState } from 'react';
import { getFollowerList, getFollowingList } from '../../services/mypage/api';

const FollowList = ({ who }) => {
  let key = 0;
  const [data, setData] = useState(null);
  useEffect(() => {
    // who에 따라 팔로잉을 불러올지 팔로워를 불러올data지 분기해줌
    if (who === 'follower') {
      (async () => {
        setData(await getFollowerList(1));
      })();
    } else {
      (async () => {
        console.log(await getFollowerList(1));
        setData(await getFollowingList(1));
      })();
    }
  }, []);
  return (
    <>
      {data &&
        data.map((user) => (
          <div key={key++}>
            imgPath : {user.imgPath}
            userCode : {user.userCode}
            nickname : {user.nickname}
            <hr />
          </div>
        ))}
    </>
  );
};

export default FollowList;
