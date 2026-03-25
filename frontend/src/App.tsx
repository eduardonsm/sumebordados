import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import type { ReactNode } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Login from "./pages/Login";
import Home from "./pages/Home";
import RegisterEmployee from "./pages/RegisterEmployee";
import CreateOrder from "./pages/CreateOrder";

function OrdersPage() {
  return (
    <div className="container py-5">
      <h1>Encomendas</h1>
      <p>Página em construção.</p>
    </div>
  );
}

function ProtectedRoute({ children }: { children: ReactNode }) {
  if (!localStorage.getItem("token")) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Home />
            </ProtectedRoute>
          }
        />
        <Route
          path="/home"
          element={
            <ProtectedRoute>
              <Home />
            </ProtectedRoute>
          }
        />
        <Route path="/login" element={<Login />} />
        <Route path="/pedidos" element={<OrdersPage />} />
        <Route path="/pedidos/novo" element={<CreateOrder />} />
        <Route path="/cadastrar-funcionario" element={<RegisterEmployee />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
