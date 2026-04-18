import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getAccounts, createAccount, updateAccount } from "../api/AccountApi";
import AccountTable from "../components/tables/AccountTable";
import EditAccountModal from "../components/forms/EditAccountModal";

function AccountPage() {
  const [accounts, setAccounts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingAccount, setEditingAccount] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = () => {
    getAccounts()
      .then((data) => setAccounts(data))
      .catch((err) => console.error("Error fetching accounts", err));
  };

  const handleSubmitAccount = async (accountData) => {
    try {
      if (editingAccount) {
        await updateAccount(editingAccount.id, accountData);
        toast.success("Account updated successfully!");
      } else {
        const newAccount = await createAccount(accountData);
        console.log("Account created:", newAccount);
        toast.success("Account created successfully!");
      }

      loadAccounts();
      setIsModalOpen(false);
      setEditingAccount(null);
    } catch (error) {
      console.error("Error saving account:", error);
      toast.error("Error saving account. Please try again.");
    }
  };

  const handleEditAccount = (account) => {
    setEditingAccount(account);
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
          marginTop: "20px", // Added top margin
          padding: "0 20px",
        }}
      >
        <h1 style={{ margin: 0, color: "#212529" }}>Accounts</h1>
        <div style={{ display: "flex", gap: "10px" }}>
          <button
            onClick={() => navigate("/accounttypes")}
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
            View Account Types
          </button>
          <button
            onClick={() => {
              setEditingAccount(null);
              setIsModalOpen(true);
            }}
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
      </div>

      <AccountTable accounts={accounts} onEdit={handleEditAccount} />

      <EditAccountModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingAccount(null);
        }}
        onSubmit={handleSubmitAccount}
        initialData={editingAccount}
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
