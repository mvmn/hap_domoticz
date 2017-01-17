package x.mvmn.hap.domoticz.devmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.mvmn.hap.domoticz.devmanager.model.DeviceDescriptor;
import x.mvmn.hap.domoticz.devmanager.model.homekit.HomekitSwitch;
import x.mvmn.hap.domoticz.devmanager.service.domoticz.DomoticzDevicesService;
import x.mvmn.hap.domoticz.devmanager.service.hap.HomeKitService;
import x.mvmn.hap.domoticz.devmanager.service.persistence.DeviceDescriptorRepository;

@RestController
@RequestMapping(value = "/devices")
public class DeviceController {

	@Autowired
	DomoticzDevicesService domoticzDevicesService;

	@Autowired
	DeviceDescriptorRepository deviceDescriptorRepository;

	@Autowired
	HomeKitService homekitService;

	@RequestMapping(path = "/domoticz/lookup")
	public List<Map<String, Object>> lookupInDomoticz() {
		return domoticzDevicesService.listDevices();
	}

	@RequestMapping(path = "/domoticz/fetch")
	public List<DeviceDescriptor> fetchFromDomotics() {
		List<Map<String, Object>> domoticzDevices = domoticzDevicesService.listDevices();

		ArrayList<DeviceDescriptor> result = new ArrayList<>();
		for (Map<String, Object> domoticzDevice : domoticzDevices) {
			int domoticzHwIdx = ((Number) domoticzDevice.get("HardwareID")).intValue();
			String domoticzIdx = domoticzDevice.get("idx").toString();
			String domoticzType = domoticzDevice.get("Type").toString();
			String domoticzDeviceName = domoticzDevice.get("Name").toString();
			DeviceDescriptor device = deviceDescriptorRepository.findByDomoticzHwIdxAndDomoticzIdx(domoticzHwIdx, domoticzIdx);
			if (device == null || !device.getDomoticzType().equals(domoticzType)) {
				if (device != null) {
					deviceDescriptorRepository.delete(device);
				}
				device = new DeviceDescriptor();
				device.setDomoticzDeviceName(domoticzDeviceName);
				device.setDomoticzHwIdx(domoticzHwIdx);
				device.setDomoticzIdx(domoticzIdx);
				device.setDomoticzType(domoticzType);
				device = deviceDescriptorRepository.save(device);
				if (device.getDomoticzType().equals("Light/Switch")) {
					homekitService.addAccessory(new HomekitSwitch(device, domoticzDevicesService));
				}
			}
			result.add(device);
		}

		return result;
	}
}
