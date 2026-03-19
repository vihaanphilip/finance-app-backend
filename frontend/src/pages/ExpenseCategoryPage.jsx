import React, { useEffect, useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  getExpenseCategories,
  createExpenseCategory,
} from "../api/ExpenseCategoryApi";
import ExpenseCategoryTable from "../components/tables/ExpenseCategoryTable";
import EditExpenseCategoryModal from "../components/forms/EditExpenseCategoryModal";

function ExpenseCategoryPage() {
  const [expenseCategories, setExpenseCategories] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    getExpenseCategories()
      .then((data) => setExpenseCategories(data))
      .catch((err) => console.error("Error fetching expense categories", err));
  }, []);

  const handleAddExpenseCategory = async (categoryData) => {
    try {
      const newCategory = await createExpenseCategory(categoryData);
      console.log("Expense category created:", newCategory);

      toast.success("Expense category created successfully!");

      const updatedCategories = await getExpenseCategories();
      setExpenseCategories(updatedCategories);
      setIsModalOpen(false);
    } catch (error) {
      console.error("Error creating expense category:", error);
      toast.error("Error creating expense category. Please try again.");
    }
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
        <h1 style={{ margin: 0, color: "#212529" }}>Expense Categories</h1>
        <button
          onClick={() => setIsModalOpen(true)}
          style={{
            backgroundColor: "#dc3545",
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
          onMouseOver={(e) => (e.target.style.backgroundColor = "#c82333")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#dc3545")}
        >
          Create Expense Category
        </button>
      </div>

      <ExpenseCategoryTable expenseCategories={expenseCategories} />

      <EditExpenseCategoryModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleAddExpenseCategory}
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

export default ExpenseCategoryPage;
