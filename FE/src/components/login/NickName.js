import { useState } from 'react';

const NickName = () => {
  const [name, setName] = useState('');
  const [dupli, setDupli] = useState(true);
  return (
    <div>
      <input
        placeholder="닉네임 입력"
        onChange={(e) => {
          setDupli(true);
          setName(e.target.value);
        }}
      ></input>
      <button
        onClick={() => {
          setDupli(false);
        }}
      >
        중복 확인
      </button>
    </div>
  );
};

export default NickName;
