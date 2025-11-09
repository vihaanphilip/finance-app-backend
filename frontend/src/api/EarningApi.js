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
