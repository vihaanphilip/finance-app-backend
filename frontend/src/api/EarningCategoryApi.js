import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/earningcategories`;

export const getEarningCategories = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createEarningCategory = async (earningCategory) => {
  const response = await axios.post(API_URL, earningCategory);
  return response.data;
};

export const updateEarningCategory = async (id, earningCategory) => {
  const response = await axios.put(`${API_URL}/${id}`, earningCategory);
  return response.data;
};

export const deleteEarningCategory = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};
