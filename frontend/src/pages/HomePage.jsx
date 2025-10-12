import React from "react";
import { useNavigate } from "react-router-dom";

function HomePage() {
  const navigate = useNavigate();

  const handleGoToAccountTypes = () => {
    navigate("/accounttypes");
  };

  const handleGoToAccounts = () => {
    navigate("/accounts");
  };

  // Handler to navigate to Earning Types page
  const handleGoToEarningTypes = () => {
    navigate("/earningtypes");
  };

  // Handler to navigate to Earning Categories page
  const handleGoToEarningCategories = () => {
    navigate("/earningcategories");
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        minHeight: "calc(100vh - 80px)",
        padding: "20px",
      }}
    >
      <h1 style={{ marginBottom: "30px", fontSize: "2.5em" }}>
        Welcome to Finance App
      </h1>
      <div
        style={{
          display: "flex",
          gap: "20px",
          flexWrap: "wrap",
          justifyContent: "center",
        }}
      >
        <button
          onClick={handleGoToAccountTypes}
          style={{
            padding: "15px 30px",
            fontSize: "1.2em",
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
            transition: "background-color 0.3s ease",
          }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#0056b3")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#007bff")}
        >
          View Account Types
        </button>
        <button
          onClick={handleGoToAccounts}
          style={{
            padding: "15px 30px",
            fontSize: "1.2em",
            backgroundColor: "#dc3545",
            color: "white",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
            transition: "background-color 0.3s ease",
          }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#b02a37")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#dc3545")}
        >
          View Accounts
        </button>
        {/* Button to navigate to Earning Types page */}
        <button
          onClick={handleGoToEarningTypes}
          style={{
            padding: "15px 30px",
            fontSize: "1.2em",
            backgroundColor: "#28a745", // Green color for distinction
            color: "white",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
            transition: "background-color 0.3s ease",
          }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#218838")} // Darker green on hover
          onMouseOut={(e) => (e.target.style.backgroundColor = "#28a745")}
        >
          View Earning Types
        </button>
        {/* Button to navigate to Earning Categories page */}
        <button
          onClick={handleGoToEarningCategories}
          style={{
            padding: "15px 30px",
            fontSize: "1.2em",
            backgroundColor: "#ffc107", // Yellow/amber color for distinction
            color: "white",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
            transition: "background-color 0.3s ease",
          }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#e0a800")} // Darker amber on hover
          onMouseOut={(e) => (e.target.style.backgroundColor = "#ffc107")}
        >
          View Earning Categories
        </button>
      </div>
    </div>
  );
}

export default HomePage;
