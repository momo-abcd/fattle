import axios from 'axios';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

const KakaoLogin = () => {
  // useEffect(() => {
  //   axios.get('http://localhost:8000/oauth/code/kakao').then((res) => {
  //     console.log(res.data);
  //   });
  // }, []);
  // return (
  //   <div>
  //     <div>하이루</div>
  //   </div>
  // );
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    console.log(code);
    axios
      .get(`http://localhost:8000/oauth/login/kakao?code=${code}`)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
};

export default KakaoLogin;
