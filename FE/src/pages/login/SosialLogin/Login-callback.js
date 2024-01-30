import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

const KakaoLogin = () => {
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
  }, []);
};

export default KakaoLogin;
