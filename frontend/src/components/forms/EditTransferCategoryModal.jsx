import React, { useState, useEffect } from "react";
import { getTransferTypes } from "../../api/TransferTypeApi";
import EditModal from "../common/EditModal";

function EditTransferCategoryModal({ isOpen, onClose, onSubmit, initialData }) {
  const [formData, setFormData] = useState({
    id: "",
    label: "",
    description: "",
    transfer_type_id: "",
  });

  const [transferTypes, setTransferTypes] = useState([]);

  useEffect(() => {
    if (initialData) {
      setFormData({
        id: initialData.id || "",
        label: initialData.label || "",
        description: initialData.description || "",
        transfer_type_id: initialData.transfer_type_id || "",
      });
    } else {
      setFormData({
        id: "",
        label: "",
        description: "",
        transfer_type_id: "",
      });
    }
  }, [initialData, isOpen]);

  useEffect(() => {
    if (isOpen) {
      getTransferTypes()
        .then((data) => {
          setTransferTypes(data);
        })
        .catch((err) => {
          console.error("Error fetching transfer types:", err);
          setTransferTypes([]);
        });
    }
  }, [isOpen]);

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
    setFormData({ id: "", label: "", description: "", transfer_type_id: "" });
  };

  const handleClose = () => {
    setFormData({ id: "", label: "", description: "", transfer_type_id: "" });
    onClose();
  };

  return (
    <EditModal
      isOpen={isOpen}
      onClose={handleClose}
      title={
        initialData ? "Edit Transfer Category" : "Create New Transfer Category"
      }
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
            Transfer Type
          </label>
          <select
            name="transfer_type_id"
            value={formData.transfer_type_id}
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
            <option value="">Select Transfer Type</option>
            {transferTypes.map((type) => (
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
            {initialData ? "Update" : "Submit"}
          </button>
        </div>
      </form>
    </EditModal>
  );
}

export default EditTransferCategoryModal;
