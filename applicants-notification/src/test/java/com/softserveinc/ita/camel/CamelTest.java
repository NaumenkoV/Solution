package com.softserveinc.ita.camel;

import com.softserveinc.ita.entity.NotificationJSONInfo;
import com.softserveinc.ita.service.QueueManager;
import org.apache.camel.BeanInject;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CamelTest extends CamelSpringTestSupport {

    private static final String producerUri = "activemq:notification.queue";
    private static final String consumerMockUri = "mock:notification";
    private NotifyBuilder notify;
    private List<NotificationJSONInfo> messageList;

    @BeanInject("queueManager")
    private QueueManager queueManager;

    @EndpointInject(uri = consumerMockUri)
    private MockEndpoint consumerMock;

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(producerUri).to(consumerMockUri);
            }
        };
    }

    protected AbstractXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext(
                "/test-camel-config.xml");
    }

    @Before
    public void setup() {
        NotificationJSONInfo info1 = new NotificationJSONInfo("applicantID_1", "groupID_1", "hrID_1");
        NotificationJSONInfo info2 = new NotificationJSONInfo("applicantID_2", "groupID_2", "hrID_2");
        NotificationJSONInfo info3 = new NotificationJSONInfo("applicantID_3", "groupID_3", "hrID_3");
        NotificationJSONInfo info4 = new NotificationJSONInfo("applicantID_4", "groupID_4", "hrID_4");
        NotificationJSONInfo info5 = new NotificationJSONInfo("applicantID_5", "groupID_5", "hrID_5");
        NotificationJSONInfo info6 = new NotificationJSONInfo("applicantID_6", "groupID_6", "hrID_6");
        NotificationJSONInfo info7 = new NotificationJSONInfo("applicantID_7", "groupID_7", "hrID_7");
        NotificationJSONInfo info8 = new NotificationJSONInfo("applicantID_8", "groupID_8", "hrID_8");
        NotificationJSONInfo info9 = new NotificationJSONInfo("applicantID_9", "groupID_9", "hrID_9");
        NotificationJSONInfo info10 = new NotificationJSONInfo("applicantID_10", "groupID_10", "hrID_10");

        messageList = new ArrayList<>();
        messageList.add(info1);
        messageList.add(info2);
        messageList.add(info3);
        messageList.add(info4);
        messageList.add(info5);
        messageList.add(info6);
        messageList.add(info7);
        messageList.add(info8);
        messageList.add(info9);
        messageList.add(info10);
        notify = new NotifyBuilder(context).from(producerUri).whenDone(messageList.size()).create();  //whenDone(?) -> expected number of messages.

    }

    @Test
    public void testServiceProducesCorrectNumberOfMessages() throws Exception {
        consumerMock.expectedMessageCount(messageList.size());
        queueManager.notifyApplicants(messageList);
        boolean matches = notify.matches(5, TimeUnit.SECONDS);        //wait 5s for messages to be consumed
        assertTrue(matches);
        consumerMock.assertIsSatisfied();
    }

//    @Test
//    public void testServiceProducesCorrectMessageBodies() throws Exception {
//        consumerMock.expectedBodiesReceivedInAnyOrder(messageList);
//        queueManager.notifyApplicants(messageList);
//        boolean matches = notify.matches(5, TimeUnit.SECONDS);
//        assertTrue(matches);
//        consumerMock.assertIsSatisfied();
//    }
}
