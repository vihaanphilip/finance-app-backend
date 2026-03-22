import React from "react";
import DataTable from "../common/DataTable";
import { FaTrash, FaPencilAlt } from "react-icons/fa";

function formatCurrency(value) {
  if (value === null || value === undefined || value === "") {
    return "-";
  }

  return `RM ${Number(value).toLocaleString("en-US", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  })}`;
}

function formatDate(value) {
  if (!value) {
    return "-";
  }

  const date = new Date(value);
  if (Number.isNaN(date.getTime())) {
    return value;
  }

  const dayName = date.toLocaleString("en-GB", { weekday: "long" });
  const formattedDate = date.toLocaleString("en-GB", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
  return `${dayName}, ${formattedDate}`;
}

function TransferTable({ transfers, onDelete, onEdit }) {
  const columns = [
    {
      key: "id",
      title: "ID",
    },
    {
      key: "amount",
      title: "Amount",
      highlight: true,
      render: (value) => formatCurrency(value),
    },
    {
      key: "description",
      title: "Description",
      render: (value) => value || "-",
    },
    {
      key: "from_account_label",
      title: "From Account",
      render: (_, row) =>
        row.from_account_label ||
        row.from_account_name ||
        row.source_account_label ||
        row.account_from_label ||
        "-",
    },
    {
      key: "to_account_label",
      title: "To Account",
      render: (_, row) =>
        row.to_account_label ||
        row.to_account_name ||
        row.destination_account_label ||
        row.account_to_label ||
        "-",
    },
    {
      key: "transfer_category_label",
      title: "Category",
      render: (_, row) => row.transfer_category_label || row.category_label || "-",
    },
    {
      key: "transfer_type_label",
      title: "Type",
      render: (_, row) => row.transfer_type_label || row.type_label || "-",
    },
    {
      key: "transaction_date",
      title: "Transaction Date",
      render: (value) => formatDate(value),
    },
    {
      key: "actions",
      title: "",
      render: (_, row) => (
        <div style={{ display: "flex", gap: "10px" }}>
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
          <button
            onClick={() => onDelete(row.id)}
            style={{
              background: "none",
              border: "none",
              color: "#dc3545",
              cursor: "pointer",
              padding: "5px",
            }}
          >
            <FaTrash />
          </button>
        </div>
      ),
    },
  ];

  return <DataTable data={transfers} columns={columns} keyField="id" />;
}

export default TransferTable;