package com.econetwireless.epay.business.utils;

import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageConvertersTest {


    private CreditRequest creditRequest;
    private static INCreditRequest inCreditRequest;

    private String msisdn;
    private String partnerCode;
    private String referenceNumber;
    private String amount;

    @Before
    public void setUp() throws Exception {

        inCreditRequest = new INCreditRequest();
        inCreditRequest.setMsisdn(msisdn);
        inCreditRequest.setPartnerCode(partnerCode);
        inCreditRequest.setReferenceNumber(referenceNumber);
        inCreditRequest.setAmount(1.0);

        creditRequest = new CreditRequest();
        creditRequest.setAmount(1.0);
        creditRequest.setMsisdn(msisdn);
        creditRequest.setPartnerCode(partnerCode);
        creditRequest.setReferenceNumber(referenceNumber);
    }

@Test
    public void SetUp(){
        
}



    }

