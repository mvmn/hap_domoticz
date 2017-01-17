package x.mvmn.hap.domoticz.devmanager.service.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import x.mvmn.hap.domoticz.devmanager.model.DeviceDescriptor;

public interface DeviceDescriptorRepository extends CrudRepository<DeviceDescriptor, Integer> {

	public List<DeviceDescriptor> findByDomoticzHwIdx(int domoticzHwIdx);

	public DeviceDescriptor findByDomoticzHwIdxAndDomoticzIdx(int domoticzHwIdx, String domoticzIdx);

	public int deleteByDomoticzHwIdxAndDomoticzIdx(int domoticzHwIdx, String domoticzIdx);

}
