import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/expensecategories`;

export const getExpenseCategories = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createExpenseCategory = async (expenseCategory) => {
  const response = await axios.post(API_URL, expenseCategory);
  return response.data;
};

export const updateExpenseCategory = async (id, expenseCategory) => {
  const response = await axios.put(`${API_URL}/${id}`, expenseCategory);
  return response.data;
};

export const deleteExpenseCategory = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};
