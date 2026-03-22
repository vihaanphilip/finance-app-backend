import React, { useEffect, useState } from "react";
import { getTransferTypes } from "../api/TransferTypeApi";
import TransferTypeTable from "../components/tables/TransferTypeTable";
import { toast } from "react-toastify";

function TransferTypePage() {
  const [transferTypes, setTransferTypes] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchTransferTypes = async () => {
    try {
      const data = await getTransferTypes();
      setTransferTypes(data);
    } catch (error) {
      console.error("Failed to fetch transfer types:", error);
      toast.error("Failed to fetch transfer types");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTransferTypes();
  }, []);

  if (loading) return <div>Loading...</div>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>Transfer Types</h1>
      <TransferTypeTable transferTypes={transferTypes} />
    </div>
  );
}

export default TransferTypePage;
