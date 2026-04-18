import React, { useState, useEffect } from "react";
import { getAccountTypes } from "../../api/AccountTypeApi";
import EditModal from "../common/EditModal";

function EditAccountModal({ isOpen, onClose, onSubmit }) {
  const [formData, setFormData] = useState({
    name: "",
    description: "",
    starting_amount: "",
    account_type_id: "",
  });
  const [accountTypes, setAccountTypes] = useState([]);

  useEffect(() => {
    if (isOpen) {
      getAccountTypes()
        .then((data) => {
          console.log("Fetched account types:", data);
          setAccountTypes(data);
        })
        .catch((err) => {
          console.error("Error fetching account types:", err);
          setAccountTypes([]);
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
    onSubmit({
      ...formData,
      starting_amount: Number(formData.starting_amount),
    });
    setFormData({
      name: "",
      description: "",
      starting_amount: "",
      account_type_id: "",
    });
  };

  const handleClose = () => {
    setFormData({
      name: "",
      description: "",
      starting_amount: "",
      account_type_id: "",
    });
    onClose();
  };

  return (
    <EditModal isOpen={isOpen} onClose={handleClose} title="Edit Account Modal">
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "15px" }}>
          <label
            style={{ display: "block", marginBottom: "5px", fontWeight: "500" }}
          >
            Account Name
          </label>
          <input
            type="text"
            name="name"
            value={formData.name}
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
            Account Type
          </label>
          <select
            name="account_type_id"
            value={formData.account_type_id}
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
            <option value="">Select Account Type</option>
            {accountTypes.map((type) => (
              <option key={type.id} value={type.id}>
                {type.label}
              </option>
            ))}
          </select>
        </div>

        <div style={{ marginBottom: "15px" }}>
          <label
            style={{
              display: "block",
              marginBottom: "5px",
              fontWeight: "500",
            }}
          >
            Starting Amount
          </label>
          <input
            type="number"
            name="starting_amount"
            value={formData.starting_amount}
            onChange={handleInputChange}
            required
            step="0.01"
            placeholder="0.00"
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
      </form>
    </EditModal>
  );
}

export default EditAccountModal;
