import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/accounts`;

export const getAccounts = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const createAccount = async (accountData) => {
  const response = await axios.post(API_URL, accountData);
  return response.data;
};

export const updateAccount = async (id, accountData) => {
  const response = await axios.post(`${API_URL}/${id}`, accountData);
  return response.data;
};