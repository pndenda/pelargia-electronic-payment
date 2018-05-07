package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.services.api.ReportingService;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ReportingServiceImplTest {

    @Mock
    private SubscriberRequestDao subscriberRequestDao;

    @InjectMocks
    ReportingService service = new ReportingServiceImpl(subscriberRequestDao);

    private List<SubscriberRequest> requestList;
    private SubscriberRequest subscriberRequest;
    private String partnerCode;

    @Before
    public void setUp() throws Exception {
        partnerCode = "PARTNER-CODE-001";

        subscriberRequest = new SubscriberRequest();
        subscriberRequest.setPartnerCode(partnerCode);

        requestList = new ArrayList<>();
        requestList.add(subscriberRequest);
    }

    @Test
    public void findSubscriberRequestsByPartnerCodeShouldReturnEmptyListIfNoRequestsWasMade() {
        partnerCode = "INVALID-CODE";
        List<SubscriberRequest> response = service.findSubscriberRequestsByPartnerCode(partnerCode);
        assertNotNull("request should return a non-null response", response);
        assertTrue("response should be empty", response.isEmpty());
    }

    @Test
    public void findSubscriberRequestsByPartnerCodeShouldReturnRequestListIfValidCodeIsProvided() {
        when(subscriberRequestDao.findByPartnerCode(partnerCode)).thenReturn(requestList);
        List<SubscriberRequest> response = service.findSubscriberRequestsByPartnerCode(partnerCode);
        assertNotNull("request should return a non-null response", response);
        assertTrue("response should NOT be empty", !response.isEmpty());

        verify(subscriberRequestDao, times(1)).findByPartnerCode(partnerCode);
    }
}
