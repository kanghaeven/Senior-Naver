// import React, { useState, useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import Intro from "./pages/Intro";
import Places from "./pages/Places";
import Login from "./pages/Login";
function App() {
  // let navigate = useNavigate();

  return (
    <Routes>
      <Route path="/" element={<Intro />} />
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Intro />} />
      <Route path="/home" element={<Intro />} />
      <Route path="/places" element={<Places />} />
    </Routes>
  );
}

export default App;
