// import React, { useState, useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import Intro from "./pages/Intro";

function App() {
  // let navigate = useNavigate();

  return (
    <Routes>
      <Route path="/" element={<Intro />} />
      <Route path="/login" element={<Intro />} />
      <Route path="/signup" element={<Intro />} />
      <Route path="/home" element={<Intro />} />
    </Routes>
  );
}

export default App;
