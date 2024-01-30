import './Login.css';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import KakaoLogin from './SosialLogin/Login-callback';
import { useEffect } from 'react';

const Login = () => {
  const Navigate = useNavigate();
  return (
    <div>
      <div className="wrapper">
        <p className="LoginText">로그인 화면입니다!!!</p>
        <div className="loginBtn">
          <a
            className="kakaoBtn"
            type="button"
            onClick={(e) => {
              e.preventDefault();
              window.location.href = 'http://localhost:8000/oauth/code/kakao';
            }}
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
