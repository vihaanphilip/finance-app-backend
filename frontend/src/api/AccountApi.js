import axios from "axios";
import config from "../config/api.js";

const API_URL = `${config.API_BASE_URL}/api/accounts`;

export const getAccounts = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};