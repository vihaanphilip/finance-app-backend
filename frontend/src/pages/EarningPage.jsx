import React, { useEffect, useState } from "react";
import { getEarnings } from "../api/EarningApi";
import EarningTable from "../components/EarningTable";

function EarningPage() {
  const [earnings, setEarnings] = useState([]);

  useEffect(() => {
    loadEarnings();
  }, []);

  const loadEarnings = () => {
    getEarnings()
      .then((data) => setEarnings(data))
      .catch((err) => console.error("Error fetching earnings:", err));
  };

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
        <h1 style={{ margin: 0, color: "#212529" }}>Earnings</h1>
      </div>
      <EarningTable earnings={earnings} />
    </div>
  );
}

export default EarningPage;
