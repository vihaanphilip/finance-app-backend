import React from "react";
import DataTable from "./DataTable";

function AccountTable({ accounts }) {
  const columns = [
    {
      key: "id",
      title: "ID",
    },
    {
      key: "name",
      title: "Account Name",
      highlight: true,
    },
    {
      key: "description",
      title: "Description",
    },
    {
      key: "account_type_label",
      title: "Account Type",
    },
  ];

  return (
    <DataTable
      data={accounts}
      columns={columns}
      keyField="id"
      // title="Accounts"
    />
  );
}

export default AccountTable;
