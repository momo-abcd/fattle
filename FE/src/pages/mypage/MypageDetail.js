import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCalendarDetail } from '../../services/mypage/api';
import redirectAxiosError from '../../utils/commons/redirectAxiosError';

const MypageDetail = (props) => {
  const [data, setData] = useState(null);
  const navigate = useNavigate();
  useEffect(() => {
    (async () => {
      const { data, status } = await redirectAxiosError(
        async () => getCalendarDetail(1, 1),
        'main',
        navigate,
      );
      setData(data);
    })();
  }, []);
  return <>{data && <p>data.month</p>}</>;
};

export default MypageDetail;
