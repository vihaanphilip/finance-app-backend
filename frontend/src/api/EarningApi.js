import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/v1/earnings`;

export const getEarnings = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createEarning = async (earningData) => {
  const response = await axios.post(API_URL, earningData);
  return response.data;
};

export const updateEarning = async (id, earningData) => {
  const response = await axios.post(`${API_URL}/${id}`, earningData);
  return response.data;
};

export const deleteEarning = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};

export const uploadEarnings = async (formData) => {
  const response = await axios.post(`${API_URL}/upload`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
};

/**
 * Get earning summary for a specific month
 * @param {number} year - The year (e.g., 2025)
 * @param {number} month - The month (1-12)
 * @returns {Promise} - Promise with earning summary data
 */
export const getEarningSummary = async (year, month) => {
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
    console.error("Error fetching earning summary:", error);
    throw error;
  }
};
