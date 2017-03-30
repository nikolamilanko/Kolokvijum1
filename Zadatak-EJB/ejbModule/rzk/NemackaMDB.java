package rzk;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import model.Conference;

/**
 * Message-Driven Bean implementation class for: NemackaMDB
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms/topic/confinfo"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "java:/jms/topic/confinfo")
public class NemackaMDB implements MessageListener {

    /**
     * Default constructor. 
     */
    public NemackaMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        ObjectMessage objMessage = (ObjectMessage) message;
        Conference con;
		try {
			con = (Conference) objMessage.getObject();
			 if(con.getCountry().equals("Germany")){
		        	System.out.println("Germany!!!!");
		        }
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        
    }

}
