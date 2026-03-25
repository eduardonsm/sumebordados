import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <h1 className="text-center mb-4">Painel</h1>
          <p className="text-center text-muted mb-4">
            Escolha uma ação para continuar.
          </p>
          <div className="d-grid gap-3">
            <Link
              to="/cadastrar-funcionario"
              className="btn btn-primary btn-lg"
            >
              Cadastrar Funcionário
            </Link>
            <Link to="/pedidos/novo" className="btn btn-primary btn-lg">
              Criar Pedido
            </Link>
            <Link to="/pedidos" className="btn btn-primary btn-lg">
              Ver Pedidos
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
