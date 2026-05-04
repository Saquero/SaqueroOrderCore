package com.saquero.ordercore.infrastructure.adapter.in.web;

import com.saquero.ordercore.application.command.CreateOrderCommand;
import com.saquero.ordercore.application.command.ProcessPaymentCommand;
import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.dto.OrderSummaryResponse;
import com.saquero.ordercore.application.dto.PageResponse;
import com.saquero.ordercore.application.dto.PaymentResponse;
import com.saquero.ordercore.application.port.in.CreateOrderUseCase;
import com.saquero.ordercore.application.port.in.GetOrderSummaryUseCase;
import com.saquero.ordercore.application.port.in.GetOrderUseCase;
import com.saquero.ordercore.application.port.in.GetPaymentsByOrderUseCase;
import com.saquero.ordercore.application.port.in.ListOrdersUseCase;
import com.saquero.ordercore.application.port.in.ProcessPaymentUseCase;
import com.saquero.ordercore.domain.exception.OrderDomainException;
import com.saquero.ordercore.domain.model.OrderStatus;
import com.saquero.ordercore.infrastructure.adapter.in.web.request.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final int MAX_PAGE_SIZE = 50;

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final ListOrdersUseCase listOrdersUseCase;
    private final ProcessPaymentUseCase processPaymentUseCase;
    private final GetPaymentsByOrderUseCase getPaymentsByOrderUseCase;
    private final GetOrderSummaryUseCase getOrderSummaryUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           GetOrderUseCase getOrderUseCase,
                           ListOrdersUseCase listOrdersUseCase,
                           ProcessPaymentUseCase processPaymentUseCase,
                           GetPaymentsByOrderUseCase getPaymentsByOrderUseCase,
                           GetOrderSummaryUseCase getOrderSummaryUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.listOrdersUseCase = listOrdersUseCase;
        this.processPaymentUseCase = processPaymentUseCase;
        this.getPaymentsByOrderUseCase = getPaymentsByOrderUseCase;
        this.getOrderSummaryUseCase = getOrderSummaryUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderCommand command = new CreateOrderCommand(
                request.getCustomerId(),
                request.getTotalAmount(),
                request.getCurrency()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createOrderUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> listOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > MAX_PAGE_SIZE) {
            throw new OrderDomainException("Page size must not exceed " + MAX_PAGE_SIZE);
        }
        if (page < 0) {
            throw new OrderDomainException("Page index must not be negative");
        }
        OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : null;
        return ResponseEntity.ok(listOrdersUseCase.execute(orderStatus, page, size));
    }

    @GetMapping("/summary")
    public ResponseEntity<OrderSummaryResponse> getOrderSummary() {
        return ResponseEntity.ok(getOrderSummaryUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(getOrderUseCase.execute(id));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<PaymentResponse> processPayment(@PathVariable UUID id) {
        return ResponseEntity.ok(processPaymentUseCase.execute(new ProcessPaymentCommand(id)));
    }

    @GetMapping("/{id}/payments")
    public ResponseEntity<List<PaymentResponse>> getPayments(@PathVariable UUID id) {
        return ResponseEntity.ok(getPaymentsByOrderUseCase.execute(id));
    }
}