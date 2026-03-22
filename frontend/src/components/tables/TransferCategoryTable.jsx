import React from "react";
import DataTable from "../common/DataTable";
import { FaTrash, FaPencilAlt } from "react-icons/fa";

function TransferCategoryTable({ transferCategories, onDelete, onEdit }) {
  const columns = [
    { key: "id", title: "ID" },
    { key: "label", title: "Label", highlight: true },
    { key: "description", title: "Description" },
    { key: "transfer_type_label", title: "Transfer Type" },
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

  return (
    <DataTable data={transferCategories} columns={columns} keyField="id" />
  );
}

export default TransferCategoryTable;
