import React from "react";
import DataTable from "../common/DataTable";

function AccountTypeTable({ accountTypes }) {
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

  return (
    <DataTable
      data={accountTypes}
      columns={columns}
      keyField="id"
      title="Account Types"
    />
  );
}

export default AccountTypeTable;
