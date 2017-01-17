package x.mvmn.hap.domoticz.devmanager.service.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import x.mvmn.hap.domoticz.devmanager.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Transactional
	@Modifying
	public int deleteByUsername(String username);

	public User findByUsername(String username);
}
