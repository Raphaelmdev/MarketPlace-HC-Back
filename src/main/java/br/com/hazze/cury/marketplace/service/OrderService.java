package br.com.hazze.cury.marketplace.service;

import br.com.hazze.cury.marketplace.dto.request.OrderItemRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.OrderRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.OrderStatusUpdateDTO;
import br.com.hazze.cury.marketplace.dto.response.OrderResponseDTO;
import br.com.hazze.cury.marketplace.entities.*;
import br.com.hazze.cury.marketplace.exceptions.BusinessException;
import br.com.hazze.cury.marketplace.exceptions.ResourceNotFoundException;
import br.com.hazze.cury.marketplace.mappers.OrderItemMapper;
import br.com.hazze.cury.marketplace.mappers.OrderMapper;
import br.com.hazze.cury.marketplace.repositories.OrderItemRepository;
import br.com.hazze.cury.marketplace.repositories.OrderRepository;
import br.com.hazze.cury.marketplace.repositories.ProductRepository;
import br.com.hazze.cury.marketplace.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    public OrderResponseDTO create(OrderRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        Order order = orderMapper.toEntity(dto);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(BigDecimal.ZERO);

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : dto.items()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

            if (!product.getActive()) {
                throw new BusinessException("Produto inativo.");
            }

            if (product.getStock() < itemDTO.quantity()) {
                throw new BusinessException("Estoque insuficiente para o produto: " + product.getName());
            }

            OrderItem orderItem = orderItemMapper.toEntity(itemDTO);
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setUnitPrice(product.getPrice());

            BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.quantity()));
            orderItem.setSubTotal(subTotal);

            orderItems.add(orderItem);
            total = total.add(subTotal);

            product.setStock(product.getStock() - itemDTO.quantity());
            productRepository.save(product);
        }

        orderItemRepository.saveAll(orderItems);

        savedOrder.setItems(orderItems);
        savedOrder.setTotal(total);

        return orderMapper.toResponse(orderRepository.save(savedOrder));
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAll() {
        return orderMapper.toResponseList(orderRepository.findAll());
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        return orderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findByUserId(Long userId) {
        return orderMapper.toResponseList(orderRepository.findByUserId(userId));
    }

    @Transactional
    public OrderResponseDTO updateStatus(Long id, OrderStatusUpdateDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        order.setStatus(dto.status());

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        List<OrderItem> items = orderItemRepository.findByOrderId(id);
        orderItemRepository.deleteAll(items);

        orderRepository.delete(order);
    }
}