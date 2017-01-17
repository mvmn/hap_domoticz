package x.mvmn.hap.domoticz.devmanager.model.homekit;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Switch;

import x.mvmn.hap.domoticz.devmanager.model.DeviceDescriptor;
import x.mvmn.hap.domoticz.devmanager.service.domoticz.DomoticzDevicesService;

public class HomekitSwitch implements Switch {

	protected final DeviceDescriptor descriptor;
	protected final DomoticzDevicesService domoticzDevicesService;
	protected volatile HomekitCharacteristicChangeCallback callback;

	public HomekitSwitch(DeviceDescriptor descriptor, DomoticzDevicesService domoticzDevicesService) {
		this.descriptor = descriptor;
		this.domoticzDevicesService = domoticzDevicesService;
	}

	@Override
	public int getId() {
		return descriptor.getId();
	}

	@Override
	public String getLabel() {
		return descriptor.getDomoticzDeviceName();
	}

	@Override
	public void identify() {
		// TODO: what?
	}

	@Override
	public String getSerialNumber() {
		return "" + descriptor.getDomoticzHwIdx() + "::" + descriptor.getDomoticzIdx();
	}

	@Override
	public String getModel() {
		return "Domoticz";
	}

	@Override
	public String getManufacturer() {
		return "Domoticz";
	}

	@Override
	public void subscribeSwitchState(HomekitCharacteristicChangeCallback callback) {
		this.callback = callback;
	}

	@Override
	public void unsubscribeSwitchState() {
		this.callback = null;
	}

	@Override
	public CompletableFuture<Boolean> getSwitchState() {
		return CompletableFuture.supplyAsync(new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				return domoticzDevicesService.getDeviceState(HomekitSwitch.this.descriptor.getDomoticzIdx()).equalsIgnoreCase("on");
			}
		});
	}

	@Override
	public CompletableFuture<Void> setSwitchState(boolean state) throws Exception {
		return CompletableFuture.runAsync(new Runnable() {
			public void run() {
				domoticzDevicesService.setDeviceState(HomekitSwitch.this.descriptor.getDomoticzIdx(), null, state ? 1 : 0);
				if (callback != null) {
					try {
						callback.changed();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			}
		});
	}
}
