package x.mvmn.hap.domoticz.devmanager.service.hap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beowulfe.hap.HomekitRoot;
import com.beowulfe.hap.HomekitServer;

@Service
public class HomeKitService {

	@Autowired
	protected HomekitAuthService auth;

	protected HomekitRoot homekitBridge;

	@PostConstruct
	public void start() {
		try {
			// FIXME: hardcode, no lifecycle management etc
			HomekitServer homekitServer = new HomekitServer(9123);
			homekitBridge = homekitServer.createBridge(auth, "Mock", "MMakhin", "Alpha0.1", "223322223322");
			// homekitBridge.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HomekitRoot getHomekitBridge() {
		return homekitBridge;
	}
}
