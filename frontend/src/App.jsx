import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import AccountTypePage from "./pages/AccountTypePage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/accounttypes" element={<AccountTypePage />} />
      </Routes>
    </Router>
  );
}

export default App;