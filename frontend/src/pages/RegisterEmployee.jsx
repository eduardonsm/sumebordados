import { useState } from "react";
import { registerEmployee } from "../services/api";

export default function RegisterEmployee() {
  const [nome, setNome] = useState("");
  const [username, setUsername] = useState("");
  const [senha, setSenha] = useState("");
  const [role, setRole] = useState("Employee");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");
    setLoading(true);

    try {
      await registerEmployee({ nome, username, senha, role });
      setSuccess("Funcionário cadastrado com sucesso!");
      setNome("");
      setUsername("");
      setSenha("");
      setRole("Employee");
    } catch (err) {
      if (err.response?.status === 403) {
        setError("Não tem permissão para cadastrar funcionários. Apenas administradores.");
      } else if (err.response?.status === 400 || err.response?.status === 422) {
        setError(
          err.response?.data?.message || "Dados inválidos. Verifique os campos e tente novamente."
        );
      } else {
        setError(
          err.response?.data?.message || "Ocorreu um erro. Tente novamente."
        );
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-5">
          <h1 className="text-center mb-4">Cadastrar Funcionário</h1>
          <form onSubmit={handleSubmit}>
            {error && (
              <div className="alert alert-danger" role="alert">
                {error}
              </div>
            )}
            {success && (
              <div className="alert alert-success" role="alert">
                {success}
              </div>
            )}
            <div className="mb-3">
              <label htmlFor="nome" className="form-label">
                Nome
              </label>
              <input
                type="text"
                className="form-control"
                id="nome"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="username" className="form-label">
                Username
              </label>
              <input
                type="text"
                className="form-control"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
                autoComplete="username"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="senha" className="form-label">
                Senha
              </label>
              <input
                type="password"
                className="form-control"
                id="senha"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
                autoComplete="new-password"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="role" className="form-label">
                Função
              </label>
              <select
                className="form-select"
                id="role"
                value={role}
                onChange={(e) => setRole(e.target.value)}
              >
                <option value="Employee">Funcionário</option>
                <option value="Admin">Administrador</option>
              </select>
            </div>
            <button
              type="submit"
              className="btn btn-primary w-100"
              disabled={loading}
            >
              {loading ? "Carregando..." : "Cadastrar"}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
