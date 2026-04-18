import React from "react";
import DataTable from "../common/DataTable";
import { FaPencilAlt } from "react-icons/fa";

const formatCurrency = (value) => `RM ${Number(value || 0).toFixed(2)}`;

function AccountTable({ accounts, onEdit }) {
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
    {
      key: "actions",
      title: "",
      render: (_, row) => (
        <button
          onClick={() => onEdit(row)}
          style={{
            background: "none",
            border: "none",
            color: "#007bff",
            cursor: "pointer",
            padding: "5px",
          }}
        >
          <FaPencilAlt />
        </button>
      ),
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
