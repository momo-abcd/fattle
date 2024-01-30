import React, { useRef } from 'react';
import { putMypage } from '../../services/mypage/api';

const MypageModify = ({ nickname, introduction, userCode }) => {
  const nicknameEle = useRef();
  const introductionEle = useRef();
  const onPutMypage = () => {
    console.log(nicknameEle.current);
    const status = putMypage(userCode, {
      nickname: nicknameEle.current,
      introduction: introduction,
    });
    if (status === 200) console.log('성공적으로 수정됨');
  };

  return (
    <>
      <input
        type="text"
        ref={nicknameEle}
        value={nickname}
        placeholder="닉네임"
      />
      <textarea
        type="text"
        ref={introductionEle}
        value={introduction}
        placeholder="자기 소개"
      />
      <button onClick={onPutMypage}></button>
    </>
  );
};

export default MypageModify;
