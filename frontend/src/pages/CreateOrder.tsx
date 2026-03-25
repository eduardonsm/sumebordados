import { useEffect, useMemo, useState, FormEvent } from "react";
import { AxiosError } from "axios";
import { createOrder, OrderRequest, OrderSizeRequest } from "../services/api";

function emptySize(): OrderSizeRequest {
  return { base_size: "", variant: "", quantity: 1 };
}

function initialOrder(): OrderRequest {
  return {
    customerId: 0,
    model: "",
    fabric: "",
    has_cut: false,
    quantity: 1,
    chest_customization: 0,
    back_customization: 0,
    sleeve_customization: 0,
    unit_price: 0,
    total_price: 0,
    delivery_date: "",
    advance_date: "",
    advance_amount: 0,
    remaining_amount: 0,
    status: "Aguardando Adiantamento",
    colors: [],
    sizes: [emptySize()],
  };
}

export default function CreateOrder() {
  const [order, setOrder] = useState<OrderRequest>(() => initialOrder());
  const [colorsText, setColorsText] = useState("");
  const [artworkFile, setArtworkFile] = useState<File | undefined>(undefined);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  const previewUrl = useMemo(() => {
    if (!artworkFile) return "";
    return URL.createObjectURL(artworkFile);
  }, [artworkFile]);

  useEffect(() => {
    return () => {
      if (previewUrl) URL.revokeObjectURL(previewUrl);
    };
  }, [previewUrl]);

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");
    setSuccess("");
    setLoading(true);

    const normalizedColors = colorsText
      .split(",")
      .map((c) => c.trim())
      .filter(Boolean);

    const payload: OrderRequest = {
      ...order,
      colors: normalizedColors,
      sizes: order.sizes.filter(
        (s) => s.base_size && s.variant && Number(s.quantity) > 0
      ),
    };

    try {
      await createOrder(payload, artworkFile);
      setSuccess("Encomenda cadastrada com sucesso!");
      setOrder(initialOrder());
      setColorsText("");
      setArtworkFile(undefined);
    } catch (err) {
      const axiosError = err as AxiosError<{ message?: string }>;
      if (axiosError.response?.status === 403) {
        setError("Sem permissão para criar encomendas.");
      } else {
        setError(
          axiosError.response?.data?.message ||
            "Ocorreu um erro ao criar a encomenda. Tente novamente."
        );
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-lg-9">
          <div className="d-flex align-items-center justify-content-between mb-3">
            <h1 className="mb-0">Nova Encomenda</h1>
          </div>

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

          <form onSubmit={handleSubmit}>
            <div className="row g-3">
              <div className="col-md-3">
                <label className="form-label" htmlFor="customerId">
                  Cliente (ID)
                </label>
                <input
                  id="customerId"
                  className="form-control"
                  type="number"
                  min={1}
                  value={order.customerId || ""}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      customerId: Number(e.target.value),
                    }))
                  }
                  required
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="status">
                  Status
                </label>
                <input
                  id="status"
                  className="form-control"
                  value={order.status}
                  onChange={(e) =>
                    setOrder((p) => ({ ...p, status: e.target.value }))
                  }
                />
              </div>

              <div className="col-md-3 d-flex align-items-end">
                <div className="form-check">
                  <input
                    id="hasCut"
                    className="form-check-input"
                    type="checkbox"
                    checked={order.has_cut}
                    onChange={(e) =>
                      setOrder((p) => ({ ...p, has_cut: e.target.checked }))
                    }
                  />
                  <label className="form-check-label" htmlFor="hasCut">
                    Possui corte
                  </label>
                </div>
              </div>

              <div className="col-md-6">
                <label className="form-label" htmlFor="model">
                  Modelo
                </label>
                <input
                  id="model"
                  className="form-control"
                  value={order.model}
                  onChange={(e) =>
                    setOrder((p) => ({ ...p, model: e.target.value }))
                  }
                />
              </div>

              <div className="col-md-6">
                <label className="form-label" htmlFor="fabric">
                  Tecido
                </label>
                <input
                  id="fabric"
                  className="form-control"
                  value={order.fabric}
                  onChange={(e) =>
                    setOrder((p) => ({ ...p, fabric: e.target.value }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="quantity">
                  Quantidade
                </label>
                <input
                  id="quantity"
                  className="form-control"
                  type="number"
                  min={1}
                  value={order.quantity}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      quantity: Number(e.target.value),
                    }))
                  }
                  required
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="chestCustomization">
                  Customização (peito)
                </label>
                <input
                  id="chestCustomization"
                  className="form-control"
                  type="number"
                  value={order.chest_customization}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      chest_customization: Number(e.target.value),
                    }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="backCustomization">
                  Customização (costas)
                </label>
                <input
                  id="backCustomization"
                  className="form-control"
                  type="number"
                  value={order.back_customization}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      back_customization: Number(e.target.value),
                    }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="sleeveCustomization">
                  Customização (manga)
                </label>
                <input
                  id="sleeveCustomization"
                  className="form-control"
                  type="number"
                  value={order.sleeve_customization}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      sleeve_customization: Number(e.target.value),
                    }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="unitPrice">
                  Preço unitário
                </label>
                <input
                  id="unitPrice"
                  className="form-control"
                  type="number"
                  step="0.01"
                  min={0}
                  value={order.unit_price}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      unit_price: Number(e.target.value),
                    }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="totalPrice">
                  Preço total
                </label>
                <input
                  id="totalPrice"
                  className="form-control"
                  type="number"
                  step="0.01"
                  min={0}
                  value={order.total_price}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      total_price: Number(e.target.value),
                    }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="deliveryDate">
                  Data de entrega
                </label>
                <input
                  id="deliveryDate"
                  className="form-control"
                  type="date"
                  value={order.delivery_date}
                  onChange={(e) =>
                    setOrder((p) => ({ ...p, delivery_date: e.target.value }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="advanceDate">
                  Data do adiantamento
                </label>
                <input
                  id="advanceDate"
                  className="form-control"
                  type="date"
                  value={order.advance_date}
                  onChange={(e) =>
                    setOrder((p) => ({ ...p, advance_date: e.target.value }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="advanceAmount">
                  Valor adiantado
                </label>
                <input
                  id="advanceAmount"
                  className="form-control"
                  type="number"
                  step="0.01"
                  min={0}
                  value={order.advance_amount}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      advance_amount: Number(e.target.value),
                    }))
                  }
                />
              </div>

              <div className="col-md-3">
                <label className="form-label" htmlFor="remainingAmount">
                  Valor restante
                </label>
                <input
                  id="remainingAmount"
                  className="form-control"
                  type="number"
                  step="0.01"
                  min={0}
                  value={order.remaining_amount}
                  onChange={(e) =>
                    setOrder((p) => ({
                      ...p,
                      remaining_amount: Number(e.target.value),
                    }))
                  }
                />
              </div>

              <div className="col-12">
                <label className="form-label" htmlFor="colors">
                  Cores (separadas por vírgula)
                </label>
                <input
                  id="colors"
                  className="form-control"
                  placeholder="Ex: Vermelho, Azul, Branco"
                  value={colorsText}
                  onChange={(e) => setColorsText(e.target.value)}
                />
              </div>

              <div className="col-12">
                <div className="d-flex align-items-center justify-content-between">
                  <h5 className="mb-0">Tamanhos</h5>
                  <button
                    type="button"
                    className="btn btn-outline-secondary btn-sm"
                    onClick={() =>
                      setOrder((p) => ({ ...p, sizes: [...p.sizes, emptySize()] }))
                    }
                  >
                    Adicionar tamanho
                  </button>
                </div>
                <div className="mt-2">
                  {order.sizes.map((s, idx) => (
                    <div className="row g-2 align-items-end mb-2" key={idx}>
                      <div className="col-md-4">
                        <label className="form-label">Base size</label>
                        <input
                          className="form-control"
                          value={s.base_size}
                          onChange={(e) =>
                            setOrder((p) => {
                              const next = [...p.sizes];
                              next[idx] = { ...next[idx], base_size: e.target.value };
                              return { ...p, sizes: next };
                            })
                          }
                          placeholder="Ex: P, M, G"
                        />
                      </div>
                      <div className="col-md-4">
                        <label className="form-label">Variant</label>
                        <input
                          className="form-control"
                          value={s.variant}
                          onChange={(e) =>
                            setOrder((p) => {
                              const next = [...p.sizes];
                              next[idx] = { ...next[idx], variant: e.target.value };
                              return { ...p, sizes: next };
                            })
                          }
                          placeholder="Ex: MASC/FEM"
                        />
                      </div>
                      <div className="col-md-2">
                        <label className="form-label">Qtd</label>
                        <input
                          className="form-control"
                          type="number"
                          min={1}
                          value={s.quantity}
                          onChange={(e) =>
                            setOrder((p) => {
                              const next = [...p.sizes];
                              next[idx] = { ...next[idx], quantity: Number(e.target.value) };
                              return { ...p, sizes: next };
                            })
                          }
                        />
                      </div>
                      <div className="col-md-2 d-flex">
                        <button
                          type="button"
                          className="btn btn-outline-danger w-100"
                          disabled={order.sizes.length === 1}
                          onClick={() =>
                            setOrder((p) => ({
                              ...p,
                              sizes: p.sizes.filter((_, i) => i !== idx),
                            }))
                          }
                        >
                          Remover
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              </div>

              <div className="col-12">
                <label className="form-label" htmlFor="artwork">
                  Imagem (artwork)
                </label>
                <input
                  id="artwork"
                  className="form-control"
                  type="file"
                  accept="image/*"
                  onChange={(e) => setArtworkFile(e.target.files?.[0])}
                />
                {previewUrl && (
                  <div className="mt-3">
                    <div className="small text-muted mb-2">Preview:</div>
                    <img
                      src={previewUrl}
                      alt="Preview artwork"
                      style={{
                        maxWidth: "240px",
                        maxHeight: "240px",
                        objectFit: "cover",
                        borderRadius: "8px",
                        border: "1px solid #ddd",
                      }}
                    />
                  </div>
                )}
              </div>

              <div className="col-12 mt-2">
                <button className="btn btn-primary" type="submit" disabled={loading}>
                  {loading ? "Salvando..." : "Salvar encomenda"}
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

