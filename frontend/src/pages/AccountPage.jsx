import React, { useEffect, useState } from "react";
import { getAccounts } from "../api/AccountApi";
import AccountTable from "../components/AccountTable";

function AccountPage() {
  const [accounts, setAccounts] = useState([]);

  useEffect(() => {
    getAccounts()
      .then((data) => setAccounts(data))
      .catch((err) => console.error("Error fetching accounts", err));
  }, []);

  return (
    <div>
      <AccountTable accounts={accounts} />
    </div>
  );
}

export default AccountPage;