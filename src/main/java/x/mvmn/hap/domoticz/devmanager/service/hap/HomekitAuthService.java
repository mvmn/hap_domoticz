package x.mvmn.hap.domoticz.devmanager.service.hap;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beowulfe.hap.HomekitAuthInfo;

import x.mvmn.hap.domoticz.devmanager.model.User;
import x.mvmn.hap.domoticz.devmanager.service.config.DevMgrServerConfigService;
import x.mvmn.hap.domoticz.devmanager.service.persistence.UserRepository;

@Service
public class HomekitAuthService implements HomekitAuthInfo {

	@Autowired
	DevMgrServerConfigService devMgrServerConfigService;

	@Autowired
	UserRepository userRepository;

	@Override
	public String getPin() {
		return devMgrServerConfigService.getPinCode();
	}

	@Override
	public String getMac() {
		return devMgrServerConfigService.getMac();
	}

	@Override
	public BigInteger getSalt() {
		return devMgrServerConfigService.getSalt();
	}

	@Override
	public byte[] getPrivateKey() {
		return devMgrServerConfigService.getPrivateKey();
	}

	@Override
	public void createUser(String username, byte[] publicKey) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			user = new User();
			user.setUsername(username);
		}
		user.setPublicKey(publicKey);
		userRepository.save(user);
	}

	@Override
	public void removeUser(String username) {
		userRepository.deleteByUsername(username);
	}

	@Override
	public byte[] getUserPublicKey(String username) {
		User user = userRepository.findByUsername(username);
		return user != null ? user.getPublicKey() : null;
	}
}
