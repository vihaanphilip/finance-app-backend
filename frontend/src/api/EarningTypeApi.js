import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/v1/earningtypes`;

export const getEarningTypes = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createEarningType = async (earningType) => {
  const response = await axios.post(API_URL, earningType);
  return response.data;
};

export const updateEarningType = async (id, earningType) => {
  const response = await axios.post(`${API_URL}/${id}`, earningType);
  return response.data;
};

export const deleteEarningType = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};
