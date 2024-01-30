import './Login.css';
import { useNavigate } from 'react-router-dom';
import KakaoLogin from './SosialLogin/Login-callback';

const Login = () => {
  const Navigate = useNavigate();
  return (
    <div>
      <div className="wrapper">
        <p className="LoginText">로그인 화면입니다!!!</p>
        <div className="loginBtn">
          <a
            className="kakaoBtn"
            //type="button"
            href="https://kauth.kakao.com/oauth/authorize?response_type=code&redirect_uri=http://localhost:3000/login-callback&client_id=40a6ee1bb9a630be39ffadabd71c51bd"
          >
            <img
              className="kakao"
              src="FE/src/assets/login/kakao_btn.png"
            ></img>
          </a>
          <a className="naverBtn">
            <img
              className="naver"
              src="FE/src/assets/login/naver_btn.png"
            ></img>
          </a>
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
