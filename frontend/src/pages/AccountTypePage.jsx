import React, { useEffect, useState } from "react";
import { getAccountTypes } from "../api/AccountTypeApi";
import AccountTypeTable from "../components/tables/AccountTypeTable";

function AccountTypePage() {
  const [accountTypes, setAccountTypes] = useState([]);

  useEffect(() => {
    getAccountTypes()
      .then((data) => setAccountTypes(data))
      .catch((err) => console.error("Error fetching account types", err));
  }, []);

  return (
    <div>
      <AccountTypeTable accountTypes={accountTypes} />
    </div>
  );
}

export default AccountTypePage;
