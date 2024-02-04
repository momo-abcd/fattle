import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const JoinPage4 = () => {
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
    menu: location.state.menu,
  };
  return (
    <div>
      <div>조인페이지4 입니다.</div>
    </div>
  );
};

export default JoinPage4;
