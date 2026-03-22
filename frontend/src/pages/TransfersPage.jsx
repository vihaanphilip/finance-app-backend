import React from "react";
import { useNavigate } from "react-router-dom";

function TransfersPage() {
  const navigate = useNavigate();

  return (
    <div>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "20px",
          marginTop: "40px",
          padding: "0 20px",
        }}
      >
        <h1 style={{ margin: 0, color: "#212529" }}>Transfers</h1>
        <div style={{ display: "flex", gap: "10px" }}>
          <button
            onClick={() => navigate("/transfertypes")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#ffc107",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              fontSize: "14px",
              lineHeight: "1",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              transition: "background-color 0.3s ease",
            }}
            onMouseOver={(e) => (e.target.style.backgroundColor = "#e0a800")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#ffc107")}
          >
            Transfer Types
          </button>
          <button
            onClick={() => navigate("/transfercategories")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#ffc107",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              fontSize: "14px",
              lineHeight: "1",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              transition: "background-color 0.3s ease",
            }}
            onMouseOver={(e) => (e.target.style.backgroundColor = "#e0a800")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#ffc107")}
          >
            Transfer Categories
          </button>
        </div>
      </div>

      <div style={{ padding: "0 20px" }}>
        <p>Manage and review account transfers here.</p>
      </div>
    </div>
  );
}

export default TransfersPage;
