import { useState } from 'react';
import { API } from '../../services/login/URL';
import axios from 'axios';
// 닉네임 중복 체크 컴포넌트
const JoinPage1 = () => {
  const [joinInfo, setJoinInfo] = useState({
    nickname: '',
    age: '',
    sex: '',
    height: '',
    weigh: '',
    goalWeight: '',
    goalCalory: '',
    goalCarbon: '',
    goalProtein: '',
    goalFat: '',
  });
  const [dupli, setDupli] = useState(true);
  return (
    <div>
      <div className="nameCheck">
        <input
          placeholder="닉네임 입력"
          onChange={(e) => {
            setDupli(true);
            setJoinInfo({
              ...joinInfo,
              nickname: e.target.value,
            });
          }}
        ></input>
        <button
          onClick={() => {
            axios // NICKNAME_GET
              .get(`${API.NICKNAME_GET}${joinInfo.nickname}`)
              .then((res) => {
                alert('좋은 닉네임 입니다.');
                setDupli(false);
              })
              .catch((err) => {
                if (joinInfo.nickname.length === 0) {
                  alert('닉네임을 입력해 주세요!!!');
                } else {
                  alert('중복된 닉네임 입니다!!!');
                }
                console.log(err);
              });
          }}
        >
          중복 확인
        </button>
      </div>

      <div className="genderCheck">
        <label>
          <input
            type="radio"
            name="gender"
            onClick={() => {
              setJoinInfo({
                ...joinInfo,
                sex: 'M',
              });
            }}
          />
          남자
        </label>
        <label>
          <input
            type="radio"
            name="gender"
            onClick={() => {
              setJoinInfo({
                ...joinInfo,
                sex: 'W',
              });
            }}
          />
          여자
        </label>
      </div>

      <div className="myInfo">
        <input
          placeholder="세"
          onChange={(e) => {
            setJoinInfo({
              ...joinInfo,
              age: e.target.value,
            });
          }}
        ></input>
        <input
          placeholder="CM"
          onChange={(e) => {
            setJoinInfo({
              ...joinInfo,
              height: e.target.value,
            });
          }}
        ></input>
      </div>
      <div className="goal">
        <input
          placeholder="Kg"
          style={{ display: 'inline-block' }}
          onChange={(e) => {
            setJoinInfo({
              ...joinInfo,
              weigh: e.target.value,
            });
          }}
        ></input>
        <input
          placeholder="goalKg"
          style={{ display: 'inline-block' }}
          onChange={(e) => {
            setJoinInfo({
              ...joinInfo,
              goalWeight: e.target.value,
            });
          }}
        ></input>
      </div>

      <div className="next">
        <button
          disabled={dupli}
          onClick={() => {
            console.log('object변환');
            // console.log(joinInfo.nickname);
            // console.log(joinInfo.sex);
            // console.log(joinInfo.age);
            // console.log(joinInfo.height);
            // console.log(joinInfo.weigh);
            // console.log(joinInfo.goalWeight);
          }}
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default JoinPage1;
