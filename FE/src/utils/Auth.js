import { Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Main from './../pages/main/Main';
import Login from './../pages/login/Login';

const Auth = (props) => {
  const userCode = useSelector((state) => {
    return state.userCode;
  });
  if (userCode !== 0) {
    return (
      <div>
        <Main />
        <Navigate to={'/'} />
      </div>
    );
  } else {
    return (
      <div>
        <Login />
        <Navigate to={'/login'} />
      </div>
    );
  }
};

export default Auth;
