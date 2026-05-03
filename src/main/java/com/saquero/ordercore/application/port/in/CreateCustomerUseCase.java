package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.command.CreateCustomerCommand;
import com.saquero.ordercore.application.dto.CustomerResponse;

public interface CreateCustomerUseCase {
    CustomerResponse execute(CreateCustomerCommand command);
}