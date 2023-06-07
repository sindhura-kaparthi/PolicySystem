package com.example.policysystem.service;

import com.example.policysystem.policies.PolicyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class PolicyDetailsService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${springjms.myQueue}")
    private String queue;

    public PolicyDetails getPolicyDetails() {
        PolicyDetails policyDetails = new PolicyDetails();
        policyDetails.setPolicyNumber(BigInteger.valueOf(1234));
        policyDetails.setPolicyHolderName("ABCD");
        policyDetails.setCoverageName("EFGH");
        policyDetails.setCoverageLimitInUSD(BigInteger.valueOf(2));
        policyDetails.setDeductibleInUSD(BigInteger.valueOf(3));
        return policyDetails;
    }

    public void sendMessage(PolicyDetails policyDetails) {
        jmsTemplate.convertAndSend(queue, policyDetails);
    }
}