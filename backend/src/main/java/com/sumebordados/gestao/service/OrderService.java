package com.sumebordados.gestao.service;
import com.sumebordados.gestao.dto.order.OrderRequestDTO;
import com.sumebordados.gestao.dto.order.OrderResponseDTO;
import com.sumebordados.gestao.exception.CustomerNotFoundException;
import com.sumebordados.gestao.exception.OrderNotFoundException;
import com.sumebordados.gestao.model.Customer;
import com.sumebordados.gestao.model.Order;
import com.sumebordados.gestao.model.OrderSize;
import com.sumebordados.gestao.model.OrderSizeId;
import com.sumebordados.gestao.repository.CustomerRepository;
import com.sumebordados.gestao.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto, MultipartFile artwork) {
        Customer customer = customerRepo.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.customerId()));

        byte[] artworkBytes = toArtworkBytes(artwork);

        Order order = new Order(
                customer, dto.model(), dto.fabric(), dto.has_cut(),
                dto.quantity(), dto.chest_customization(), dto.back_customization(),
                dto.sleeve_customization(), dto.unit_price(), dto.total_price(),
                dto.delivery_date(), dto.advance_date(), dto.advance_amount(), dto.remaining_amount(),
                dto.status(), artworkBytes, dto.colors()
        );
        Set<OrderSize> sizes = dto.sizes().stream()
                .map(sizeDto -> {
                    OrderSize os = new OrderSize();
                    os.setId(new OrderSizeId(sizeDto.base_size(), sizeDto.variant()));
                    os.setOrder(order);
                    os.setQuantity(sizeDto.quantity());
                    return os;
                }).collect(Collectors.toSet());

        order.setSizes(sizes);

        Order saved = orderRepo.save(order);
        return new OrderResponseDTO(saved.getId());
    }
    @Transactional
    public void deleteOrder(Long id){
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepo.delete(order);
    }
    @Transactional
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto, MultipartFile artwork) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        Customer customer = customerRepo.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.customerId()));

        order.setCustomer(customer);
        order.setModel(dto.model());
        order.setFabric(dto.fabric());
        order.setHas_cut(dto.has_cut());
        order.setQuantity(dto.quantity());
        order.setChest_customization(dto.chest_customization());
        order.setBack_customization(dto.back_customization());
        order.setSleeve_customization(dto.sleeve_customization());
        order.setUnit_price(dto.unit_price());
        order.setTotal_price(dto.total_price());
        order.setDelivery_date(dto.delivery_date());
        order.setAdvance_date(dto.advance_date());
        order.setAdvance_amount(dto.advance_amount());
        order.setRemaining_amount(dto.remaining_amount());
        order.setStatus(dto.status());
        byte[] artworkBytes = toArtworkBytes(artwork);
        if (artworkBytes != null) {
            order.setArtwork(artworkBytes);
        }
        order.setColors(dto.colors());
        order.getSizes().clear();
        Set<OrderSize> sizes = dto.sizes().stream()
                .map(sizeDto -> {
                    OrderSize os = new OrderSize();
                    os.setId(new OrderSizeId(order.getId(), sizeDto.base_size(), sizeDto.variant()));
                    os.setOrder(order);
                    os.setQuantity(sizeDto.quantity());
                    return os;
                }).collect(Collectors.toSet());
        order.setSizes(sizes);

        Order saved = orderRepo.save(order);
        return new OrderResponseDTO(saved.getId());
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private byte[] toArtworkBytes(MultipartFile artwork) {
        if (artwork == null || artwork.isEmpty()) {
            return null;
        }
        try {
            return artwork.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao ler arquivo da arte: " + e.getMessage(), e);
        }
    }
    @Transactional(readOnly = true)
    public byte[] getOrderArtwork(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (order.getArtwork() == null || order.getArtwork().length == 0) {
            return null;
        }
        return order.getArtwork();
    }
}
