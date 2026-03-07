import React from "react";
import DataTable from "../common/DataTable";
import { FaTrash, FaPencilAlt } from "react-icons/fa";

function ExpenseTable({ expenses, onDelete, onEdit }) {
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
      key: "expense_category_label",
      title: "Category",
    },
    {
      key: "expense_type_label",
      title: "Type",
    },
    {
      key: "transaction_date",
      title: "Transaction Date",
      render: (value) => {
        const date = new Date(value);
        const dayName = date.toLocaleString("en-GB", { weekday: "long" });
        const formattedDate = date.toLocaleString("en-GB", {
          day: "2-digit",
          month: "2-digit",
          year: "numeric",
        });
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

  return <DataTable data={expenses} columns={columns} keyField="id" />;
}

export default ExpenseTable;
