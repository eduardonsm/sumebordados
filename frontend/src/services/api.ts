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

export type OrderStatus = "Aguardando Adiantamento" | "Atrasado" | "Em Produção" | "Cancelado" | "Finalizado" | "Aguardando Arte";

export interface OrderSizeRequest {
  base_size: string;
  variant: string;
  quantity: number;
}

export interface OrderRequest {
  customerId: number;
  model: string;
  fabric: string;
  has_cut: boolean;
  quantity: number;
  chest_customization: number;
  back_customization: number;
  sleeve_customization: number;
  unit_price: number;
  total_price: number;
  delivery_date: string;
  advance_date: string;
  advance_amount: number;
  remaining_amount: number;
  status: OrderStatus | string;
  colors: string[];
  sizes: OrderSizeRequest[];
}

export function createOrder(orderData: OrderRequest, artworkFile?: File) {
  const formData = new FormData();
  formData.append(
    "order",
    new Blob([JSON.stringify(orderData)], { type: "application/json" })
  );
  if (artworkFile) {
    formData.append("artwork", artworkFile);
  }

  return api.post("/orders", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
}

export default api;
