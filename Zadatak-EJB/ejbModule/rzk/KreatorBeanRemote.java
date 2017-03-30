package rzk;

import java.util.Date;

import javax.ejb.Remote;
import javax.jms.JMSException;

@Remote
public interface KreatorBeanRemote {

	public void sendMessageToQueue(String naziv, String drzava, String grad, String oblast, Date datumOd, Date datumDo) throws JMSException;
}