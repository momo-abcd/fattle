<<<<<<< Updated upstream
import { Routes, Route, Navigate } from 'react-router-dom';
import Main from './pages/main/Main';
import Login from './pages/login/Login';
import Join from './pages/join/Join';

function App() {
  let isLogged = false;
=======
import { Routes, Route } from 'react-router-dom';

import Join from './pages/join/Join';
import Auth from './utils/Auth';

function App() {
  let isLogged = true;
>>>>>>> Stashed changes
  return (
    <Routes>
      <Route path="/" element={<Auth isLogged={isLogged} />} />
      <Route path="/login" element={<Auth isLogged={isLogged} />} />
      <Route path="/join" element={<Join></Join>}></Route>
    </Routes>
  );
}
export default App;

function Auth(props) {
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
}
