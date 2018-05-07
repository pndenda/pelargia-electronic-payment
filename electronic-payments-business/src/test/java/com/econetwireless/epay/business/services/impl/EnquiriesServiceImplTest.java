package com.econetwireless.epay.business.services.impl;


import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.business.services.api.EnquiriesService;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnquiriesServiceImplTest {


    @Mock
    private ChargingPlatform chargingPlatform;

    @Mock
    private SubscriberRequestDao subscriberRequestDao;

    private EnquiriesService enquiriesService;
    private AirtimeTopupRequest airtimeTopupRequest;

    private String msisdn;
    private String partnerCode;
    private double amount;
    private String requestType;
    private String referenceNumber;

    private INCreditRequest inCreditRequest;
    private INBalanceResponse inBalanceResponse;
    private SubscriberRequest subscriberRequest;

    @Before
    public void setUp() {

        enquiriesService = new EnquiriesServiceImpl(chargingPlatform, subscriberRequestDao);

        msisdn = "0777222838";
        amount = 1.0;
        partnerCode = "Pc001";
        referenceNumber = "RF001";
        requestType = "Aitime Topup";

        subscriberRequest = new SubscriberRequest();
        subscriberRequest.setAmount(amount);
        subscriberRequest.setMsisdn(msisdn);
        subscriberRequest.setId(1L);
        subscriberRequest.setPartnerCode(partnerCode);
        subscriberRequest.setReference(referenceNumber);
        subscriberRequest.setRequestType(requestType);
        subscriberRequest.setBalanceBefore(2);
        subscriberRequest.setBalanceAfter(4);

        airtimeTopupRequest = new AirtimeTopupRequest();
        airtimeTopupRequest.setAmount(amount);
        airtimeTopupRequest.setMsisdn(msisdn);
        airtimeTopupRequest.setPartnerCode(partnerCode);
        airtimeTopupRequest.setReferenceNumber(referenceNumber);

        inCreditRequest = new INCreditRequest();
        inCreditRequest.setAmount(amount);
        inCreditRequest.setReferenceNumber(referenceNumber);
        inCreditRequest.setPartnerCode(partnerCode);
        inCreditRequest.setMsisdn(msisdn);

        inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setAmount(amount);
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setNarrative("Successful");
        inBalanceResponse.setResponseCode("400");


    }

    @Test
    public void enquireAirtimeBalanceShouldFailIfServerIsNotAvailable(){
        final String message ="Server not available";

        inBalanceResponse.setResponseCode("400");
        inBalanceResponse.setNarrative(message);

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(any(), any())).thenReturn(inBalanceResponse);

        final AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode, msisdn);
        assertNotNull(airtimeBalanceResponse);
        assertEquals(message, airtimeBalanceResponse.getNarrative());
        assertEquals("400", airtimeBalanceResponse.getResponseCode());
        verify(subscriberRequestDao, times(2)).save(any(SubscriberRequest.class));


    }

    @Test
    public void enquireAirtimeBalanceShouldPassIfPartnerCodeAndMsisdnAreValid(){
        final String message ="Successful";

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(any(), any())).thenReturn(inBalanceResponse);

        final AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode, msisdn);
        assertNotNull(airtimeBalanceResponse);
        assertEquals(message, airtimeBalanceResponse.getNarrative());
        assertEquals("400", airtimeBalanceResponse.getResponseCode());
        verify(subscriberRequestDao, times(2)).save(any(SubscriberRequest.class));




    }



}
