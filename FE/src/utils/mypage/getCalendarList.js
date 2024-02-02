// 매개변수에 전달된 달의 캘린더 요일리스트를 반환하는 함수
/**
 *
 * @param {number} month - 반환할 캘린더의 달
 * @param {number} [year=new Date().getFullYear()] - 입력하지 않으면 자동적으로 현재 년도를 넣어줌
 * @returns 캘린더 요일과 이전달,현재달,다음달에 대한 상태를 나타내는 boolean 리스트 반환
 */
const getCalendarList = (month, year = new Date().getFullYear()) => {
  const date = new Date(year + '-' + month);
  const viewYear = date.getFullYear();
  const viewMonth = date.getMonth();

  const prevLast = new Date(viewYear, viewMonth, 0);
  const thisLast = new Date(viewYear, viewMonth + 1, 0);

  const PLDate = prevLast.getDate();
  const PLDay = prevLast.getDay();

  const TLDate = thisLast.getDate();
  const TLDay = thisLast.getDay();

  const prevDates = [];
  const thisDates = [...Array(TLDate + 1).keys()].slice(1);
  const nextDates = [];

  if (PLDay !== 6) {
    for (let i = 0; i < PLDay + 1; i++) {
      prevDates.unshift(PLDate - i);
    }
  }

  for (let i = 1; i < 7 - TLDay; i++) {
    nextDates.push(i);
  }

  const dates = prevDates.concat(thisDates, nextDates);
  const firstDateIndex = dates.indexOf(1);
  const lastDateIndex = dates.lastIndexOf(TLDate);

  // 캘린더의 요일이 현재 월의 요일인지 아닌지 체크해서 반환
  const isDayInCurrentMonth = [];
  for (let index = 0; index < dates.length; index++) {
    if (index >= firstDateIndex && index < lastDateIndex) {
      isDayInCurrentMonth[index] = true;
    } else {
      isDayInCurrentMonth[index] = false;
    }
  }

  return { dates, isDayInCurrentMonth };
};
export default getCalendarList;
