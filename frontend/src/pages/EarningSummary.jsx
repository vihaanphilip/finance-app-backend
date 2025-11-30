import React, { useState, useEffect } from "react";
import MonthSelector from "../components/common/MonthSelector";
import EarningSummaryTable from "../components/tables/EarningSummaryTable";
import { getEarningSummary } from "../api/EarningApi";
import { toast } from "react-toastify";

function EarningSummary() {
  const currentDate = new Date();
  const [selectedMonth, setSelectedMonth] = useState({
    year: currentDate.getFullYear(),
    month: currentDate.getMonth() + 1,
  });
  const [summaryData, setSummaryData] = useState(null);
  const [loading, setLoading] = useState(false);

  const fetchSummary = async () => {
    setLoading(true);
    try {
      const data = await getEarningSummary(
        selectedMonth.year,
        selectedMonth.month
      );
      setSummaryData(data);
    } catch (error) {
      toast.error("Failed to fetch earning summary");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSummary();
  }, [selectedMonth]);

  const handleMonthChange = (monthData) => {
    setSelectedMonth(monthData);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Earning Summary</h1>
      <MonthSelector
        selectedMonth={selectedMonth}
        onMonthChange={handleMonthChange}
      />

      {loading ? (
        <p>Loading...</p>
      ) : summaryData ? (
        <div style={{ marginTop: "20px" }}>
          <EarningSummaryTable summaryData={summaryData} />
        </div>
      ) : (
        <p>No data available</p>
      )}
    </div>
  );
}

export default EarningSummary;
