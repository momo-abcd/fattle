import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Ranking from './pages/ranking/Ranking';


function App() {
  return (
    <BrowserRouter>
      <>
        <div>처음</div>
        <Routes>
          <Route path="/ranking" element={<Ranking />} />
        </Routes>
      </>
    </BrowserRouter>
  );
}

export default App;

