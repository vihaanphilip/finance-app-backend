// src/pages/EarningTypePage.jsx
import React, { useEffect, useState } from "react";
import { getEarningTypes } from "../api/EarningTypeApi";
import EarningTypeTable from "../components/EarningTypeTable";

function EarningTypePage() {
  const [earningTypes, setEarningTypes] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchEarningTypes = async () => {
      try {
        const data = await getEarningTypes();
        setEarningTypes(data);
      } catch (error) {
        console.error("Failed to fetch earning types:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchEarningTypes();
  }, []);

  if (loading) return <div>Loading...</div>;

  return (
    <div className="p-4">
      <EarningTypeTable earningTypes={earningTypes} />
    </div>
  );
}

export default EarningTypePage;
