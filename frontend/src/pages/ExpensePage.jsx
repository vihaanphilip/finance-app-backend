import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  getExpenses,
  createExpense,
  updateExpense,
  deleteExpense,
  uploadExpenses,
} from "../api/ExpenseApi";
import ExpenseTable from "../components/tables/ExpenseTable";
import EditExpenseModal from "../components/forms/EditExpenseModal";

function ExpensePage() {
  const [expenses, setExpenses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingExpense, setEditingExpense] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchExpenses();
  }, []);

  const fetchExpenses = async () => {
    try {
      const data = await getExpenses();
      setExpenses(data);
    } catch (error) {
      console.error("Failed to fetch expenses:", error);
      toast.error("Failed to fetch expenses");
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteExpense = async (id) => {
    if (window.confirm("Are you sure you want to delete this expense?")) {
      try {
        await deleteExpense(id);
        toast.success("Expense deleted successfully!");
        fetchExpenses();
      } catch (error) {
        console.error("Error deleting expense:", error);
        toast.error("Error deleting expense. Please try again.");
      }
    }
  };

  const handleSubmitExpense = async (expenseData) => {
    try {
      if (editingExpense) {
        await updateExpense(editingExpense.id, expenseData);
        toast.success("Expense updated successfully!");
      } else {
        await createExpense(expenseData);
        toast.success("Expense added successfully!");
      }
      setEditingExpense(null);
      fetchExpenses();
    } catch (error) {
      console.error("Error saving expense:", error);
      toast.error("Error saving expense. Please try again.");
    }
  };

  const handleEditExpense = (expense) => {
    setEditingExpense(expense);
    setIsModalOpen(true);
  };

  if (loading) return <div>Loading...</div>;

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
        <h1 style={{ margin: 0, color: "#212529" }}>Expenses</h1>
        <div style={{ display: "flex", gap: "10px" }}>
          <button
            onClick={() => navigate("/expensetypes")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#dc3545",
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
            onMouseOver={(e) => (e.target.style.backgroundColor = "#c82333")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#dc3545")}
          >
            Expense Types
          </button>
          <button
            onClick={() => navigate("/expensecategories")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#dc3545",
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
            onMouseOver={(e) => (e.target.style.backgroundColor = "#c82333")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#dc3545")}
          >
            Expense Categories
          </button>
          <button
            onClick={() => navigate("/expensesummary")}
            style={{
              padding: "10px 16px",
              backgroundColor: "#dc3545",
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
            onMouseOver={(e) => (e.target.style.backgroundColor = "#c82333")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#dc3545")}
          >
            Expense Summary
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
              setEditingExpense(null);
              setIsModalOpen(true);
            }}
            style={{
              padding: "10px 16px",
              backgroundColor: "#dc3545",
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
            Create Expense
          </button>
        </div>
      </div>

      <ExpenseTable
        expenses={expenses}
        onDelete={handleDeleteExpense}
        onEdit={handleEditExpense}
      />

      <EditExpenseModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingExpense(null);
        }}
        onSubmit={handleSubmitExpense}
        initialData={editingExpense}
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

export default ExpensePage;
