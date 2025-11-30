import React from "react";
import DataTable from "../common/DataTable";

function EarningCategoryTable({ earningCategories }) {
  const columns = [
    { key: "id", title: "ID" },
    { key: "label", title: "Label", highlight: true },
    { key: "description", title: "Description" },
    { key: "earning_type_label", title: "Earning Type" },
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
