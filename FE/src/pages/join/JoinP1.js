import { useState } from 'react';
import axios from 'axios';
// 닉네임 중복 체크 컴포넌트
const Join = () => {
  const [nickName, setNickName] = useState('');
  const [dupli, setDupli] = useState(true);
  return (
    <div>
      <input
        placeholder="닉네임 입력"
        onChange={(e) => {
          setDupli(true);
          setNickName(e.target.value);
          console.log(nickName);
        }}
      ></input>
      <button
        onClick={() => {
          console.log(nickName);
          axios
            .get(`http://localhost:8000/user/nickname/${nickName}`)
            .then((res) => {
              console.log(res);
            })
            .catch((err) => {
              alert('중복된 닉네임 입니다.');
              console.log(err);
            });

          setDupli(false);
        }}
      >
        중복 확인
      </button>

      <div>
        <button disabled={false}>회원가입</button>
      </div>
    </div>
  );
};

export default Join;
