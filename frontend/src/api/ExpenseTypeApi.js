import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/v1/expensetypes`;

export const getExpenseTypes = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createExpenseType = async (expenseType) => {
  const response = await axios.post(API_URL, expenseType);
  return response.data;
};

export const updateExpenseType = async (id, expenseType) => {
  const response = await axios.post(`${API_URL}/${id}`, expenseType);
  return response.data;
};

export const deleteExpenseType = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};
