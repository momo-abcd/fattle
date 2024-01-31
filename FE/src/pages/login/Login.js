import './Login.css';
// import KakaoBtn from '../../assets/login/kakao_btn';
import { useDispatch, useSelector } from 'react-redux';
import kakao_btn from '../../assets/login/kakao_btn.png';
import naver_btn from '../../assets/login/naver_btn.png';
const Login = () => {
  return (
    <div>
      <div className="background">
        <p className="LoginText">로그인 화면입니다!!!</p>
        <div className="wrapper">
          <div className="title">
            <div>다이어트의 시작</div>
            <div>Fattle</div>
          </div>

          <div className="kakao">
            <a
              className="kakaoBtn"
              type="button"
              onClick={(e) => {
                e.preventDefault();
                // 주소로 이동 후 Login-callback.js에 데이터 전달
                window.location.href = 'http://localhost:8000/oauth/code/kakao';
              }}
            >
              <img className="kakao" src={kakao_btn} alt="Kakao Button" />
            </a>
          </div>
          <div className="naver">
            <a className="naverBtn" type="button">
              <img className="naver" src={naver_btn} alt="Kakao Button" />
            </a>
          </div>
          <div className="otherBtn">
            <button className="faceBook">페북</button>
            <button className="apple">apple</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
