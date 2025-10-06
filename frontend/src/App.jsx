import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import HomePage from "./pages/HomePage";
import AccountTypePage from "./pages/AccountTypePage";
import AccountPage from "./pages/AccountPage";

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/accounttypes" element={<AccountTypePage />} />
          <Route path="/accounts" element={<AccountPage />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;