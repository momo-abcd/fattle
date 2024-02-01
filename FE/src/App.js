import { Routes, Route, Navigate } from 'react-router-dom';
import Join from './pages/join/Join.js';
import JoinPage1 from './pages/join/JoinPage1';
import Mypage from './pages/mypage/Mypage';
import Auth from './utils/Auth';
import MypageModify from './pages/mypage/MypageModify';
import FollowList from './pages/mypage/FollowList';
import Goal from './pages/mypage/Goal';
import Ranking from './pages/ranking/Ranking';
import ExpHistory from './components/main/ExpHistory';
import Main from './pages/main/Main';

//styles
import styles from './styles/App.module.css';
import Callback from './pages/login/SosialLogin/Login-callback';

function App() {
  let isLogged = false;
  return (
    <div className={styles.wrapper}>
      <Routes>
        <Route path="/mypage" element={<Mypage />}></Route>
        <Route path="/mypage/follow" element={<FollowList />} />
        <Route path="/mypageModify" element={<MypageModify />} />
        <Route path="/mypage/goal" element={<Goal />} />
        <Route path="/" element={<Auth isLogged={isLogged} />} />
        <Route path="/login" element={<Auth isLogged={isLogged} />} />
        <Route path="/join" element={<Join></Join>}></Route>
        <Route path="/join/page1" element={<JoinPage1></JoinPage1>}></Route>
        <Route path="/login-callback" element={<Callback />}></Route>
        <Route path="/main" element={<Main />} />
        <Route path="/history" element={<ExpHistory />} />
        <Route path="/ranking" element={<Ranking />} />
      </Routes>
    </div>
  );
}
export default App;
