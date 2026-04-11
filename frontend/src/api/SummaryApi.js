import axios from "axios";
import config from "../config/api.js";

const SUMMARY_API_URL = `${config.API_BASE_URL}/api/v1/summary`;

export const getAccountSummary = async (startDate, endDate) => {
  const response = await axios.get(`${SUMMARY_API_URL}/accounts`, {
    params: {
      start_date: startDate,
      end_date: endDate,
    },
  });

  return response.data;
};
