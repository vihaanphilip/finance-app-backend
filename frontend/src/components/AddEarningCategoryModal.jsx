import React, { useState } from "react";
import EditModal from "./EditModal";

function AddEarningCategoryModal({ isOpen, onClose, onSubmit }) {
  const [formData, setFormData] = useState({
    label: "",
    description: "",
    earning_type: "",
  });

  // Test data for earning types (unimplemented)
  const earningTypes = [
    { id: 1, label: "Salary" },
    { id: 2, label: "Bonus" },
    { id: 3, label: "Commission" },
    { id: 4, label: "Other" },
  ];

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
    setFormData({ label: "", description: "", earning_type: "" });
  };

  const handleClose = () => {
    setFormData({ label: "", description: "", earning_type: "" });
    onClose();
  };

  const formFields = (
    <>
      <div style={{ marginBottom: "15px" }}>
        <label
          style={{ display: "block", marginBottom: "5px", fontWeight: "500" }}
        >
          Label
        </label>
        <input
          type="text"
          name="label"
          value={formData.label}
          onChange={handleInputChange}
          required
          style={{
            width: "100%",
            padding: "8px 12px",
            border: "1px solid #ced4da",
            borderRadius: "4px",
            fontSize: "14px",
            boxSizing: "border-box",
          }}
        />
      </div>

      <div style={{ marginBottom: "15px" }}>
        <label
          style={{
            display: "block",
            marginBottom: "5px",
            fontWeight: "500",
          }}
        >
          Description
        </label>
        <textarea
          name="description"
          value={formData.description}
          onChange={handleInputChange}
          required
          rows="3"
          style={{
            width: "100%",
            padding: "8px 12px",
            border: "1px solid #ced4da",
            borderRadius: "4px",
            fontSize: "14px",
            resize: "vertical",
            boxSizing: "border-box",
          }}
        />
      </div>

      <div style={{ marginBottom: "20px" }}>
        <label
          style={{
            display: "block",
            marginBottom: "5px",
            fontWeight: "500",
          }}
        >
          Earning Type
        </label>
        <select
          name="earning_type"
          value={formData.earning_type}
          onChange={handleInputChange}
          required
          style={{
            width: "100%",
            padding: "8px 12px",
            border: "1px solid #ced4da",
            borderRadius: "4px",
            fontSize: "14px",
            backgroundColor: "white",
            boxSizing: "border-box",
          }}
        >
          <option value="">Select Earning Type</option>
          {earningTypes.map((type) => (
            <option key={type.id} value={type.id}>
              {type.label}
            </option>
          ))}
        </select>
      </div>
      <div
        style={{
          display: "flex",
          gap: "10px",
          justifyContent: "flex-end",
        }}
      >
        <button
          type="button"
          onClick={handleClose}
          style={{
            padding: "8px 16px",
            backgroundColor: "#6c757d",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Close
        </button>
        <button
          type="submit"
          style={{
            padding: "8px 16px",
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Submit
        </button>
      </div>
    </>
  );

  return (
    <EditModal
      isOpen={isOpen}
      onClose={handleClose}
      onSubmit={handleSubmit}
      title="Create New Earning Category"
      submitLabel="Submit"
    >
      {formFields}
    </EditModal>
  );
}

export default AddEarningCategoryModal;
