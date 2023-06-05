package com.example.policysystem.controller;

import com.example.policysystem.policies.GetPolicyDetailsRequest;
import com.example.policysystem.policies.GetPolicyDetailsResponse;
import com.example.policysystem.policies.PolicyDetails;
import com.example.policysystem.service.PolicyDetailsSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PolicyDetailsEndpoint {
    @Autowired
    PolicyDetailsSevice policyDetailsSevice;

    @PayloadRoot(namespace = "http://policysystem.com/policies", localPart = "GetPolicyDetailsRequest")
    @ResponsePayload
    public GetPolicyDetailsResponse processCourseDetailsRequest(@RequestPayload GetPolicyDetailsRequest claimNumber) {
        System.out.println(claimNumber);
        PolicyDetails policyDetails = policyDetailsSevice.getPolicyDetails();
        GetPolicyDetailsResponse response = new GetPolicyDetailsResponse();
        response.setPolicyDetails(policyDetails);
        return response;
    }
}
