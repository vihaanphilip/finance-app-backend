import React, { useState } from "react";
import MonthSelector from "../components/common/MonthSelector";

function EarningSummary() {
  const currentDate = new Date();
  const [selectedMonth, setSelectedMonth] = useState({
    year: currentDate.getFullYear(),
    month: currentDate.getMonth() + 1,
  });

  const handleMonthChange = (monthData) => {
    setSelectedMonth(monthData);
    console.log("Selected:", monthData); // You can fetch data here
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Earning Summary</h1>
      <MonthSelector
        selectedMonth={selectedMonth}
        onMonthChange={handleMonthChange}
      />
      <p>
        Selected: {selectedMonth.month}/{selectedMonth.year}
      </p>
      {/* Add your earning summary content here */}
    </div>
  );
}

export default EarningSummary;
