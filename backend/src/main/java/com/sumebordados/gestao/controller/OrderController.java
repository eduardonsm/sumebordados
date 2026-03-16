package com.sumebordados.gestao.controller;

import com.sumebordados.gestao.dto.order.OrderRequestDTO;
import com.sumebordados.gestao.dto.order.OrderResponseDTO;
import com.sumebordados.gestao.model.Order;
import com.sumebordados.gestao.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestPart("order") @Valid OrderRequestDTO dto,
            @RequestPart(value = "artwork", required = false) MultipartFile artwork) {
        OrderResponseDTO created = orderService.createOrder(dto, artwork);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping(value = "/{id}", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable Long id,
            @RequestPart("order") @Valid OrderRequestDTO dto,
            @RequestPart(value = "artwork", required = false) MultipartFile artwork) {

        OrderResponseDTO updated = orderService.updateOrder(id, dto, artwork);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}