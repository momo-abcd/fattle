import React from 'react';
import { BrowserRouter, Routes, Route} from 'react-router-dom'
import Ranking from './pages/ranking/Ranking'
import Character from './components/main/Character'
import ExpHistory from './components/main/ExpHistory';



function App() {
  return (
    <BrowserRouter>
        <Routes>
          <Route path="/" element={<Character />} />
          <Route path="/history" element={<ExpHistory />} />
          <Route path="/ranking" element={<Ranking />} />
        </Routes>
    </BrowserRouter>
  )
}

export default App;


