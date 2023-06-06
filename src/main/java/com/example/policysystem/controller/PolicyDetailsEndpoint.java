package com.example.policysystem.controller;

import com.example.policysystem.policies.GetPolicyDetailsRequest;
import com.example.policysystem.policies.GetPolicyDetailsResponse;
import com.example.policysystem.policies.PolicyDetails;
import com.example.policysystem.service.PolicyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PolicyDetailsEndpoint {
    final PolicyDetailsService policyDetailsService;

    @Autowired
    public PolicyDetailsEndpoint(PolicyDetailsService policyDetailsService) {
        this.policyDetailsService = policyDetailsService;
    }

    @PayloadRoot(namespace = "http://policysystem.com/policies", localPart = "GetPolicyDetailsRequest")
    @ResponsePayload
    public GetPolicyDetailsResponse getPolicyDetails(@RequestPayload GetPolicyDetailsRequest request) {
        PolicyDetails policyDetails = policyDetailsService.getPolicyDetails();
        GetPolicyDetailsResponse response = new GetPolicyDetailsResponse();
        response.setPolicyDetails(policyDetails);
        return response;
    }

}
