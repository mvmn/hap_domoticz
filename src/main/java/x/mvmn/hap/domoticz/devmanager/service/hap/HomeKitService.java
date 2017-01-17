package x.mvmn.hap.domoticz.devmanager.service.hap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.HomekitRoot;
import com.beowulfe.hap.HomekitServer;

import x.mvmn.hap.domoticz.devmanager.model.DeviceDescriptor;
import x.mvmn.hap.domoticz.devmanager.model.homekit.HomekitSwitch;
import x.mvmn.hap.domoticz.devmanager.service.domoticz.DomoticzDevicesService;
import x.mvmn.hap.domoticz.devmanager.service.persistence.DeviceDescriptorRepository;

@Service
public class HomeKitService {

	@Autowired
	protected HomekitAuthService auth;

	@Autowired
	protected DomoticzDevicesService domoticzDevicesService;

	@Autowired
	protected DeviceDescriptorRepository deviceDescriptorRepository;

	protected volatile HomekitRoot homekitBridge;

	@PostConstruct
	public void start() {
		try {
			// FIXME: hardcode, no lifecycle management etc
			HomekitServer homekitServer = new HomekitServer(9123);
			homekitBridge = homekitServer.createBridge(auth, "Mock", "MMakhin", "Alpha0.1", "223322223322");
			// homekitBridge.start();
			for (DeviceDescriptor device : deviceDescriptorRepository.findAll()) {
				if (device.getDomoticzType().equals("Light/Switch")) {
					homekitBridge.addAccessory(new HomekitSwitch(device, domoticzDevicesService));
				}
			}
			homekitBridge.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addAccessory(HomekitAccessory accessory) {
		homekitBridge.addAccessory(accessory);
	}
}
