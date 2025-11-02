import React, { useState, useEffect } from "react";
import { getAccounts } from "../api/AccountApi";
import { getEarningCategories } from "../api/EarningCategoryApi";
import { getEarningTypes } from "../api/EarningTypeApi";

function AddEarningModal({ isOpen, onClose, onSubmit }) {
  const getLocalTimestamp = () => {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, "0");
    const day = String(now.getDate()).padStart(2, "0");
    const hours = String(now.getHours()).padStart(2, "0");
    const minutes = String(now.getMinutes()).padStart(2, "0");
    return `${year}-${month}-${day}T${hours}:${minutes}:00.000Z`;
  };

  const formatForDateTimeLocal = (isoString) => {
    return isoString.slice(0, 16);
  };

  const [formData, setFormData] = useState({
    amount: "",
    description: "",
    earning_category_id: "",
    earning_type_id: "",
    account_id: "",
    created_at: getLocalTimestamp(),
    last_modified_at: getLocalTimestamp(),
  });

  const [earningCategories, setEarningCategories] = useState([]);
  const [earningTypes, setEarningTypes] = useState([]);
  const [accounts, setAccounts] = useState([]);

  useEffect(() => {
    if (isOpen) {
      // Reset form when modal opens
      const localTimestamp = getLocalTimestamp();
      setFormData({
        amount: "",
        description: "",
        earning_category_id: "",
        earning_type_id: "",
        account_id: "",
        created_at: localTimestamp,
        last_modified_at: localTimestamp,
      });

      // Fetch all necessary data
      Promise.all([getEarningCategories(), getEarningTypes(), getAccounts()])
        .then(([categories, types, accountsList]) => {
          setEarningCategories(categories);
          setEarningTypes(types);
          setAccounts(accountsList);
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
          // TODO: Add error handling
        });
    }
  }, [isOpen]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name === "created_at") {
      // Store the exact string from the input with Z suffix to mark it as UTC
      const isoString = `${value}:00.000Z`;
      setFormData((prev) => ({
        ...prev,
        [name]: isoString,
        last_modified_at: isoString,
      }));
    } else {
      setFormData((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const submissionData = {
      ...formData,
      last_modified_at: getLocalTimestamp(),
    };
    onSubmit(submissionData);
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: "rgba(0, 0, 0, 0.5)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 1000,
      }}
    >
      <div
        style={{
          backgroundColor: "white",
          padding: "30px 40px",
          borderRadius: "8px",
          boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
          width: "400px",
          maxWidth: "90vw",
        }}
      >
        <h2 style={{ marginTop: 0, marginBottom: "20px" }}>Add New Earning</h2>
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: "15px" }}>
            <label style={{ display: "block", marginBottom: "5px" }}>
              Amount (RM) *
            </label>
            <input
              type="number"
              step="0.01"
              name="amount"
              value={formData.amount}
              onChange={handleInputChange}
              required
              style={{
                width: "100%",
                padding: "8px",
                borderRadius: "4px",
                border: "1px solid #ced4da",
                boxSizing: "border-box",
              }}
            />
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label style={{ display: "block", marginBottom: "5px" }}>
              Description
            </label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleInputChange}
              style={{
                width: "100%",
                padding: "8px",
                borderRadius: "4px",
                border: "1px solid #ced4da",
                boxSizing: "border-box",
                minHeight: "100px",
              }}
            />
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label style={{ display: "block", marginBottom: "5px" }}>
              Transaction Date *
            </label>
            <input
              type="datetime-local"
              name="created_at"
              value={formatForDateTimeLocal(formData.created_at)}
              onChange={handleInputChange}
              required
              style={{
                width: "100%",
                padding: "8px",
                borderRadius: "4px",
                border: "1px solid #ced4da",
                boxSizing: "border-box",
              }}
            />
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label style={{ display: "block", marginBottom: "5px" }}>
              Account *
            </label>
            <select
              name="account_id"
              value={formData.account_id}
              onChange={handleInputChange}
              required
              style={{
                width: "100%",
                padding: "8px",
                borderRadius: "4px",
                border: "1px solid #ced4da",
                boxSizing: "border-box",
              }}
            >
              <option value="">Select Account</option>
              {accounts.map((account) => (
                <option key={account.id} value={account.id}>
                  {account.name}
                </option>
              ))}
            </select>
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label style={{ display: "block", marginBottom: "5px" }}>
              Category *
            </label>
            <select
              name="earning_category_id"
              value={formData.earning_category_id}
              onChange={handleInputChange}
              required
              style={{
                width: "100%",
                padding: "8px",
                borderRadius: "4px",
                border: "1px solid #ced4da",
                boxSizing: "border-box",
              }}
            >
              <option value="">Select Category</option>
              {earningCategories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.label}
                </option>
              ))}
            </select>
          </div>

          <div style={{ marginBottom: "20px" }}>
            <label style={{ display: "block", marginBottom: "5px" }}>
              Type *
            </label>
            <select
              name="earning_type_id"
              value={formData.earning_type_id}
              onChange={handleInputChange}
              required
              style={{
                width: "100%",
                padding: "8px",
                borderRadius: "4px",
                border: "1px solid #ced4da",
                boxSizing: "border-box",
              }}
            >
              <option value="">Select Type</option>
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
              justifyContent: "flex-end",
              gap: "10px",
            }}
          >
            <button
              type="button"
              onClick={onClose}
              style={{
                padding: "8px 16px",
                border: "1px solid #ced4da",
                borderRadius: "4px",
                backgroundColor: "white",
                cursor: "pointer",
              }}
            >
              Cancel
            </button>
            <button
              type="submit"
              style={{
                padding: "8px 16px",
                backgroundColor: "#28a745",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            >
              Add Earning
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default AddEarningModal;
