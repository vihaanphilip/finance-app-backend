import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/v1/transfercategories`;

export const getTransferCategories = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createTransferCategory = async (transferCategory) => {
  const response = await axios.post(API_URL, transferCategory);
  return response.data;
};

export const updateTransferCategory = async (id, transferCategory) => {
  const response = await axios.put(`${API_URL}/${id}`, transferCategory);
  return response.data;
};

export const deleteTransferCategory = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};
