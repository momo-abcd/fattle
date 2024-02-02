import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const JoinPage2 = () => {
  const navigate = useNavigate();
  const location = useLocation();
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
  };
  return (
    <div>
      <div>조인페이지2</div>
      <div className="mealCheck">
        <label>
          <input type="radio" name="meal" />
          일반 식단
        </label>
        <label>
          <input type="radio" name="meal" />
          운동 식단
        </label>
        <label>
          <input type="radio" name="meal" />
          키토 식단
        </label>
        <label>
          <input type="radio" name="meal" />
          비건 식단
        </label>
      </div>

      <div className="next">
        <button
          onClick={() => {
            navigate('/join/page3', {
              state: {
                userCode: joinInfo.userCode,
                nickname: joinInfo.nickname,
                sex: joinInfo.sex,
                height: joinInfo.height,
                weight: joinInfo.weight,
                goalWeight: joinInfo.goalWeight,
                goalCalory: joinInfo.goalCalory,
                goalCarbon: joinInfo.goalCarbon,
                goalProtein: joinInfo.goalProtein,
                goalFat: joinInfo.goalFat,
              },
            });
          }}
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default JoinPage2;
