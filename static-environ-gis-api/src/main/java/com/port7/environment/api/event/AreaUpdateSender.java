package com.port7.environment.api.event;

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

import com.port7.environment.model.event.AreaDataChangeEvent;

@Singleton
@LocalBean
public class AreaUpdateSender {
	@Resource(mappedName = "java:/JmsXA")
	private static ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "java:/static-environ-gis/area-updates")
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
	
	public void receiveEvent(@Observes AreaDataChangeEvent event) throws JMSException {
		MessageProducer producer = session.createProducer(topic);
		ObjectMessage message = session.createObjectMessage(event);
		producer.send(message);
	}
}
