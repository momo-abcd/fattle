import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Join = () => {
  const navigate = useNavigate();
  const [state, setState] = useState({
    id: '111',
    pw: '152',
  });
  const handleState = (data) => {
    let copy = { ...state };
    // console.log(copy);
    copy.id = data;
    setState(copy);
    console.log(state);
  };
  return (
    <div>
      <div>Join 메인</div>
      <button
        onClick={() => {
          navigate('/join/page1');
        }}
      >
        다음페이지
      </button>
    </div>
  );
};
export default Join;
