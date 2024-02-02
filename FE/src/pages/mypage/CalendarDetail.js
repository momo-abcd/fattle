import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Calendar from '../../components/mypage/Calendar';
import { getCalendarDetail } from '../../services/mypage/api';
const CalendarDetail = (props) => {
  const [data, setData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const { data, status } = await getCalendarDetail(1, 1);
        setData(data);
      } catch (error) {
        // navigate('/');
      }
    })();
  }, []);
  return (
    <>
      {data && (
        <p>
          <Calendar>
            <>HI</>
          </Calendar>
        </p>
      )}
    </>
  );
};

export default CalendarDetail;
