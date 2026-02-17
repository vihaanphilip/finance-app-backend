import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  getEarnings,
  createEarning,
  updateEarning,
  deleteEarning,
  uploadEarnings,
} from "../api/EarningApi";
import EarningTable from "../components/tables/EarningTable";
import EditEarningModal from "../components/forms/EditEarningModal";
import UploadEarningModal from "../components/forms/UploadEarningModal";

function EarningPage() {
  const [earnings, setEarnings] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isUploadModalOpen, setIsUploadModalOpen] = useState(false);
  const [editingEarning, setEditingEarning] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadEarnings();
  }, []);

  const handleSubmitEarning = async (earningData) => {
    try {
      if (editingEarning) {
        // Update existing earning
        await updateEarning(editingEarning.id, earningData);
        toast.success("Earning updated successfully!");
      } else {
        // Create new earning
        await createEarning(earningData);
        toast.success("Earning added successfully!");
      }
      loadEarnings();
      setEditingEarning(null);
    } catch (error) {
      console.error("Error saving earning:", error);
      toast.error("Error saving earning. Please try again.");
    }
  };

  const handleEditEarning = (earning) => {
    setEditingEarning(earning);
    setIsModalOpen(true);
  };

  const handleDeleteEarning = async (id) => {
    if (window.confirm("Are you sure you want to delete this earning?")) {
      try {
        await deleteEarning(id);
        loadEarnings();
        toast.success("Earning deleted successfully!");
      } catch (error) {
        console.error("Error deleting earning:", error);
        toast.error("Error deleting earning. Please try again.");
      }
    }
  };

  const handleUpload = async (formData) => {
    try {
      await uploadEarnings(formData);
      toast.success("Earnings uploaded successfully!");
      loadEarnings();
    } catch (error) {
      console.error("Error uploading earnings:", error);
      toast.error("Error uploading earnings. Please try again.");
    }
  };

  const loadEarnings = () => {
    getEarnings()
      .then((data) => setEarnings(data))
      .catch((err) => console.error("Error fetching earnings:", err));
  };

  return (
    <div>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "20px",
          marginTop: "40px",
          padding: "0 20px",
        }}
      >
        <h1 style={{ margin: 0, color: "#212529" }}>Earnings</h1>
        <div style={{ display: "flex", gap: "10px" }}>
          <button
            onClick={() => navigate("/earningtypes")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#28a745",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              fontSize: "14px",
              lineHeight: "1",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              transition: "background-color 0.3s ease",
            }}
            onMouseOver={(e) => (e.target.style.backgroundColor = "#218838")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#28a745")}
          >
            Earning Types
          </button>
          <button
            onClick={() => navigate("/earningcategories")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#28a745",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              fontSize: "14px",
              lineHeight: "1",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              transition: "background-color 0.3s ease",
            }}
            onMouseOver={(e) => (e.target.style.backgroundColor = "#218838")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#28a745")}
          >
            Earning Categories
          </button>
          <button
            onClick={() => navigate("/earningsummary")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#28a745",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              fontSize: "14px",
              lineHeight: "1",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              transition: "background-color 0.3s ease",
            }}
            onMouseOver={(e) => (e.target.style.backgroundColor = "#218838")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#28a745")}
          >
            Earning Summary
          </button>
          <button
            onClick={() => setIsUploadModalOpen(true)}
            style={{
              padding: "10px 16px",
              backgroundColor: "#17a2b8",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              fontSize: "14px",
              lineHeight: "1",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            Upload CSV
          </button>
          <button
            onClick={() => {
              setEditingEarning(null);
              setIsModalOpen(true);
            }}
            style={{
              padding: "10px 16px",
              backgroundColor: "#28a745",
              color: "white",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
              fontSize: "14px",
              lineHeight: "1",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            Create Earning
          </button>
        </div>
      </div>
      <EarningTable
        earnings={earnings}
        onDelete={handleDeleteEarning}
        onEdit={handleEditEarning}
      />
      <EditEarningModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingEarning(null);
        }}
        onSubmit={handleSubmitEarning}
        initialData={editingEarning}
      />
      <UploadEarningModal
        isOpen={isUploadModalOpen}
        onClose={() => setIsUploadModalOpen(false)}
        onUpload={handleUpload}
      />
    </div>
  );
}

export default EarningPage;
