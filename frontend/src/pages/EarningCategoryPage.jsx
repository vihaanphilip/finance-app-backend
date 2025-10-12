import React, { useEffect, useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  getEarningCategories,
  createEarningCategory,
} from "../api/EarningCategoryApi";
import EarningCategoryTable from "../components/EarningCategoryTable";
import AddEarningCategoryModal from "../components/AddEarningCategoryModal";

console.log("✅ EarningCategoryPage mounted");

function EarningCategoryPage() {
  const [earningCategories, setEarningCategories] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    getEarningCategories()
      .then((data) => setEarningCategories(data))
      .catch((err) => console.error("Error fetching earning categories", err));
  }, []);

  const handleAddEarningCategory = async (categoryData) => {
    try {
      const newCategory = await createEarningCategory(categoryData);
      console.log("Earning category created:", newCategory);

      // Show success toast
      toast.success("Earning category created successfully!");

      // Refresh the earning categories list
      const updatedCategories = await getEarningCategories();
      setEarningCategories(updatedCategories);
      setIsModalOpen(false);
    } catch (error) {
      console.error("Error creating earning category:", error);
      toast.error("Error creating earning category. Please try again.");
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
        <h1 style={{ margin: 0, color: "#212529" }}>Earning Categories</h1>
        <button
          onClick={() => setIsModalOpen(true)}
          style={{
            backgroundColor: "#007bff",
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
          onMouseOver={(e) => (e.target.style.backgroundColor = "#0056b3")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#007bff")}
        >
          Create Earning Category
        </button>
      </div>

      <EarningCategoryTable earningCategories={earningCategories} />

      <AddEarningCategoryModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleAddEarningCategory}
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

export default EarningCategoryPage;
