import React from 'react';
import { BrowserRouter, Routes, Route} from 'react-router-dom'
import Ranking from './pages/ranking/Ranking'
import ExpHistory from './components/main/ExpHistory';
import Main from './pages/main/Main';



function App() {
  return (
    <BrowserRouter>
        <Routes>
          <Route path="/main" element={<Main />} />
          <Route path="/history" element={<ExpHistory />} />
          <Route path="/ranking" element={<Ranking />} />
        </Routes>
    </BrowserRouter>
  )
}

export default App;


