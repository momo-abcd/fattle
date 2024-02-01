import { useState } from 'react';
import { API } from '../../services/login/URL';
import axios from 'axios';
// 닉네임 중복 체크 컴포넌트
const JoinPage1 = () => {
  const [nickName, setNickName] = useState('');
  const [gender, setGender] = useState('');
  const [age, setAge] = useState('');
  const [height, setHeight] = useState('');
  const [dupli, setDupli] = useState(true);
  return (
    <div>
      <div className="nameCheck">
        <input
          placeholder="닉네임 입력"
          onChange={(e) => {
            setDupli(true);
            setNickName(e.target.value);
          }}
        ></input>
        <button
          onClick={() => {
            axios // NICKNAME_GET
              .get(`${API.NICKNAME_GET}${nickName}`)
              .then((res) => {
                setDupli(false);
              })
              .catch((err) => {
                if (nickName.length === 0) {
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
              setGender('M');
            }}
          />
          남자
        </label>
        <label>
          <input
            type="radio"
            name="gender"
            onClick={() => {
              setGender('W');
            }}
          />
          여자
        </label>
      </div>

      <div className="myInfo">
        <input
          placeholder="세"
          onChange={(e) => {
            setAge(e.target.value);
          }}
        ></input>
        <input
          placeholder="CM"
          onChange={(e) => {
            setHeight(e.target.value);
          }}
        ></input>
      </div>

      <div className="next">
        <button
          disabled={dupli}
          onClick={() => {
            console.log(nickName);
            console.log(gender);
            console.log(age);
            console.log(height);
          }}
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default JoinPage1;
