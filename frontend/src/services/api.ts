import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export interface EmployeeRequestDTO {
  nome: string;
  username: string;
  senha: string;
  role: "Admin" | "Employee";
}

export function registerEmployee(data: EmployeeRequestDTO) {
  return api.post("/employees", data);
}

export default api;
