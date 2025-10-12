import React from "react";
import DataTable from "./DataTable";

function EarningTypeTable({ earningTypes }) {
  const columns = [
    { key: "id", title: "ID" },
    { key: "label", title: "Label", highlight: true },
    { key: "description", title: "Description" },
  ];

  return (
    <DataTable
      data={earningTypes}
      columns={columns}
      keyField="id"
      title="Earning Types"
    />
  );
}

export default EarningTypeTable;
