import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const JoinPage4 = () => {
  const location = useLocation();
  const [nutrient, setNutrient] = useState([
    {
      carbon: 241,
      protein: 145,
      fats: 43,
    },
    {
      carbon: 193,
      protein: 193,
      fats: 43,
    },
    {
      carbon: 39,
      protein: 106,
      fats: 150,
    },
    {
      carbon: 241,
      protein: 145,
      fats: 43,
    },
  ]);
  const joinInfo = {
    userCode: location.state.userCode,
    nickname: location.state.nickname,
    sex: location.state.sex,
    height: location.state.height,
    weight: location.state.weight,
    goalWeight: location.state.goalWeight,
    goalCalory: location.state.goalCalory,
    goalCarbon: location.state.goalCarbon,
    goalProtein: location.state.goalProtein,
    goalFat: location.state.goalFat,
    menu: location.state.menu,
  };

  const handleCarbonChange = (e) => {
    const updateNutrient = [...nutrient];
    updateNutrient[joinInfo.menu].carbon = parseInt(e.target.value, 10) || 0;
    setNutrient(updateNutrient);
    // setNutrient
  };
  const handleProteinChange = (e) => {
    const updateNutrient = [...nutrient];
    updateNutrient[joinInfo.menu].protein = parseInt(e.target.value, 10) || 0;
    setNutrient(updateNutrient);
    // setNutrient
  };
  const handlefatsChange = (e) => {
    const updateNutrient = [...nutrient];
    updateNutrient[joinInfo.menu].fats = parseInt(e.target.value, 10) || 0;
    setNutrient(updateNutrient);
    // setNutrient
  };
  return (
    <div>
      <div>
        <div>
          <div>순 탄수</div>
          <input
            placeholder="g"
            value={nutrient[joinInfo.menu].carbon}
            onChange={handleCarbonChange}
          ></input>
        </div>
        <div>
          <div>단백질</div>
          <input
            placeholder="g"
            value={nutrient[joinInfo.menu].protein}
            onChange={handleProteinChange}
          ></input>
        </div>
        <div>
          <div>지방</div>
          <input
            placeholder="g"
            value={nutrient[joinInfo.menu].fats}
            onChange={handlefatsChange}
          ></input>
        </div>
      </div>
      <div></div>
    </div>
  );
};

export default JoinPage4;
