package rzk;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Conference;
import net.webservicex.Country;
import net.webservicex.CountrySoap;

/**
 * Message-Driven Bean implementation class for: ConferenceMDB
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/conference"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "java:/jms/queue/conference")
public class ConferenceMDB implements MessageListener {

	@Inject
	JMSContext context;

	@Resource(mappedName = "java:/jms/topic/confinfo")
	private Destination destinationTopic;

	@PersistenceContext
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public ConferenceMDB() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			Conference con = (Conference) objectMessage.getObject();

			Country country = new Country();
			CountrySoap countrySoap = country.getCountrySoap();

			String countryName = con.getCountry();

			String temp = countrySoap.getCurrencyByCountry(countryName);
			String temp2 = countrySoap.getISD(countryName);

			String[] stringovi = temp.split("<CountryCode>");
			String[] stringovi2 = stringovi[1].split("</CountryCode>");

			String[] stringovi3 = temp.split("<Currency>");
			String[] stringovi4 = stringovi3[1].split("</Currency>");

			String[] stringovi5 = temp2.split("<code>");
			String[] stringovi6 = stringovi5[1].split("</code>");

			con.setCountryCode(stringovi2[0]);
			con.setCurrency(stringovi4[0]);
			con.setDialingCode(stringovi6[0]);

			em.persist(con);

			JMSProducer produce = context.createProducer();
			produce.send(destinationTopic, objectMessage);

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
