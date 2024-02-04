import { useState } from 'react';

const JoinPage3 = () => {
  const [kcal, setKcal] = useState(1930);

  const handleKcalChange = (e) => {
    // 입력 값이 변경될 때마다 상태 업데이트
    setKcal(e.target.value);
  };

  return (
    <div>
      <div>목표 섭취 열량</div>
      <div>
        <input
          type="number"
          value={kcal}
          placeholder="kcal"
          onChange={handleKcalChange}
        />
      </div>
      <button
        onClick={() => {
          console.log(kcal);
        }}
      >
        다음
      </button>
    </div>
  );
};

export default JoinPage3;
