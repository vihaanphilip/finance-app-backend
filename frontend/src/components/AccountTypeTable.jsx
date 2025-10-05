import React from "react";

function AccountTypeTable({ accountTypes }) {
  return (
    <table
      style={{
        marginTop: "20px",
        marginLeft: "20px",
        marginRight: "20px",
        borderCollapse: "collapse",
        width: "calc(100% - 40px)", // 20px left margin + 20px right margin
        boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
        borderRadius: "8px",
        overflow: "hidden",
      }}
    >
      <thead>
        <tr style={{ backgroundColor: "#e9ecef" }}>
          <th
            style={{
              padding: "15px",
              textAlign: "left",
              fontWeight: "600",
              color: "#212529",
            }}
          >
            ID
          </th>
          <th
            style={{
              padding: "15px",
              textAlign: "left",
              fontWeight: "600",
              color: "#212529",
            }}
          >
            Label
          </th>
          <th
            style={{
              padding: "15px",
              textAlign: "left",
              fontWeight: "600",
              color: "#212529",
            }}
          >
            Description
          </th>
        </tr>
      </thead>
      <tbody>
        {accountTypes.map((acc, index) => (
          <tr
            key={acc.id}
            style={{
              backgroundColor: index % 2 === 0 ? "#ffffff" : "#f1f3f4",
              borderBottom: "1px solid #ced4da",
            }}
          >
            <td style={{ padding: "15px", color: "#495057" }}>{acc.id}</td>
            <td
              style={{ padding: "15px", fontWeight: "500", color: "#212529" }}
            >
              {acc.label}
            </td>
            <td style={{ padding: "15px", color: "#495057" }}>
              {acc.description}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default AccountTypeTable;
