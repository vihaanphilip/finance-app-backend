import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import HomePage from "./pages/HomePage";
import AccountTypePage from "./pages/AccountTypePage";
import AccountPage from "./pages/AccountPage";
import EarningTypePage from "./pages/EarningTypePage";
import EarningCategoryPage from "./pages/EarningCategoryPage"; // ✅ Added import

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/accounttypes" element={<AccountTypePage />} />
          <Route path="/accounts" element={<AccountPage />} />
          <Route path="/earningtypes" element={<EarningTypePage />} />
          <Route
            path="/earningcategories"
            element={<EarningCategoryPage />}
          />{" "}
          {/* ✅ Added route */}
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
