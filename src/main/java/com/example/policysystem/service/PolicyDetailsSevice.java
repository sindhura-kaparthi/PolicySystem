package com.example.policysystem.service;

import com.example.policysystem.policies.PolicyDetails;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class PolicyDetailsSevice {


    public PolicyDetails getPolicyDetails() {

        PolicyDetails policyDetails = new PolicyDetails();
        policyDetails.setPolicyNumber(BigInteger.valueOf(1234));
        policyDetails.setPolicyHolderName("ABCD");
        policyDetails.setCoverageName("EFGH");
        policyDetails.setCoverageLimitInUSD(BigInteger.valueOf(2));
        policyDetails.setDeductibleInUSD(BigInteger.valueOf(3));
        return policyDetails;
    }
}
