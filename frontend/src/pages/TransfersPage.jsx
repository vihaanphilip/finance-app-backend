import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  getTransfers,
  createTransfer,
  updateTransfer,
  deleteTransfer,
} from "../api/TransferApi";
import TransferTable from "../components/tables/TransferTable";
import EditTransferModal from "../components/forms/EditTransferModal";

function TransfersPage() {
  const [transfers, setTransfers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingTransfer, setEditingTransfer] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchTransfers();
  }, []);

  const fetchTransfers = async () => {
    try {
      const data = await getTransfers();
      setTransfers(data);
    } catch (error) {
      console.error("Failed to fetch transfers:", error);
      toast.error("Failed to fetch transfers");
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteTransfer = async (id) => {
    if (window.confirm("Are you sure you want to delete this transfer?")) {
      try {
        await deleteTransfer(id);
        toast.success("Transfer deleted successfully!");
        fetchTransfers();
      } catch (error) {
        console.error("Error deleting transfer:", error);
        toast.error("Error deleting transfer. Please try again.");
      }
    }
  };

  const handleSubmitTransfer = async (transferData) => {
    try {
      if (editingTransfer) {
        await updateTransfer(editingTransfer.id, transferData);
        toast.success("Transfer updated successfully!");
      } else {
        await createTransfer(transferData);
        toast.success("Transfer added successfully!");
      }

      setEditingTransfer(null);
      fetchTransfers();
    } catch (error) {
      console.error("Error saving transfer:", error);
      toast.error("Error saving transfer. Please try again.");
    }
  };

  const handleEditTransfer = (transfer) => {
    setEditingTransfer(transfer);
    setIsModalOpen(true);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

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
        <h1 style={{ margin: 0, color: "#212529" }}>Transfers</h1>
        <div style={{ display: "flex", gap: "10px" }}>
          <button
            onClick={() => navigate("/transfertypes")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#ffc107",
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
            onMouseOver={(e) => (e.target.style.backgroundColor = "#e0a800")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#ffc107")}
          >
            Transfer Types
          </button>
          <button
            onClick={() => navigate("/transfercategories")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#ffc107",
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
            onMouseOver={(e) => (e.target.style.backgroundColor = "#e0a800")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#ffc107")}
          >
            Transfer Categories
          </button>
          <button
            onClick={() => toast.info("Upload CSV not yet implemented")}
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
              setEditingTransfer(null);
              setIsModalOpen(true);
            }}
            style={{
              padding: "10px 16px",
              backgroundColor: "#ffc107",
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
            Create Transfer
          </button>
        </div>
      </div>

      <TransferTable
        transfers={transfers}
        onDelete={handleDeleteTransfer}
        onEdit={handleEditTransfer}
      />

      <EditTransferModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingTransfer(null);
        }}
        onSubmit={handleSubmitTransfer}
        initialData={editingTransfer}
      />

      <ToastContainer
        position="top-right"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </div>
  );
}

export default TransfersPage;
