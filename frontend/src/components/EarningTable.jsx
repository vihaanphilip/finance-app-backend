import React from "react";
import DataTable from "./DataTable";
import { FaTrash, FaPencilAlt } from "react-icons/fa";

function EarningTable({ earnings, onDelete, onEdit }) {
  const columns = [
    {
      key: "id",
      title: "ID",
    },
    {
      key: "amount",
      title: "Amount",
      highlight: true,
      render: (value) =>
        `RM ${Number(value).toLocaleString("en-US", {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
        })}`,
    },
    {
      key: "description",
      title: "Description",
    },
    {
      key: "account_label",
      title: "Account",
    },
    {
      key: "earning_category_label",
      title: "Category",
    },
    {
      key: "earning_type_label",
      title: "Type",
    },
    {
      key: "created_at",
      title: "Transaction Date",
      render: (value) => {
        const date = new Date(value);
        const dayName = date.toLocaleString("en-GB", { weekday: "long" });
        const formattedDate = date
          .toLocaleString("en-US", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour: "numeric",
            minute: "2-digit",
            hour12: true,
          })
          .replace(/am|pm/i, (match) => match.toUpperCase());
        return `${dayName}, ${formattedDate}`;
      },
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

  return <DataTable data={earnings} columns={columns} keyField="id" />;
}

export default EarningTable;
