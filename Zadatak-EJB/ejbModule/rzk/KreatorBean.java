package rzk;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;

import model.Conference;

/**
 * Session Bean implementation class KreatorBean
 */
@Stateless
@LocalBean
public class KreatorBean implements KreatorBeanRemote {

	@Inject
	JMSContext context;
	
	@Resource(mappedName="java:/jms/queue/conference")
	private Destination destination;
	
	
    /**
     * Default constructor. 
     */
    public KreatorBean() {
        // TODO Auto-generated constructor stub
    }
    
    public void sendMessageToQueue(String naziv, String drzava, String grad, String oblast, Date datumOd, Date datumDo) throws JMSException{
    	ObjectMessage objMessage = context.createObjectMessage();
    	Conference con = new Conference();
    	con.setCity(grad);
    	con.setCountry(drzava);
    	con.setTitle(naziv);
    	con.setField(oblast);
    	con.setDateFrom(datumOd);
    	con.setDateTo(datumDo);
    	objMessage.setObject(con);
    	
    	JMSProducer producer = context.createProducer();
    	producer.send(destination, objMessage);
    }

}
