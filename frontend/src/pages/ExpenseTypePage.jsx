import React, { useEffect, useState } from "react";
import {
  getExpenseTypes,
  createExpenseType,
  deleteExpenseType,
  updateExpenseType,
} from "../api/ExpenseTypeApi";
import ExpenseTypeTable from "../components/tables/ExpenseTypeTable";
import EditExpenseTypeModal from "../components/forms/EditExpenseTypeModal";
import { toast } from "react-toastify";

function ExpenseTypePage() {
  const [expenseTypes, setExpenseTypes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingExpenseType, setEditingExpenseType] = useState(null);

  const fetchExpenseTypes = async () => {
    try {
      const data = await getExpenseTypes();
      setExpenseTypes(data);
    } catch (error) {
      console.error("Failed to fetch expense types:", error);
      toast.error("Failed to fetch expense types");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchExpenseTypes();
  }, []);

  const handleSubmitExpenseType = async (formData) => {
    try {
      if (editingExpenseType) {
        await updateExpenseType(editingExpenseType.id, formData);
        toast.success("Expense type updated successfully!");
      } else {
        await createExpenseType(formData);
        toast.success("Expense type created successfully!");
      }
      setIsModalOpen(false);
      setEditingExpenseType(null);
      fetchExpenseTypes();
    } catch (error) {
      console.error("Failed to save expense type:", error);
      toast.error("Failed to save expense type");
    }
  };

  const handleEditExpenseType = (expenseType) => {
    setEditingExpenseType(expenseType);
    setIsModalOpen(true);
  };

  const handleDeleteExpenseType = async (id) => {
    if (window.confirm("Are you sure you want to delete this expense type?")) {
      try {
        await deleteExpenseType(id);
        toast.success("Expense type deleted successfully!");
        fetchExpenseTypes();
      } catch (error) {
        console.error("Error deleting expense type:", error);
        toast.error("Error deleting expense type. Please try again.");
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
        <h1>Expense Types</h1>
        <button
          onClick={() => {
            setEditingExpenseType(null);
            setIsModalOpen(true);
          }}
          style={{
            padding: "10px 20px",
            backgroundColor: "#dc3545",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
            fontSize: "14px",
          }}
        >
          Add Expense Type
        </button>
      </div>

      <ExpenseTypeTable
        expenseTypes={expenseTypes}
        onDelete={handleDeleteExpenseType}
        onEdit={handleEditExpenseType}
      />

      <EditExpenseTypeModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingExpenseType(null);
        }}
        onSubmit={handleSubmitExpenseType}
        initialData={editingExpenseType}
      />
    </div>
  );
}

export default ExpenseTypePage;
