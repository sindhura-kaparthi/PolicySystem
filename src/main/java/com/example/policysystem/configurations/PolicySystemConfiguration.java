package com.example.policysystem.configurations;

import com.example.policysystem.utility.Constants;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class PolicySystemConfiguration {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(messageDispatcherServlet, "/ws/*");
    }

    @Bean(name = "policy-details")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema policyDetailsSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("PolicyDetailsPort");
        definition.setTargetNamespace(Constants.NAMESPACE);
        definition.setLocationUri("/ws");
        definition.setSchema(policyDetailsSchema);
        return definition;
    }

    @Bean
    public XsdSchema policyDetailsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("policy-system.xsd"));
    }
}
