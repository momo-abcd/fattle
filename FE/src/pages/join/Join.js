import { useState } from 'react';

const Join = () => {
<<<<<<< Updated upstream
  let [name, setName] = useState;
  return (
    <div>
      <h3>닉네임 입력</h3>
      <input type="text"></input>
      <button onClick={() => {}}>확인</button>
=======
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
>>>>>>> Stashed changes
    </div>
  );
};

export default Join;
