import { useState } from 'react';

const Join = () => {
  const [name, setName] = useState('');
  const [dupli] = useState(false);
  return (
    <div>
      <h3>닉네임 입력</h3>
      <input
        onChange={(e) => {
          setName(e.target.value);
        }}
      ></input>
      <button onClick={() => {}}>확인</button>

      <div>
        <button disabled={dupli}>회원가입</button>
      </div>
    </div>
  );
};

export default Join;
