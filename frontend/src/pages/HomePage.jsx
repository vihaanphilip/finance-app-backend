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

  return (
    <div style={{ 
      display: "flex", 
      flexDirection: "column", 
      alignItems: "center", 
      justifyContent: "center", 
      minHeight: "calc(100vh - 80px)",
      padding: "20px"
    }}>
      <h1 style={{ marginBottom: "30px", fontSize: "2.5em" }}>
        Welcome to Finance App
      </h1>
      <div style={{ display: "flex", gap: "20px" }}>
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
            transition: "background-color 0.3s ease"
          }}
          onMouseOver={(e) => e.target.style.backgroundColor = "#0056b3"}
          onMouseOut={(e) => e.target.style.backgroundColor = "#007bff"}
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
            transition: "background-color 0.3s ease"
          }}
          onMouseOver={(e) => e.target.style.backgroundColor = "#b02a37"}
          onMouseOut={(e) => e.target.style.backgroundColor = "#dc3545"}
        >
          View Accounts
        </button>
      </div>
    </div>
  );
}

export default HomePage;