import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { changeUserCode } from '../../../store/store';
const KakaoLogin = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [user, setUser] = useState('');
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    console.log(code);
    axios
      .get(`http://localhost:8000/oauth/login/kakao?code=${code}`)
      .then((res) => {
        console.log(res.data.userCode);
        // console.log(res.data.userCode);
        setUser(res.data.userCode);
        console.log(user);
        axios
          .get(`http://localhost:8000/user/login/${res.data.userCode}`)
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
              navigate('/join1');
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
