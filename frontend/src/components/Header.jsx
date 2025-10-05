import React from "react";
import { useNavigate } from "react-router-dom";

function Header() {
  const navigate = useNavigate();

  const handleLogoClick = () => {
    navigate("/");
  };

  return (
    <header style={{
      backgroundColor: "#f8f9fa",
      borderBottom: "1px solid #dee2e6",
      padding: "15px 20px",
      position: "sticky",
      top: 0,
      zIndex: 1000,
      boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)"
    }}>
      <h1 
        onClick={handleLogoClick}
        style={{
          margin: 0,
          fontSize: "1.8em",
          fontWeight: "600",
          color: "#212529",
          cursor: "pointer",
          display: "inline-block",
          transition: "color 0.3s ease"
        }}
        onMouseOver={(e) => e.target.style.color = "#007bff"}
        onMouseOut={(e) => e.target.style.color = "#212529"}
      >
        Finance App
      </h1>
    </header>
  );
}

export default Header;