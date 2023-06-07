package com.example.policysystem.service

import com.example.policysystem.model.GetPolicyDetailsResponse
import com.example.policysystem.model.PolicyDetails
import org.mockito.MockitoAnnotations
import org.spockframework.spring.SpringBean
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

class PolicyDetailsServiceSpec extends Specification{

    @SpringBean
    JmsTemplate jmsTemplate = Mock(JmsTemplate);

    private PolicyDetailsService policyDetailsService

    def setup() {
        MockitoAnnotations.openMocks(this)
        policyDetailsService = new PolicyDetailsService()
        policyDetailsService.jmsTemplate = jmsTemplate
    }

    def "should retrieve policy policy details"() {
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