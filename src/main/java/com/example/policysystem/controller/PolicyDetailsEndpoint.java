package com.example.policysystem.controller;

import com.example.policysystem.exception.InvalidClaimNumberException;
import com.example.policysystem.model.GetPolicyDetailsRequest;
import com.example.policysystem.model.GetPolicyDetailsResponse;
import com.example.policysystem.model.PolicyDetails;
import com.example.policysystem.service.PolicyDetailsService;
import com.example.policysystem.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PolicyDetailsEndpoint {
    private static final String INVALID_CLAIM_NUMBER = "Claim Number is not valid";
    PolicyDetailsService policyDetailsService;

    @Autowired
    public PolicyDetailsEndpoint(PolicyDetailsService policyDetailsService) {
        this.policyDetailsService = policyDetailsService;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE, localPart = "GetPolicyDetailsRequest")
    @ResponsePayload
    public GetPolicyDetailsResponse getPolicyDetails(@RequestPayload GetPolicyDetailsRequest request) throws InvalidClaimNumberException {

        if (!isClaimNumberValid(request.getClaimNumber())) throw new InvalidClaimNumberException(INVALID_CLAIM_NUMBER);

        PolicyDetails policyDetails = policyDetailsService.getPolicyDetails();
        GetPolicyDetailsResponse response = new GetPolicyDetailsResponse();
        response.setPolicyDetails(policyDetails);
        policyDetailsService.sendMessage(response);
        return response;
    }

    private boolean isClaimNumberValid(String claimNumber) {
        return claimNumber.matches("^[A-Za-z]{3}-\\d{6}$");
    }
}