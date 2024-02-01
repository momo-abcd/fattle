import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { changeUserCode } from '../../../store/store';
import { API } from '../../../services/login/URL';
const KakaoLogin = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [user, setUser] = useState('');
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    console.log(code);
    axios // KAKAO_GET
      .get(`${API.KAKAO_GET}${code}`)
      .then((res) => {
        console.log(res.data.userCode);
        // console.log(res.data.userCode);
        setUser(res.data.userCode);
        console.log(user);
        axios // LOGIN_GET
          .get(`${API.LOGIN_CALLBACK_GET}${res.data.userCode}`)
          .then((res) => {
            console.log(res.status);
            if (res.status === 200) {
              console.log(user);
              dispatch(
                changeUserCode({
                  userCode: user,
                }),
              );
              navigate('/');
            } else if (res.status === 204) {
              navigate('/join/page1');
            }
            // res.status === 200 ? navigate('/join') : navigate('/');
          })
          .catch((err) => {
            console.log(err);
          });
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
};

export default KakaoLogin;
