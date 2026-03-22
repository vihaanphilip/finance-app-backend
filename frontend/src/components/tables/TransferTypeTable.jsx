import React from "react";
import DataTable from "../common/DataTable";

function TransferTypeTable({ transferTypes }) {
  const columns = [
    {
      key: "id",
      title: "ID",
    },
    {
      key: "label",
      title: "Label",
      highlight: true,
    },
    {
      key: "description",
      title: "Description",
    },
  ];

  return <DataTable data={transferTypes} columns={columns} keyField="id" />;
}

export default TransferTypeTable;
