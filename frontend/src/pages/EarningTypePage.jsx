import React, { useEffect, useState } from "react";
import {
  getEarningTypes,
  createEarningType,
  deleteEarningType,
  updateEarningType,
} from "../api/EarningTypeApi";
import EarningTypeTable from "../components/tables/EarningTypeTable";
import AddEarningTypeModal from "../components/forms/EditEarningTypeModal";
import { toast } from "react-toastify";

function EarningTypePage() {
  const [earningTypes, setEarningTypes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingEarningType, setEditingEarningType] = useState(null);

  const fetchEarningTypes = async () => {
    try {
      const data = await getEarningTypes();
      setEarningTypes(data);
    } catch (error) {
      console.error("Failed to fetch earning types:", error);
      toast.error("Failed to fetch earning types");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEarningTypes();
  }, []);

  const handleSubmitEarningType = async (formData) => {
    try {
      if (editingEarningType) {
        // Update existing earning type
        await updateEarningType(editingEarningType.id, formData);
        toast.success("Earning type updated successfully!");
      } else {
        // Create new earning type
        await createEarningType(formData);
        toast.success("Earning type created successfully!");
      }
      setIsModalOpen(false);
      setEditingEarningType(null);
      fetchEarningTypes(); // Refresh the list
    } catch (error) {
      console.error("Failed to save earning type:", error);
      toast.error("Failed to save earning type");
    }
  };

  const handleEditEarningType = (earningType) => {
    setEditingEarningType(earningType);
    setIsModalOpen(true);
  };

  const handleDeleteEarningType = async (id) => {
    if (window.confirm("Are you sure you want to delete this earning type?")) {
      try {
        await deleteEarningType(id);
        toast.success("Earning type deleted successfully!");
        fetchEarningTypes();
      } catch (error) {
        console.error("Error deleting earning type:", error);
        toast.error("Error deleting earning type. Please try again.");
      }
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div style={{ padding: "20px" }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "20px",
        }}
      >
        <h1>Earning Types</h1>
        <button
          onClick={() => {
            setEditingEarningType(null);
            setIsModalOpen(true);
          }}
          style={{
            padding: "10px 20px",
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
            fontSize: "14px",
          }}
        >
          Add Earning Type
        </button>
      </div>

      <EarningTypeTable
        earningTypes={earningTypes}
        onDelete={handleDeleteEarningType}
        onEdit={handleEditEarningType}
      />

      <AddEarningTypeModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingEarningType(null);
        }}
        onSubmit={handleSubmitEarningType}
        initialData={editingEarningType}
      />
    </div>
  );
}

export default EarningTypePage;
