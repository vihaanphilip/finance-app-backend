import React, { useEffect, useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getAccounts, createAccount } from "../api/AccountApi";
import AccountTable from "../components/tables/AccountTable";
import AddAccountModal from "../components/forms/AddAccountModal";

function AccountPage() {
  const [accounts, setAccounts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    getAccounts()
      .then((data) => setAccounts(data))
      .catch((err) => console.error("Error fetching accounts", err));
  }, []);

  const handleAddAccount = async (accountData) => {
    try {
      const newAccount = await createAccount(accountData);
      console.log("Account created:", newAccount);

      // Show success toast
      toast.success("Account created successfully!");

      // Refresh the accounts list
      const updatedAccounts = await getAccounts();
      setAccounts(updatedAccounts);
      setIsModalOpen(false);
    } catch (error) {
      console.error("Error creating account:", error);
      toast.error("Error creating account. Please try again.");
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
          marginTop: "20px", // Added top margin
          padding: "0 20px",
        }}
      >
        <h1 style={{ margin: 0, color: "#212529" }}>Accounts</h1>
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
          Create Account
        </button>
      </div>

      <AccountTable accounts={accounts} />

      <AddAccountModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleAddAccount}
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

export default AccountPage;
