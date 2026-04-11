import React, { useState, useEffect } from "react";
import MonthSelector from "../components/common/MonthSelector";
import EarningSummaryTable from "../components/tables/EarningSummaryTable";
import { getEarningSummary } from "../api/EarningApi";
import { getAccountSummary } from "../api/SummaryApi";
import AccountSummaryTable from "../components/tables/AccountSummaryTable";
import { toast } from "react-toastify";

function Summary() {
  const currentDate = new Date();
  const [viewMode, setViewMode] = useState("month");
  const [selectedMonth, setSelectedMonth] = useState({
    year: currentDate.getFullYear(),
    month: currentDate.getMonth() + 1,
  });
  const [dateRange, setDateRange] = useState({
    startDate: `${currentDate.getFullYear()}-${String(
      currentDate.getMonth() + 1,
    ).padStart(2, "0")}-01`,
    endDate: `${currentDate.getFullYear()}-${String(
      currentDate.getMonth() + 1,
    ).padStart(2, "0")}-${String(
      new Date(
        currentDate.getFullYear(),
        currentDate.getMonth() + 1,
        0,
      ).getDate(),
    ).padStart(2, "0")}`,
  });
  const [summaryData, setSummaryData] = useState(null);
  const [accountSummaryData, setAccountSummaryData] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchSummary = async () => {
    setLoading(true);
    try {
      const data = await getEarningSummary(
        selectedMonth.year,
        selectedMonth.month,
      );
      setSummaryData(data);
    } catch (error) {
      toast.error("Failed to fetch earning summary");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const getMonthRange = (year, month) => {
    const startDate = `${year}-${String(month).padStart(2, "0")}-01`;
    const lastDay = new Date(year, month, 0).getDate();
    const endDate = `${year}-${String(month).padStart(2, "0")}-${String(
      lastDay,
    ).padStart(2, "0")}`;
    return { startDate, endDate };
  };

  const fetchAccountSummary = async (startDate, endDate) => {
    try {
      const data = await getAccountSummary(startDate, endDate);
      setAccountSummaryData(data || []);
    } catch (error) {
      toast.error("Failed to fetch account summary");
      console.error(error);
    }
  };

  useEffect(() => {
    if (viewMode === "month") {
      fetchSummary();
      const monthRange = getMonthRange(selectedMonth.year, selectedMonth.month);
      fetchAccountSummary(monthRange.startDate, monthRange.endDate);
      return;
    }

    setSummaryData(null);
    fetchAccountSummary(dateRange.startDate, dateRange.endDate);
  }, [selectedMonth, viewMode, dateRange]);

  const handleMonthChange = (monthData) => {
    setSelectedMonth(monthData);
  };

  const handleRangeDateChange = (e) => {
    const { name, value } = e.target;
    setDateRange((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Summary</h1>

      <div style={{ marginBottom: "20px", display: "flex", gap: "16px" }}>
        <label>
          <input
            type="radio"
            name="summaryMode"
            value="month"
            checked={viewMode === "month"}
            onChange={() => setViewMode("month")}
          />{" "}
          By Month
        </label>
        <label>
          <input
            type="radio"
            name="summaryMode"
            value="range"
            checked={viewMode === "range"}
            onChange={() => setViewMode("range")}
          />{" "}
          By Date Range
        </label>
      </div>

      {viewMode === "month" ? (
        <MonthSelector
          selectedMonth={selectedMonth}
          onMonthChange={handleMonthChange}
        />
      ) : (
        <div
          style={{
            marginBottom: "20px",
            display: "flex",
            alignItems: "center",
            gap: "12px",
            flexWrap: "wrap",
          }}
        >
          <label htmlFor="start-date">Start Date:</label>
          <input
            id="start-date"
            name="startDate"
            type="date"
            value={dateRange.startDate}
            onChange={handleRangeDateChange}
          />
          <label htmlFor="end-date">End Date:</label>
          <input
            id="end-date"
            name="endDate"
            type="date"
            value={dateRange.endDate}
            onChange={handleRangeDateChange}
          />
        </div>
      )}

      <div style={{ marginTop: "24px" }}>
        <h2>Account Summary</h2>
        {accountSummaryData.length > 0 ? (
          <AccountSummaryTable accountSummaryData={accountSummaryData} />
        ) : (
          <p>No account summary data available</p>
        )}
      </div>

      <div style={{ marginTop: "24px" }}>
        <h2>Earning Summary</h2>
        {viewMode === "month" ? (
          loading ? (
            <p>Loading...</p>
          ) : summaryData ? (
            <div style={{ marginTop: "20px" }}>
              <EarningSummaryTable summaryData={summaryData} />
            </div>
          ) : (
            <p>No earning summary data available</p>
          )
        ) : (
          <p>Earning summary is available in month view.</p>
        )}
      </div>
    </div>
  );
}

export default Summary;
