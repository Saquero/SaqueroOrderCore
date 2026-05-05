package com.saquero.ordercore.infrastructure.adapter.in.web;

import com.saquero.ordercore.application.command.CreateCustomerCommand;
import com.saquero.ordercore.application.dto.CustomerResponse;
import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.port.in.CreateCustomerUseCase;
import com.saquero.ordercore.application.port.in.GetCustomerUseCase;
import com.saquero.ordercore.application.port.in.GetOrdersByCustomerUseCase;
import com.saquero.ordercore.infrastructure.adapter.in.web.request.CreateCustomerRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final GetOrdersByCustomerUseCase getOrdersByCustomerUseCase;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase,
                              GetCustomerUseCase getCustomerUseCase,
                              GetOrdersByCustomerUseCase getOrdersByCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.getOrdersByCustomerUseCase = getOrdersByCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {
        CreateCustomerCommand command = new CreateCustomerCommand(
                request.getName(),
                request.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createCustomerUseCase.execute(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID id) {
        return ResponseEntity.ok(getCustomerUseCase.execute(id));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomer(@PathVariable UUID id) {
        return ResponseEntity.ok(getOrdersByCustomerUseCase.execute(id));
    }
}