package com.awesomeware.shipping.environment.api.event;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.awesomeware.shipping.environment.model.event.PortDataChangeEvent;

@Singleton
@LocalBean
public class PortUpdateSender {
	@Resource(mappedName = "java:/JmsXA")
	private static ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "java:/static-environ-gis/port-updates")
	private Destination topic;
	
	private Connection connection;
	
	private Session session;
	
	@PostConstruct
	public void initialise() throws JMSException {
		connection 	= connectionFactory.createConnection();
		session 	= connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
	}
	
	@PreDestroy
	public void tearDown() throws JMSException {
		session.close();
		connection.close();
	}
	
	public void receiveEvent(@Observes PortDataChangeEvent event) throws JMSException {
		MessageProducer producer = session.createProducer(topic);
		ObjectMessage message = session.createObjectMessage(event);
		producer.send(message);
		session.commit();
	}
}
