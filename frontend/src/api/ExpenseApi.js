import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/v1/expenses`;

export const getExpenses = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createExpense = async (expenseData) => {
  const response = await axios.post(API_URL, expenseData);
  return response.data;
};

export const updateExpense = async (id, expenseData) => {
  const response = await axios.post(`${API_URL}/${id}`, expenseData);
  return response.data;
};

export const deleteExpense = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};

export const uploadExpenses = async (formData) => {
  const response = await axios.post(`${API_URL}/upload`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
};

/**
 * Get expense summary for a specific month
 * @param {number} year - The year (e.g., 2025)
 * @param {number} month - The month (1-12)
 * @returns {Promise} - Promise with expense summary data
 */
export const getExpenseSummary = async (year, month) => {
  try {
    // Format date as YYYY-MM-01
    const date = `${year}-${String(month).padStart(2, "0")}-01`;

    const response = await axios.get(`${API_URL}/summary`, {
      params: {
        date: date,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching expense summary:", error);
    throw error;
  }
};
