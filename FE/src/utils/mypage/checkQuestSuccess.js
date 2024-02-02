import { checkCurrentDay } from './makeCalendarList';

// 받아온 캘린더 리스트에서 날짜(day)만 리스트에 담아 반환해주는 함수
const getQuestSuccessList = (calendar) => {
  const list = calendar.map((item) => {
    // if (item.finish) {
    // 퀘스트 성공했다면 담아준다
    const day = item.recordDate.split('-')[2];
    if (day.length === 2) {
      if (day.slice(0, 1) === '0') {
        return day.slice(1, 2);
      } else {
        return day;
      }
    }
    return day;
    // }
  });
  return list;
};
const getQuestSuccessListDetail = (days) => {
  const list = days.map((item) => {
    if (item.finish) {
      // 퀘스트 성공했다면 담아준다
      const day = item.date.split('-')[2];
      if (day.length === 2) {
        if (day.slice(0, 1) === '0') {
          return day.slice(1, 2);
        } else {
          return day;
        }
      }
      return day;
    }
  });
  return list;
};
// 스트릭이 성공했는지, 이번달의 날짜가 맞는지 계산 해서 알맞은 class속성 값을 리턴해줌
const getClassName = (styles, calendar, day, index) => {
  const isCurrentDay = checkCurrentDay(index) === '' ? true : false; // '' or 'other'
  const successList = getQuestSuccessList(calendar);
  if (isCurrentDay) {
    if (successList.includes(day.toString())) {
      return `${styles['day']} ${styles['success']}`;
    } else {
      return styles['day'];
    }
  } else {
    return `${styles['day']} ${styles['other']}`;
  }
};
const getClassNameDetail = (styles, calendar, day, index) => {
  const isCurrentDay = checkCurrentDay(index) === '' ? true : false; // '' or 'other'
  const successList = getQuestSuccessListDetail(calendar);
  if (isCurrentDay) {
    if (successList.includes(day.toString())) {
      return `${styles['day']} ${styles['success']}`;
    } else {
      return styles['day'];
    }
  } else {
    return `${styles['day']} ${styles['other']}`;
  }
};

export { getClassName, getQuestSuccessList, getClassNameDetail };
