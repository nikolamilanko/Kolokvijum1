package rzk;

import java.util.Date;

import javax.ejb.Remote;
import javax.jws.WebService;

@Remote
@WebService
public interface ServisBeanRemote {
	 public void getDataForOblast(String oblast);
}
