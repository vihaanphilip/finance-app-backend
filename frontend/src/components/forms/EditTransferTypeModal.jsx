import React, { useState, useEffect } from "react";
import EditModal from "../common/EditModal";

function EditTransferTypeModal({ isOpen, onClose, onSubmit, initialData }) {
  const [formData, setFormData] = useState({
    id: "",
    label: "",
    description: "",
  });

  useEffect(() => {
    if (initialData) {
      setFormData({
        id: initialData.id || "",
        label: initialData.label || "",
        description: initialData.description || "",
      });
    } else {
      setFormData({
        id: "",
        label: "",
        description: "",
      });
    }
  }, [initialData, isOpen]);

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
    setFormData({ id: "", label: "", description: "" });
  };

  const handleClose = () => {
    setFormData({ id: "", label: "", description: "" });
    onClose();
  };

  return (
    <EditModal
      isOpen={isOpen}
      onClose={handleClose}
      title={initialData ? "Edit Transfer Type" : "Create New Transfer Type"}
    >
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "15px" }}>
          <label
            style={{ display: "block", marginBottom: "5px", fontWeight: "500" }}
          >
            ID
          </label>
          <input
            type="number"
            name="id"
            value={formData.id}
            onChange={handleInputChange}
            required
            disabled={!!initialData}
            style={{
              width: "100%",
              padding: "8px 12px",
              border: "1px solid #ced4da",
              borderRadius: "4px",
              fontSize: "14px",
              boxSizing: "border-box",
              backgroundColor: initialData ? "#e9ecef" : "white",
              cursor: initialData ? "not-allowed" : "text",
            }}
          />
        </div>

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
              boxSizing: "border-box",
              resize: "vertical",
            }}
          />
        </div>

        <div
          style={{
            display: "flex",
            justifyContent: "flex-end",
            gap: "10px",
            marginTop: "20px",
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
              fontSize: "14px",
            }}
          >
            Cancel
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
              fontSize: "14px",
            }}
          >
            {initialData ? "Update" : "Create"}
          </button>
        </div>
      </form>
    </EditModal>
  );
}

export default EditTransferTypeModal;
