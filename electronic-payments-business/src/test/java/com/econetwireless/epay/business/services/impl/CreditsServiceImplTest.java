package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.config.IntegrationsConfig;
import com.econetwireless.epay.business.config.RootConfig;
import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditsServiceImplTest {

        @Mock
        ChargingPlatform chargingPlatform;

        CreditsService creditsService;

        @Mock
        SubscriberRequestDao subscriberRequestDao;


        AirtimeTopupRequest airtimeTopupRequest;
        AirtimeTopupResponse airtimeTopupResponse;

        private String msisdn;
        private String referenceNumber;
        private String partnerCode;
        private double amount;
        private SubscriberRequest subscriberRequest;
        private String requestType;

        private INCreditRequest inCreditRequest;
        private INCreditResponse inCreditResponse;

        @Before
        public void setUp() {

            creditsService = new CreditsServiceImpl(chargingPlatform, subscriberRequestDao);

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

            inCreditResponse = new INCreditResponse();
            inCreditResponse.setBalance(2.0);
            inCreditResponse.setMsisdn(msisdn);
            inCreditResponse.setResponseCode("400");
            inCreditResponse.setNarrative("Successful Topup");

            airtimeTopupResponse = new AirtimeTopupResponse();
            airtimeTopupResponse.setNarrative("TopUp Sucessful");
            airtimeTopupResponse.setResponseCode("200");
            airtimeTopupResponse.setMsisdn(msisdn);
            airtimeTopupResponse.setBalance(1.0);



        }

        @Test
        public void creditShouldFailIfServerIsNotReachable(){
            final String message = "Server not available";

            inCreditResponse.setResponseCode("500");
            inCreditResponse.setNarrative(message);
            when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
            when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);

            final AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
            assertNotNull(airtimeTopupResponse);
            assertEquals(message, airtimeTopupResponse.getNarrative());
            assertEquals(ResponseCode.FAILED.getCode(), airtimeTopupResponse.getResponseCode());
            verify(subscriberRequestDao, times(2)).save(any(SubscriberRequest.class));
            verify(chargingPlatform, times(1)).creditSubscriberAccount(any(INCreditRequest.class));


        }

        @Test
        public void enquireAirtimeBalanceShouldPassIfPartnerCodeAndMsisdnAreValid(){
        final String message ="Successful Topup";

            when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
            when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);

            final AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
            assertNotNull(airtimeTopupResponse);
            assertEquals(message, airtimeTopupResponse.getNarrative());
            assertEquals("400", airtimeTopupResponse.getResponseCode());
            verify(subscriberRequestDao, times(2)).save(any(SubscriberRequest.class));
            verify(chargingPlatform, times(1)).creditSubscriberAccount(any(INCreditRequest.class));




        }



    }





