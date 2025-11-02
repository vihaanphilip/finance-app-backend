import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Layout from "./components/Layout";
import HomePage from "./pages/HomePage";
import AccountTypePage from "./pages/AccountTypePage";
import AccountPage from "./pages/AccountPage";
import EarningTypePage from "./pages/EarningTypePage";
import EarningCategoryPage from "./pages/EarningCategoryPage"; // ✅ Added import
import EarningPage from "./pages/EarningPage";

function App() {
  return (
    <Router>
      <Layout>
        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          theme="light"
        />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/accounttypes" element={<AccountTypePage />} />
          <Route path="/accounts" element={<AccountPage />} />
          <Route path="/earningtypes" element={<EarningTypePage />} />
          <Route path="/earningcategories" element={<EarningCategoryPage />} />
          <Route path="/earnings" element={<EarningPage />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
