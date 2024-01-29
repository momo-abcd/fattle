import React, { useRef, useState } from 'react';
import { patchMypage } from '../../services/mypage/api';
import { useLocation } from 'react-router';

const MypageModify = () => {
  const location = useLocation();

  // variable
  const [nickname, setNickname] = useState(location.state.nickname);
  const [introduction, setIntroduction] = useState(location.state.introduction);
  const nicknameInput = useRef(null);
  const introductionTextarea = useRef(null);

  const [status, setStatus] = useState('');

  // method
  const onPutMypage = async () => {
    const status = await patchMypage(location.state.userCode, {
      nickname,
      introduction,
    });
    console.log(status);
    if (status === 200) {
      setStatus('수정 성공');
    } else {
      setStatus('수정 실패 Status Code : ' + status);
    }
  };
  const onChangeHandler = (e) => {
    if (e.target === nicknameInput.current) {
      setNickname(e.target.value);
    } else {
      setIntroduction(e.target.value);
    }
  };

  return (
    <>
      <input
        type="text"
        ref={nicknameInput}
        value={nickname}
        placeholder="닉네임"
        onChange={(e) => {
          onChangeHandler(e);
        }}
      />
      <textarea
        type="text"
        ref={introductionTextarea}
        value={introduction}
        placeholder="자기 소개"
        onChange={(e) => {
          onChangeHandler(e);
        }}
      />
      <button onClick={onPutMypage}>수정하기</button>
      {status}
    </>
  );
};

export default MypageModify;
