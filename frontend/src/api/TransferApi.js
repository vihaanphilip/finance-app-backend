import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/v1/transfers`;

export const getTransfers = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createTransfer = async (transferData) => {
  const response = await axios.post(API_URL, transferData);
  return response.data;
};

export const updateTransfer = async (id, transferData) => {
  const response = await axios.post(`${API_URL}/${id}`, transferData);
  return response.data;
};

export const deleteTransfer = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};

export const uploadTransfers = async (formData) => {
  const response = await axios.post(`${API_URL}/upload`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
};