import React from "react";

function MonthSelector({ selectedMonth, onMonthChange }) {
  const months = [
    { value: 1, label: "January" },
    { value: 2, label: "February" },
    { value: 3, label: "March" },
    { value: 4, label: "April" },
    { value: 5, label: "May" },
    { value: 6, label: "June" },
    { value: 7, label: "July" },
    { value: 8, label: "August" },
    { value: 9, label: "September" },
    { value: 10, label: "October" },
    { value: 11, label: "November" },
    { value: 12, label: "December" },
  ];

  const currentYear = new Date().getFullYear();
  const years = Array.from({ length: 10 }, (_, i) => currentYear - i);

  const handleChange = (e) => {
    const [year, month] = e.target.value.split("-");
    onMonthChange({ year: parseInt(year), month: parseInt(month) });
  };

  const currentValue = selectedMonth
    ? `${selectedMonth.year}-${String(selectedMonth.month).padStart(2, "0")}`
    : "";

  return (
    <div style={{ marginBottom: "20px" }}>
      <label
        htmlFor="month-selector"
        style={{ marginRight: "10px", fontWeight: "bold" }}
      >
        Select Month:
      </label>
      <input
        id="month-selector"
        type="month"
        value={currentValue}
        onChange={handleChange}
        style={{
          padding: "8px 12px",
          fontSize: "1em",
          borderRadius: "4px",
          border: "1px solid #ccc",
          cursor: "pointer",
        }}
      />
    </div>
  );
}

export default MonthSelector;
