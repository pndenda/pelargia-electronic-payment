package com.econetwireless.epay.business.integrations.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.in.webservice.IntelligentNetworkService;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPlatformImplTest {

    @Mock
    IntelligentNetworkService intelligentNetworkService;

    private ChargingPlatform chargingPlatform;
    private INBalanceResponse inBalanceResponse;
    private SubscriberRequest subscriberRequest;
    private BalanceResponse balanceResponse;
    private INCreditRequest inCreditRequest;
    private INCreditResponse inCreditResponse;
    private CreditResponse  creditResponse;

    private double amount;
    private String msisdn;
    private String partnerCode;
    private String referenceNumber;
    private String requestType;
    private String responseCode;
    //private String narrative;

    @Before
    public void setUp(){

    chargingPlatform = new ChargingPlatformImpl(intelligentNetworkService);

        msisdn = "0777222838";
        amount = 1.0;
        partnerCode = "Pc001";
        referenceNumber = "RF001";
        requestType = "Aitime Topup";


        inBalanceResponse = new INBalanceResponse();
            inBalanceResponse.setAmount(amount);
            inBalanceResponse.setMsisdn(msisdn);
            inBalanceResponse.setNarrative("Successful");
            inBalanceResponse.setResponseCode("400");


            subscriberRequest = new SubscriberRequest();
            subscriberRequest.setAmount(amount);
            subscriberRequest.setMsisdn(msisdn);
            subscriberRequest.setId(1L);
            subscriberRequest.setPartnerCode(partnerCode);
            subscriberRequest.setReference(referenceNumber);
            subscriberRequest.setRequestType(requestType);
            subscriberRequest.setBalanceBefore(2);
            subscriberRequest.setBalanceAfter(4);

            balanceResponse = new BalanceResponse();
            balanceResponse.setAmount(amount);
            balanceResponse.setMsisdn(msisdn);
            balanceResponse.setNarrative("Successful");
            balanceResponse.setResponseCode("400");

        inCreditRequest = new INCreditRequest();
        inCreditRequest.setAmount(amount);
        inCreditRequest.setReferenceNumber(referenceNumber);
        inCreditRequest.setPartnerCode(partnerCode);
        inCreditRequest.setMsisdn(msisdn);

        inCreditResponse = new INCreditResponse();
        inCreditResponse.setBalance(2.0);
        inCreditResponse.setMsisdn(msisdn);
        inCreditResponse.setResponseCode("400");
        inCreditResponse.setNarrative("Successful Topup");

        creditResponse = new CreditResponse();
        creditResponse.setBalance(1.0);
        creditResponse.setMsisdn(msisdn);
        creditResponse.setNarrative("Successful");
        creditResponse.setResponseCode("400");



    }

    @Test
    public void enquireAirtimeBalanceShouldFailIfServerIsNotAvailable(){
        final String message ="Server not available";

        balanceResponse.setResponseCode("400");
        balanceResponse.setNarrative(message);

        when(intelligentNetworkService.enquireBalance(any(),  any())).thenReturn(balanceResponse);

        final INBalanceResponse inBalanceResponse = chargingPlatform.enquireBalance(partnerCode, msisdn);
        assertNotNull(inBalanceResponse);
        assertEquals(message, inBalanceResponse.getNarrative());
        assertEquals("400",inBalanceResponse.getResponseCode());


    }

    @Test
    public void enquireAirtimeBalanceShouldPassIfPartnerCodeAndMsisdnAreValid(){

        final String message ="Successful";

        when(intelligentNetworkService.enquireBalance(any(),  any())).thenReturn(balanceResponse);

        final INBalanceResponse inBalanceResponse = chargingPlatform.enquireBalance(partnerCode, msisdn);
        assertNotNull(inBalanceResponse);
        assertEquals(message, inBalanceResponse.getNarrative());
        assertEquals("400",inBalanceResponse.getResponseCode());

    }

    @Test
    public void creditSubscriberAccount(){
        final String message = "Successful";

        when(intelligentNetworkService.creditSubscriberAccount(any())).thenReturn(creditResponse);

       final INCreditResponse inCreditResponse = chargingPlatform.creditSubscriberAccount(inCreditRequest);
        assertNotNull(inCreditResponse);
        assertEquals(message, inCreditResponse.getNarrative());
        assertEquals("400",inCreditResponse.getResponseCode());

    }
    }

