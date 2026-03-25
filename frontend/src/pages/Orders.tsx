import { Link } from "react-router-dom";

export default function OrdersPage() {

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <h1 className="text-center mb-4">Pedidos</h1>
          <div className="d-grid gap-3">
          <Link to="/pedidos/novo" className="btn btn-primary btn-lg">
              Criar Pedido
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
