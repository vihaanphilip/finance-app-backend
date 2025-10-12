import React from "react";
import DataTable from "./DataTable";

function EarningCategoryTable({ earningCategories }) {
  const columns = [
    { key: "id", title: "ID" },
    { key: "label", title: "Label", highlight: true },
    { key: "description", title: "Description" },
  ];

  return (
    <DataTable
      data={earningCategories}
      columns={columns}
      keyField="id"
      // title="Earning Categories"
    />
  );
}

export default EarningCategoryTable;
