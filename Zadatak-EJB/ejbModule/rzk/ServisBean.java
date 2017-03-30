package rzk;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Conference;

/**
 * Session Bean implementation class ServisBean
 */
@Stateless
@LocalBean
@WebService
public class ServisBean implements ServisBeanRemote {

	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ServisBean() {
        // TODO Auto-generated constructor stub
    }
    public void getDataForOblast(String oblast){
    	TypedQuery<Conference> query = em.createQuery("select c from Conference c where c.field=:oblast",Conference.class);
    	query.setParameter("oblast",oblast);
    	ArrayList<Conference> conferences = (ArrayList<Conference>) query.getResultList();
    	for(Conference c: conferences){
    		System.out.println(c.getCountry()+" "+c.getCity());
    	}
    		
    	
    }

}
