import React, { useEffect, useState } from "react";
import { getEarningCategories } from "../api/EarningCategoryApi";
import EarningCategoryTable from "../components/EarningCategoryTable";
console.log("✅ EarningCategoryPage mounted");

function EarningCategoryPage() {
  const [earningCategories, setEarningCategories] = useState([]);

  useEffect(() => {
    getEarningCategories()
      .then((data) => setEarningCategories(data))
      .catch((err) => console.error("Error fetching earning categories", err));
  }, []);

  return (
    <div>
      <EarningCategoryTable earningCategories={earningCategories} />
    </div>
  );
}

export default EarningCategoryPage;
