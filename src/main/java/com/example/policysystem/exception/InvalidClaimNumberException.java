package com.example.policysystem.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class InvalidClaimNumberException extends Exception {
    public InvalidClaimNumberException(String message) {
        super(message);
    }
}
