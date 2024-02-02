import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCalendarDetail } from '../../services/mypage/api';
import Calendar from '../../components/mypage/Calendar';
import dates from '../../utils/mypage/makeCalendarList';
import { getClassNameDetail } from '../../utils/mypage/checkQuestSuccess';
import calendarStyle from '../../styles/mypage/Calendar.module.css';
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
      {/* API 없어서 임시로 작성하는 코드 // 나중에 위로 옮겨야함*/}
      <Calendar>
        {dates.map((day, i) => {
          const cl = getClassNameDetail(calendarStyle, data.days, day, i);
          return (
            <li key={i} className={cl}>
              {day} <br />
            </li>
          );
        })}
      </Calendar>
    </>
  );
};

export default CalendarDetail;
