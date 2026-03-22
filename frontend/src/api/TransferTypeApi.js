import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/v1/transfertypes`;

export const getTransferTypes = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createTransferType = async (transferType) => {
  const response = await axios.post(API_URL, transferType);
  return response.data;
};

export const updateTransferType = async (id, transferType) => {
  const response = await axios.post(`${API_URL}/${id}`, transferType);
  return response.data;
};

export const deleteTransferType = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};
