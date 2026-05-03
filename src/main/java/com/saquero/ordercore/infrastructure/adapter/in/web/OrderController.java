package com.saquero.ordercore.infrastructure.adapter.in.web;

import com.saquero.ordercore.application.command.CreateOrderCommand;
import com.saquero.ordercore.application.command.ProcessPaymentCommand;
import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.dto.PaymentResponse;
import com.saquero.ordercore.application.port.in.CreateOrderUseCase;
import com.saquero.ordercore.application.port.in.GetOrderUseCase;
import com.saquero.ordercore.application.port.in.ProcessPaymentUseCase;
import com.saquero.ordercore.infrastructure.adapter.in.web.request.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final ProcessPaymentUseCase processPaymentUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           GetOrderUseCase getOrderUseCase,
                           ProcessPaymentUseCase processPaymentUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderCommand command = new CreateOrderCommand(
                request.getCustomerId(),
                request.getTotalAmount(),
                request.getCurrency()
        );
        OrderResponse response = createOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID id) {
        OrderResponse response = getOrderUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<PaymentResponse> processPayment(@PathVariable UUID id) {
        ProcessPaymentCommand command = new ProcessPaymentCommand(id);
        PaymentResponse response = processPaymentUseCase.execute(command);
        return ResponseEntity.ok(response);
    }
}