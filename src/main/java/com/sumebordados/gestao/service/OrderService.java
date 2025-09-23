package com.sumebordados.gestao.service;
import com.sumebordados.gestao.dto.order.CreateOrderRequestDTO;
import com.sumebordados.gestao.dto.order.CreateOrderResponseDTO;
import com.sumebordados.gestao.exception.CustomerNotFoundException;
import com.sumebordados.gestao.exception.OrderNotFoundException;
import com.sumebordados.gestao.model.Customer;
import com.sumebordados.gestao.model.Order;
import com.sumebordados.gestao.model.OrderSize;
import com.sumebordados.gestao.model.OrderSizeId;
import com.sumebordados.gestao.model.enums.OrderStatus;
import com.sumebordados.gestao.repository.CustomerRepository;
import com.sumebordados.gestao.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;

    @Transactional
    public CreateOrderResponseDTO createOrder(CreateOrderRequestDTO dto){

        Customer customer = customerRepo.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.customerId()));

        Order order = new Order(
                customer, dto.model(), dto.fabric(), dto.has_cut(),
                dto.quantity(), dto.chest_customization(), dto.back_customization(),
                dto.sleeve_customization(), dto.unit_price(), dto.total_price(),
                dto.delivery_date(), dto.advance_date(), dto.advance_amount(), dto.remaining_amount(),
                dto.status(), dto.artwork_url(), dto.colors()
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
        return new CreateOrderResponseDTO(saved.getId());
    }
    @Transactional
    public void deleteOrder(Long id){
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepo.delete(order);
    }
    @Transactional
    public CreateOrderResponseDTO updateOrder(Long id, CreateOrderRequestDTO dto){
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
        order.setArtwork_url(dto.artwork_url());
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
        return new CreateOrderResponseDTO(saved.getId());
    }

}
