import React from "react";
import { useNavigate } from "react-router-dom";

function HomePage() {
  const navigate = useNavigate();

  const handleGoToAccounts = () => {
    navigate("/accounts");
  };

  // Handler to navigate to Earnings page
  const handleGoToEarnings = () => {
    navigate("/earnings");
  };

  // Handler to navigate to Expenses page
  const handleGoToExpenses = () => {
    navigate("/expenses");
  };

  // Handler to navigate to Transfers page
  const handleGoToTransfers = () => {
    navigate("/transfers");
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
          onClick={handleGoToAccounts}
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
          View Accounts
        </button>
        {/* Button to navigate to Earnings page */}
        <button
          onClick={handleGoToEarnings}
          style={{
            padding: "15px 30px",
            fontSize: "1.2em",
            backgroundColor: "#28a745", // Green color matching Earning Types
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
          View Earnings
        </button>
        {/* Button to navigate to Expenses page */}
        <button
          onClick={handleGoToExpenses}
          style={{
            padding: "15px 30px",
            fontSize: "1.2em",
            backgroundColor: "#dc3545", // Red color for expenses
            color: "white",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
            transition: "background-color 0.3s ease",
          }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#c82333")} // Darker red on hover
          onMouseOut={(e) => (e.target.style.backgroundColor = "#dc3545")}
        >
          View Expenses
        </button>
        {/* Button to navigate to Transfers page */}
        <button
          onClick={handleGoToTransfers}
          style={{
            padding: "15px 30px",
            fontSize: "1.2em",
            backgroundColor: "#ffc107",
            color: "white",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
            transition: "background-color 0.3s ease",
          }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#e0a800")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#ffc107")}
        >
          View Transfers
        </button>
      </div>
    </div>
  );
}

export default HomePage;
