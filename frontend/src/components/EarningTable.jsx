import React from "react";
import DataTable from "./DataTable";

function EarningTable({ earnings }) {
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
        const formattedDate = date.toLocaleString("en-GB", {
          day: "2-digit",
          month: "2-digit",
          year: "numeric",
          hour: "2-digit",
          minute: "2-digit",
          second: "2-digit",
          hour12: false,
        });
        return `${dayName}, ${formattedDate}`;
      },
    },
  ];

  return <DataTable data={earnings} columns={columns} keyField="id" />;
}

export default EarningTable;
