package x.mvmn.hap.domoticz.devmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DeviceDescriptor {

	protected int id;
	protected int domoticzHwIdx;
	protected String domoticzIdx;
	protected String domoticzDeviceName;
	protected String domoticzType;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDomoticzHwIdx() {
		return domoticzHwIdx;
	}

	public void setDomoticzHwIdx(int domoticzHwIdx) {
		this.domoticzHwIdx = domoticzHwIdx;
	}

	public String getDomoticzIdx() {
		return domoticzIdx;
	}

	public void setDomoticzIdx(String domoticzIdx) {
		this.domoticzIdx = domoticzIdx;
	}

	public String getDomoticzDeviceName() {
		return domoticzDeviceName;
	}

	public void setDomoticzDeviceName(String domoticzDeviceName) {
		this.domoticzDeviceName = domoticzDeviceName;
	}

	public String getDomoticzType() {
		return domoticzType;
	}

	public void setDomoticzType(String domoticzType) {
		this.domoticzType = domoticzType;
	}
}
