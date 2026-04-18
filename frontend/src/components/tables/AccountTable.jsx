import React from "react";
import DataTable from "../common/DataTable";

const formatCurrency = (value) => `RM ${Number(value || 0).toFixed(2)}`;

function AccountTable({ accounts }) {
  const columns = [
    {
      key: "id",
      title: "ID",
    },
    {
      key: "name",
      title: "Account Name",
      highlight: true,
    },
    {
      key: "description",
      title: "Description",
    },
    {
      key: "account_type_label",
      title: "Account Type",
    },
    {
      key: "starting_amount",
      title: "Starting Amount",
      render: (value) => formatCurrency(value),
    },
  ];

  return (
    <DataTable
      data={accounts}
      columns={columns}
      keyField="id"
      // title="Accounts"
    />
  );
}

export default AccountTable;
