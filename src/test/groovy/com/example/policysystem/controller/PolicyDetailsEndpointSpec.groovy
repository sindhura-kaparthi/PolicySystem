package com.example.policysystem.controller

import com.example.policysystem.model.PolicyDetails
import com.example.policysystem.service.PolicyDetailsService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest
import org.springframework.ws.test.server.MockWebServiceClient
import org.springframework.xml.transform.StringSource
import spock.lang.Specification

import static org.springframework.ws.test.server.RequestCreators.withPayload
import static org.springframework.ws.test.server.ResponseMatchers.*

@WebServiceServerTest
class PolicyDetailsEndpointSpec extends Specification {

    @SpringBean
    PolicyDetailsService policyDetailsService = Mock();

    @Autowired
    private MockWebServiceClient client;

    def "should return policy details in the response with valid claim number"() {
        given:
        new PolicyDetailsEndpoint(policyDetailsService)

        def expectedPolicyDetails = new PolicyDetails()

        expectedPolicyDetails.setPolicyNumber(BigInteger.valueOf(1234));
        expectedPolicyDetails.setPolicyHolderName("ABCD");
        expectedPolicyDetails.setCoverageName("EFGH");
        expectedPolicyDetails.setCoverageLimitInUSD(BigInteger.valueOf(2));
        expectedPolicyDetails.setDeductibleInUSD(BigInteger.valueOf(3));

        policyDetailsService.getPolicyDetails() >> expectedPolicyDetails

        StringSource request = new StringSource("<pol:GetPolicyDetailsRequest xmlns:pol='http://policysystem.com/policies'>" +
                "         <pol:claimNumber>CMs-123456</pol:claimNumber>" +
                "      </pol:GetPolicyDetailsRequest>"
        )

        StringSource expectedResponse = new StringSource(
                "<ns2:GetPolicyDetailsResponse xmlns:ns2='http://policysystem.com/policies'>" +
                        "<ns2:policyDetails>" +
                        "<ns2:policyHolderName>ABCD</ns2:policyHolderName>" +
                        "<ns2:policyNumber>1234</ns2:policyNumber>" +
                        "<ns2:coverageName>EFGH</ns2:coverageName>" +
                        "<ns2:coverageLimitInUSD>2</ns2:coverageLimitInUSD>" +
                        "<ns2:deductibleInUSD>3</ns2:deductibleInUSD>" +
                        "</ns2:policyDetails>" +
                        "</ns2:GetPolicyDetailsResponse>"
        )

        expect:
        client.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(payload(expectedResponse))
    }

    def "should return invalid claim number response when invalid claim number is given"() {
        given:
        new PolicyDetailsEndpoint(policyDetailsService)

        def expectedPolicyDetails = new PolicyDetails()

        expectedPolicyDetails.setPolicyNumber(BigInteger.valueOf(1234));
        expectedPolicyDetails.setPolicyHolderName("ABCD");
        expectedPolicyDetails.setCoverageName("EFGH");
        expectedPolicyDetails.setCoverageLimitInUSD(BigInteger.valueOf(2));
        expectedPolicyDetails.setDeductibleInUSD(BigInteger.valueOf(3));

        policyDetailsService.getPolicyDetails() >> expectedPolicyDetails

        StringSource request = new StringSource("<pol:GetPolicyDetailsRequest xmlns:pol='http://policysystem.com/policies'>" +
                "         <pol:claimNumber>CM-123456</pol:claimNumber>" +
                "      </pol:GetPolicyDetailsRequest>"
        )
        StringSource expectedResponse = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">Claim Number is not valid</faultstring>" +
                        "</SOAP-ENV:Fault>"
        )

        expect:
        client.sendRequest(withPayload(request))
                .andExpect(serverOrReceiverFault("Claim Number is not valid"))
                .andExpect(payload(expectedResponse))
    }
}