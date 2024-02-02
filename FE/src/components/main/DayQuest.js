import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "../../styles/main/DayQuest.module.css";
import { API } from '../../services/main/URL';

function DayQuest() {
  const [questComplete, setQuestComplete] = useState(false);
  const [exercise, setExercise] = useState();
  const [selectedExercises, setSelectedExercises] = useState([]);
  const [visibleCount, setVisibleCount] = useState(3);
  const [showCloseButton, setShowCloseButton] = useState(false);
  const [attendanceChecked, setAttendanceChecked] = useState(false);
  const [foodquestChecked, setFoodquestChecked] = useState({
    morning: false,
    lunch: false,
    dinner: false,
  });

  useEffect(() => {
    axios.get(`http://localhost:5000/quest`)
      .then(response => {
        const exerciseData = response.data.exercisequest;
        setExercise(exerciseData);
        setQuestComplete(true);
      })
      .catch(error => {
        console.error('랭킹 데이터를 불러오는 중 에러 발생:', error);
      });
  }, []);

  useEffect(() => {
    setAttendanceChecked(exercise && exercise.attendance === 1);

    setFoodquestChecked((prevFoodquestChecked) => ({
      ...prevFoodquestChecked,
      morning: exercise && exercise.foodquest && exercise.foodquest.morning === 1,
      lunch: exercise && exercise.foodquest && exercise.foodquest.lunch === 1,
      dinner: exercise && exercise.foodquest && exercise.foodquest.dinner === 1,
    }));
  }, [exercise]);

  const handleAttendanceClick = () => {
    setAttendanceChecked((prev) => !prev);
  };

  const handleFoodquestClick = (key) => {
    setFoodquestChecked((prev) => ({
      ...prev,
      [key]: !prev[key],
    }));
  };

  const handleExerciseClick = (key) => {
    setSelectedExercises((prevSelectedExercises) => {
      if (prevSelectedExercises.includes(key)) {
        return prevSelectedExercises.filter(item => item !== key);
      } else {
        return [...prevSelectedExercises, key];
      }
    });
  };

  const handleShowMore = () => {
    setVisibleCount((prevVisibleCount) => prevVisibleCount + 1);
    setShowCloseButton(true);
  };

  useEffect(() => {
    if (selectedExercises.length === 7) {
      axios.post(`${API.QUEST_COMPLETE}`, { completed: true })
        .then(response => {
          console.log('일일 퀘스트 완료:', response.data);
        })
        .catch(error => {
          console.error('일일 퀘스트 완료 전송 중 에러 발생:', error);
        });
    }
  }, [selectedExercises]);

  return (
    <div className={styles.dayQuestContainer}>
      <h3 className={styles.questTitle}>일일퀘스트</h3>
  
      <div className={styles.exerciseListContainer}>
        <ul className={styles.exerciseList}>
          {exercise && typeof exercise === 'object' && Object.keys(exercise).map((key, index) => (
            <li
              key={key}
              onClick={() => handleExerciseClick(key)}
              className={`${styles.exerciseItem} ${selectedExercises.includes(key) ? styles.exerciseItemSelected : styles.exerciseItemUnselected}`}
              style={{ display: index < visibleCount ? "block" : "none" }}
            >
              <span style={{ alignSelf: "center" }}>{key}</span>
            </li>
          ))}

          {exercise && (
            <>
              <li
                onClick={handleAttendanceClick}
                className={`${styles.exerciseItem} ${attendanceChecked ? styles.exerciseItemSelected : styles.exerciseItemUnselected}`}
              >
                <span style={{ alignSelf: "center" }}>출석</span>
              </li>

              {exercise.foodquest && (
                Object.entries(exercise.foodquest).map(([key]) => (
                  <li
                    key={key}
                    onClick={() => handleFoodquestClick(key)}
                    className={`${styles.exerciseItem} ${foodquestChecked[key] ? styles.exerciseItemSelected : styles.exerciseItemUnselected}`}
                  >
                    <span style={{ alignSelf: "center" }}>{key}</span>
                  </li>
                ))
              )}
            </>
          )}
        </ul>
  
        {visibleCount < Object.keys(exercise || {}).length && (
          <>
            <button onClick={handleShowMore} className={styles.showMoreButton}>
              더 보기
            </button>
          </>
        )}
      </div>
    </div>
  );
}

export default DayQuest;










