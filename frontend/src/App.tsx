import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import Login from "./pages/Login";
import RegisterEmployee from "./pages/RegisterEmployee";

function OrdersPage() {
  return (
    <div className="container py-5">
      <h1>Encomendas</h1>
      <p>Página em construção.</p>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/orders" element={<OrdersPage />} />
        <Route path="/register-employee" element={<RegisterEmployee />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
