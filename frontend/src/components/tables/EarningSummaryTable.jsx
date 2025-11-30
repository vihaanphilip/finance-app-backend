import React from "react";
import DataTable from "../common/DataTable";

function EarningSummaryTable({ summaryData }) {
  if (!summaryData || !summaryData.categories) {
    return <p>No summary data available</p>;
  }

  const columns = [
    {
      key: "earning_category_label",
      title: "Category",
    },
    {
      key: "total_earnings",
      title: "Total Earnings",
      render: (value) => `RM ${value.toFixed(2)}`,
    },
    {
      key: "percentage",
      title: "Percentage",
      render: (value, item) => {
        const percentage =
          (item.total_earnings / summaryData.total_earnings) * 100;
        return `${percentage.toFixed(2)}%`;
      },
    },
  ];

  return (
    <div>
      <div
        style={{
          marginBottom: "20px",
          padding: "15px",
          backgroundColor: "#f8f9fa",
          borderRadius: "8px",
          marginLeft: "20px",
          marginRight: "20px",
        }}
      >
        <h3 style={{ margin: "0 0 10px 0" }}>
          Month:{" "}
          {new Date(summaryData.month).toLocaleDateString("en-US", {
            year: "numeric",
            month: "long",
          })}
        </h3>
        <h2 style={{ margin: 0, color: "#28a745" }}>
          Total Earnings: RM {summaryData.total_earnings.toFixed(2)}
        </h2>
      </div>
      <DataTable
        columns={columns}
        data={summaryData.categories}
        keyField="earning_category_id"
      />
    </div>
  );
}

export default EarningSummaryTable;
