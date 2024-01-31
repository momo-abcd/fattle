import React from 'react';
import styles from '../../styles/mypage/Calendar.module.css';
import { getClassName } from '../../utils/mypage/checkQuestSuccess';
import dates from '../../utils/mypage/makeCalendarList';
// svg import
import CalendarSVG from '../../assets/svg/mypage/Calendar.svg';

const Calendar = ({ calendar }) => {
  return (
    <div className={styles.wrapper}>
      <header>
        <div className={styles.nav}>
          {/* <button className={styles['material-icons']}> chevron_left </button> */}
          <p className={styles['current-date']}>
            <img
              style={{ display: 'inline-block', marginRight: '5px' }}
              src={CalendarSVG}
              alt="calendarSVG"
            />
            2024. 01
          </p>
          {/* <button className={styles['material-icons']}> chevron_right</button> */}
        </div>
      </header>
      <div className={styles.calendar}>
        <ul className={styles.weeks}>
          <li className={styles.date}>일</li>
          <li className={styles.date}>월</li>
          <li className={styles.date}>화</li>
          <li className={styles.date}>수</li>
          <li className={styles.date}>목</li>
          <li className={styles.date}>금</li>
          <li className={styles.date}>토</li>
        </ul>
        <ul className={styles.days}>
          {dates.map((day, i) => {
            const cl = getClassName(styles, calendar, day, i);
            return (
              <li key={i} className={cl}>
                {day} <br />
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
};

export default Calendar;
