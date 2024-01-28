import { Routes, Route, Navigate } from 'react-router-dom';
import Join from './pages/join/Join';
import Mypage from './pages/mypage/Mypage';
import Auth from './utils/Auth';
import MypageModify from './pages/mypage/MypageModify';

function App() {
  let isLogged = false;
  return (
    <Routes>
      <Route path="/mypage" element={<Mypage />} />
      <Route path="/mypageModify" element={<MypageModify />} />
      <Route path="/" element={<Auth isLogged={isLogged} />} />
      <Route path="/login" element={<Auth isLogged={isLogged} />} />
      <Route path="/join" element={<Join></Join>}></Route>
    </Routes>
  );
}
export default App;
