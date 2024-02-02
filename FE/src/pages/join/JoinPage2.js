import { useLocation } from 'react-router-dom';

const JoinPage2 = () => {
  const location = useLocation();
  const joinInfo = {
    nickname: location.state.nickname,
    age: location.state.age,
  };
  return (
    <div>
      <div>조인페이지2</div>
      {/* {console.log(location)}
      {console.log(joinInfo.nickname)}
      {console.log(joinInfo.age)} */}
    </div>
  );
};

export default JoinPage2;
