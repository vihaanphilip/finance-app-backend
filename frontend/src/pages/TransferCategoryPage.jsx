import React, { useEffect, useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  getTransferCategories,
  createTransferCategory,
  updateTransferCategory,
  deleteTransferCategory,
} from "../api/TransferCategoryApi";
import TransferCategoryTable from "../components/tables/TransferCategoryTable";
import EditTransferCategoryModal from "../components/forms/EditTransferCategoryModal";

function TransferCategoryPage() {
  const [transferCategories, setTransferCategories] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingTransferCategory, setEditingTransferCategory] = useState(null);

  useEffect(() => {
    getTransferCategories()
      .then((data) => setTransferCategories(data))
      .catch((err) => console.error("Error fetching transfer categories", err));
  }, []);

  const handleDeleteTransferCategory = async (id) => {
    try {
      await deleteTransferCategory(id);
      toast.success("Transfer category deleted successfully!");
      const updatedCategories = await getTransferCategories();
      setTransferCategories(updatedCategories);
    } catch (error) {
      console.error("Error deleting transfer category:", error);
      toast.error("Error deleting transfer category. Please try again.");
    }
  };

  const handleSubmitTransferCategory = async (categoryData) => {
    try {
      if (editingTransferCategory) {
        const updatedCategory = await updateTransferCategory(
          categoryData.id,
          categoryData,
        );
        console.log("Transfer category updated:", updatedCategory);
        toast.success("Transfer category updated successfully!");
      } else {
        const newCategory = await createTransferCategory(categoryData);
        console.log("Transfer category created:", newCategory);
        toast.success("Transfer category created successfully!");
      }

      const updatedCategories = await getTransferCategories();
      setTransferCategories(updatedCategories);
      setEditingTransferCategory(null);
      setIsModalOpen(false);
    } catch (error) {
      console.error("Error saving transfer category:", error);
      toast.error("Error saving transfer category. Please try again.");
    }
  };

  const handleEditTransferCategory = (category) => {
    setEditingTransferCategory(category);
    setIsModalOpen(true);
  };

  return (
    <div>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "20px",
          marginTop: "20px",
          padding: "0 20px",
        }}
      >
        <h1 style={{ margin: 0, color: "#212529" }}>Transfer Categories</h1>
        <button
          onClick={() => {
            setEditingTransferCategory(null);
            setIsModalOpen(true);
          }}
          style={{
            backgroundColor: "#ffc107",
            color: "white",
            border: "none",
            borderRadius: "4px",
            padding: "10px 16px",
            fontSize: "14px",
            cursor: "pointer",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
            transition: "background-color 0.3s ease",
          }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#e0a800")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#ffc107")}
        >
          Create Transfer Category
        </button>
      </div>

      <TransferCategoryTable
        transferCategories={transferCategories}
        onDelete={handleDeleteTransferCategory}
        onEdit={handleEditTransferCategory}
      />

      <EditTransferCategoryModal
        isOpen={isModalOpen}
        onClose={() => {
          setEditingTransferCategory(null);
          setIsModalOpen(false);
        }}
        onSubmit={handleSubmitTransferCategory}
        initialData={editingTransferCategory}
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

export default TransferCategoryPage;
