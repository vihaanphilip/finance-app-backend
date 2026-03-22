import React, { useEffect, useState } from "react";
import {
  getTransferTypes,
  createTransferType,
  deleteTransferType,
  updateTransferType,
} from "../api/TransferTypeApi";
import TransferTypeTable from "../components/tables/TransferTypeTable";
import EditTransferTypeModal from "../components/forms/EditTransferTypeModal";
import { toast } from "react-toastify";

function TransferTypePage() {
  const [transferTypes, setTransferTypes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingTransferType, setEditingTransferType] = useState(null);

  const fetchTransferTypes = async () => {
    try {
      const data = await getTransferTypes();
      setTransferTypes(data);
    } catch (error) {
      console.error("Failed to fetch transfer types:", error);
      toast.error("Failed to fetch transfer types");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTransferTypes();
  }, []);

  const handleSubmitTransferType = async (formData) => {
    try {
      if (editingTransferType) {
        await updateTransferType(editingTransferType.id, formData);
        toast.success("Transfer type updated successfully!");
      } else {
        await createTransferType(formData);
        toast.success("Transfer type created successfully!");
      }
      setIsModalOpen(false);
      setEditingTransferType(null);
      fetchTransferTypes();
    } catch (error) {
      console.error("Failed to save transfer type:", error);
      toast.error("Failed to save transfer type");
    }
  };

  const handleEditTransferType = (transferType) => {
    setEditingTransferType(transferType);
    setIsModalOpen(true);
  };

  const handleDeleteTransferType = async (id) => {
    if (window.confirm("Are you sure you want to delete this transfer type?")) {
      try {
        await deleteTransferType(id);
        toast.success("Transfer type deleted successfully!");
        fetchTransferTypes();
      } catch (error) {
        console.error("Error deleting transfer type:", error);
        toast.error("Error deleting transfer type. Please try again.");
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
        <h1>Transfer Types</h1>
        <button
          onClick={() => {
            setEditingTransferType(null);
            setIsModalOpen(true);
          }}
          style={{
            padding: "10px 20px",
            backgroundColor: "#ffc107",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
            fontSize: "14px",
            transition: "background-color 0.3s ease",
          }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#e0a800")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#ffc107")}
        >
          Add Transfer Type
        </button>
      </div>

      <TransferTypeTable
        transferTypes={transferTypes}
        onDelete={handleDeleteTransferType}
        onEdit={handleEditTransferType}
      />

      <EditTransferTypeModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingTransferType(null);
        }}
        onSubmit={handleSubmitTransferType}
        initialData={editingTransferType}
      />
    </div>
  );
}

export default TransferTypePage;
