import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AccountTypePage from "./pages/AccountTypePage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<div>Welcome to Finance App</div>} />
        <Route path="/accounttypes" element={<AccountTypePage />} />
      </Routes>
    </Router>
  );
}

export default App;