package com.econetwireless.epay.business.utils;

import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageConvertersTest {

    private String partnerCode;
    private String msisdn;
    private double amount;
    private String referenceNumber;
    private String responseCode;
    private String narrative;
    private double balance;

    private INCreditRequest inRequest;
    private CreditRequest creditRequest;
    private INCreditResponse inCreditResponse;
    private CreditResponse creditResponse;
    private INBalanceResponse inBalanceResponse;
    private BalanceResponse balanceResponse;

    @Before
    public void setUp() {

        partnerCode = "001";
        msisdn = "0777222838";
        amount = 1.0;
        referenceNumber = "REF-01";

        responseCode = "400";
        narrative = "Successful";
        balance = 2.0;

        inRequest = new INCreditRequest();
        inRequest.setPartnerCode(partnerCode);
        inRequest.setMsisdn(msisdn);
        inRequest.setAmount(amount);
        inRequest.setReferenceNumber(referenceNumber);

        creditRequest = new CreditRequest();
        creditRequest.setPartnerCode(partnerCode);
        creditRequest.setMsisdn(msisdn);
        creditRequest.setAmount(amount);
        creditRequest.setReferenceNumber(referenceNumber);

        inCreditResponse = new INCreditResponse();
        inCreditResponse.setBalance(balance);
        inCreditResponse.setMsisdn(msisdn);
        inCreditResponse.setNarrative(narrative);
        inCreditResponse.setResponseCode(responseCode);

        creditResponse = new CreditResponse();
        creditResponse.setBalance(balance);
        creditResponse.setMsisdn(msisdn);
        creditResponse.setNarrative(narrative);
        creditResponse.setResponseCode(responseCode);

        inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setAmount(amount);
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setNarrative(narrative);
        inBalanceResponse.setResponseCode(responseCode);

        balanceResponse = new BalanceResponse();
        balanceResponse.setAmount(amount);
        balanceResponse.setMsisdn(msisdn);
        balanceResponse.setNarrative(narrative);
        balanceResponse.setResponseCode(responseCode);

    }

    @Test
    public void convertINCreditRequestShouldFailIfRequestIsNullOrEmpty() {
        INCreditRequest request = null;
        final CreditRequest response = MessageConverters.convert(request);
        assertNull(response);
    }

    @Test
    public void convertINCreditRequestShouldFPassIfRequestIsValid() {
        final CreditRequest response = MessageConverters.convert(inRequest);
        assertNotNull(response);
        assertEquals(inRequest.getAmount(),response.getAmount(),0.01);
        assertEquals(inRequest.getMsisdn(),response.getMsisdn());
        assertEquals(inRequest.getPartnerCode(),response.getPartnerCode());
        assertEquals(inRequest.getReferenceNumber(),response.getReferenceNumber());
    }

    @Test
    public void convertINCreditResponseShouldFailIfRequestIsNullOrEmpty() {
        INCreditResponse request = null;
        final CreditResponse response = MessageConverters.convert(request);
        assertNull(response);
    }

    @Test
    public void convertINCreditResponseShouldFPassIfRequestIsValid() {
        final CreditResponse response = MessageConverters.convert(inCreditResponse);
        assertNotNull(response);
        assertEquals(inCreditResponse.getBalance(),response.getBalance(),0.01);
        assertEquals(inCreditResponse.getMsisdn(),response.getMsisdn());
        assertEquals(inCreditResponse.getNarrative(),response.getNarrative());
        assertEquals(inCreditResponse.getResponseCode(),response.getResponseCode());
    }

    @Test
    public void convertINBalanceResponseShouldFailIfRequestIsNullOrEmpty() {
        INBalanceResponse request = null;
        final BalanceResponse response = MessageConverters.convert(request);
        assertNull(response);
    }

    @Test
    public void convertINBalanceResponseShouldFPassIfRequestIsValid() {
        final BalanceResponse response = MessageConverters.convert(inBalanceResponse);
        assertNotNull(response);
        assertEquals(inBalanceResponse.getAmount(),response.getAmount(),0.01);
        assertEquals(inBalanceResponse.getMsisdn(),response.getMsisdn());
        assertEquals(inBalanceResponse.getNarrative(),response.getNarrative());
        assertEquals(inBalanceResponse.getResponseCode(),response.getResponseCode());
    }
}