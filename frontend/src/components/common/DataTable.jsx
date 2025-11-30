import React from "react";

function DataTable({ 
  data, 
  columns, 
  keyField = "id", 
  title = null,
  style = {} 
}) {
  const defaultTableStyle = {
    marginTop: "20px",
    marginLeft: "20px",
    marginRight: "20px",
    borderCollapse: "collapse",
    width: "calc(100% - 40px)",
    boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
    borderRadius: "8px",
    overflow: "hidden",
    ...style
  };

  const headerCellStyle = {
    padding: "15px",
    textAlign: "left",
    fontWeight: "600",
    color: "#212529",
  };

  const cellStyle = {
    padding: "15px",
    color: "#495057",
  };

  const highlightedCellStyle = {
    padding: "15px",
    fontWeight: "500",
    color: "#212529",
  };

  return (
    <div>
      {title && (
        <h2 style={{ marginLeft: "20px", marginBottom: "10px", color: "#212529" }}>
          {title}
        </h2>
      )}
      <table style={defaultTableStyle}>
        <thead>
          <tr style={{ backgroundColor: "#e9ecef" }}>
            {columns.map((column) => (
              <th key={column.key} style={headerCellStyle}>
                {column.title}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr
              key={item[keyField]}
              style={{
                backgroundColor: index % 2 === 0 ? "#ffffff" : "#f1f3f4",
                borderBottom: "1px solid #ced4da",
              }}
            >
              {columns.map((column) => (
                <td 
                  key={column.key} 
                  style={column.highlight ? highlightedCellStyle : cellStyle}
                >
                  {column.render ? column.render(item[column.key], item) : item[column.key]}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default DataTable;