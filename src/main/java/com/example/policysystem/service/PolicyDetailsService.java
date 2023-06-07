package com.example.policysystem.service;

import com.example.policysystem.model.GetPolicyDetailsResponse;
import com.example.policysystem.model.PolicyDetails;
import jakarta.xml.bind.JAXB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.math.BigInteger;

@Component
public class PolicyDetailsService {

    @Autowired
    JmsTemplate jmsTemplate;

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

    public void sendMessage(GetPolicyDetailsResponse response) {
        String xmlString = marshallToXml(response);
        jmsTemplate.convertAndSend(queue, xmlString);
    }

    private String marshallToXml(Object object) {
        StringWriter writer = new StringWriter();
        JAXB.marshal(object, writer);
        return writer.toString();
    }
}