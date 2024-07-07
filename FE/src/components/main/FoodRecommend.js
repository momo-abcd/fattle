import React, { useState } from 'react';
import axios from 'axios';
import recommendicon from '../../assets/images/main/recommendicon.svg';
import recommendtext from '../../assets/images/main/recommendtext.svg';
import styles from '../../styles/main/FoodRecommend.module.css';

function FoodRecommend() {
  const [modalVisible, setModalVisible] = useState(false);
  const [conversation, setConversation] = useState([]);
  const [userInput, setUserInput] = useState('');

  const openModal = () => {
    // 모달 열기 전에 대화 내용 초기화
    setConversation([]);
    setModalVisible(true);
  };

  const closeModal = () => {
    setModalVisible(false);
  };

  const handleUserInput = (e) => {
    setUserInput(e.target.value);
  };

  const handleModalClick = (e) => {
    // 모달 바깥쪽 클릭 시 모달 닫기
    if (e.target.classList.contains('modal-overlay')) {
      closeModal();
    }
  };

  const handleDietRecommendation = async () => {
    // 추천을 받기 전에 이전 대화 내용 초기화
    setConversation([]);

    try {
      // ChatGPT API 호출
      const response = await axios.post(
        'https://api.openai.com/v1/chat/completions',
        {
          model: 'gpt-3.5-turbo',
          messages: [
            {
              role: 'user',
              content: `건강식 5개 이름만`,
              // '건강하고 든든한 요리 아무거나 추천해는데 대답은 "오늘의 추천 음식은 {음식이름} 입니다. {}kcal 탄수화물{}g, 단백질{}g, 지방{}g" 이렇게 한 문장으로만 답해줘 ',
            },
            {
              role: 'system',
              content: conversation[conversation.length - 1]?.content || '',
            },
          ],
        },
        {
          // 헤더에 API 키 추가
          headers: {
            Authorization: process.env.REACT_APP_GPT_API_KEY,
          },
        },
      );

      // 응답에서 추천 식단을 가져와 채팅창에 추가
      const recommendedDietPlan = response.data.choices[0].message.content;
      setConversation([
        ...conversation,
        { role: 'system', content: recommendedDietPlan },
      ]);
    } catch (error) {
      console.error('식단 추천을 가져오는 동안 오류 발생:', error);
      alert(
        '식단 추천을 가져오는 동안 오류가 발생했습니다. 다시 시도해주세요.',
      );
    }
  };

  return (
    <div>
      <img
        src={recommendtext}
        alt="Food Recommendation"
        style={textImageStyle}
      />
      <img
        src={recommendicon}
        alt="Click to open modal"
        onClick={() => {
          openModal();
          handleDietRecommendation();
        }}
        style={{ cursor: 'pointer', ...iconImageStyle }}
      />

      {modalVisible && (
        <div className={styles.modaloverlay} onClick={handleModalClick}>
          <div className={styles.modalcontent}>
            <span onClick={closeModal} style={closeButtonStyle}>
              &times;
            </span>
            <div className={styles.today}>오늘의 음식 추천</div>
            <section className={styles.textareat}>
              {/* <div className="top-area">
                <div className="profile-area"></div>
              </div> */}

              {/* <button
                className="diet-recommendation-button"
                onClick={handleDietRecommendation}
                style={buttonStyle}
              >
                식단추천
              </button> */}

              <div className={styles.textarea}>
                {conversation.length > 0 && (
                  <div className={conversation[conversation.length - 1].role}>
                    {conversation[conversation.length - 1].content}
                  </div>
                )}
              </div>

              <div className="bottom-area">
                {/* <input
                  className="chat-input"
                  type="text"
                  value={userInput}
                  onChange={handleUserInput}
                  placeholder="type a message..."
                />
                <button
                  className="chat-submit-icon"
                  onClick={handleDietRecommendation} // 수정된 부분
                >
                  Send
                </button> */}
              </div>
            </section>
          </div>
        </div>
      )}
    </div>
  );
}

const textImageStyle = {
  marginTop: '500px',
};

const iconImageStyle = {
  marginTop: '450px',
};

const modalContentStyle = {
  backgroundColor: 'white',
  padding: '10px',
  borderRadius: '10px',
  boxShadow: '0 0 10px rgba(0, 0, 0, 0.5)',
  position: 'absolute',
  top: '50%',
  left: '0%',
  transform: 'translate(-50%, -50%)',
  width: '400px',
  height: '500px',
  zIndex: '1001',
};

const closeButtonStyle = {
  position: 'absolute',
  top: '10px',
  right: '10px',
  fontSize: '20px',
  cursor: 'pointer',
};

export default FoodRecommend;
