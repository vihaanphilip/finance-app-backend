import React from "react";
import DataTable from "../common/DataTable";

function ExpenseCategoryTable({ expenseCategories }) {
  const columns = [
    { key: "id", title: "ID" },
    { key: "label", title: "Label", highlight: true },
    { key: "description", title: "Description" },
    { key: "expense_type_label", title: "Expense Type" },
  ];

  return (
    <DataTable
      data={expenseCategories}
      columns={columns}
      keyField="id"
    />
  );
}

export default ExpenseCategoryTable;
