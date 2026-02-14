package com.sumebordados.gestao.repository;

import com.sumebordados.gestao.model.OrderSize;
import com.sumebordados.gestao.model.OrderSizeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSizeRepository extends JpaRepository<OrderSize, OrderSizeId> {
}
