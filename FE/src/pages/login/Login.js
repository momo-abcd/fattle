import './Login.css';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const Navigate = useNavigate();
  return (
    <div>
      <div className="wrapper">
        <p className="LoginText">로그인 화면입니다!!!</p>
        <div className="loginBtn">
          <button
            className="kakaoBtn"
            //type="button"
            onClick={() => {}}
          >
            네이버 버튼
          </button>
          <button
            className="naverBtn"
            //type="button"
            onClick={() => {}}
          >
            카카오 버튼
          </button>
        </div>
        <div className="join">
          <button
            className="joinBtn"
            onClick={() => {
              Navigate('/join');
            }}
          >
            회원가입
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
