import { Navigate } from 'react-router-dom';

import Main from './../pages/main/Main';
import Login from './../pages/login/Login';

const Auth = (props) => {
  if (props.isLogged) {
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
