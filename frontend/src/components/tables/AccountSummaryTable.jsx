import React from "react";
import DataTable from "../common/DataTable";

const formatCurrency = (value) => `RM ${Number(value || 0).toFixed(2)}`;

function AccountSummaryTable({ accountSummaryData }) {
  if (!accountSummaryData || accountSummaryData.length === 0) {
    return <p>No account summary data available</p>;
  }

  const columns = [
    {
      key: "account_name",
      title: "Account",
      highlight: true,
    },
    {
      key: "starting_amount",
      title: "Starting Amount",
      render: (value) => formatCurrency(value),
    },
    {
      key: "earnings_amount",
      title: "Earnings",
      render: (value) => formatCurrency(value),
    },
    {
      key: "expenses_amount",
      title: "Expenses",
      render: (value) => formatCurrency(value),
    },
    {
      key: "transfers_in_amount",
      title: "Transfers In",
      render: (value) => formatCurrency(value),
    },
    {
      key: "transfers_out_amount",
      title: "Transfers Out",
      render: (value) => formatCurrency(value),
    },
    {
      key: "balance_amount",
      title: "Balance",
      render: (value) => formatCurrency(value),
      highlight: true,
    },
  ];

  return (
    <DataTable
      columns={columns}
      data={accountSummaryData}
      keyField="account_id"
    />
  );
}

export default AccountSummaryTable;
