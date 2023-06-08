package com.example.policysystem.service

import com.example.policysystem.model.GetPolicyDetailsResponse
import com.example.policysystem.model.PolicyDetails
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

class PolicyDetailsServiceSpec extends Specification {

    private PolicyDetailsService policyDetailsService
    private JmsTemplate jmsTemplate

    def setup() {
        jmsTemplate = Mock(JmsTemplate)
        policyDetailsService = new PolicyDetailsService()
        policyDetailsService.jmsTemplate = jmsTemplate
    }

    def "getPolicyDetails should retrieve policy details"() {
        when:
        PolicyDetails policyDetails = policyDetailsService.getPolicyDetails()

        then:
        policyDetails.policyNumber == BigInteger.valueOf(1234)
        policyDetails.policyHolderName == "ABCD"
        policyDetails.coverageName == "EFGH"
        policyDetails.coverageLimitInUSD == BigInteger.valueOf(2)
        policyDetails.deductibleInUSD == BigInteger.valueOf(3)
    }

    def "should send message to jms queue"() {
        given:
        GetPolicyDetailsResponse response = new GetPolicyDetailsResponse()

        when:
        policyDetailsService.sendMessage(response)

        then:
        1 * jmsTemplate.convertAndSend(_, _)
    }
}