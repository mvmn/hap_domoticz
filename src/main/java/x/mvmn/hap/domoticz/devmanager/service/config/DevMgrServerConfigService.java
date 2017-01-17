package x.mvmn.hap.domoticz.devmanager.service.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.beowulfe.hap.HomekitServer;
import com.google.gson.Gson;

@Service
public class DevMgrServerConfigService {

	protected Gson gson = new Gson();

	protected String pinCode;
	protected byte[] key;
	protected BigInteger salt;
	protected String mac;

	public DevMgrServerConfigService() {
		try {
			File userHome = new File(System.getProperty("user.home"));
			File appHomeFolder = new File(userHome, ".hap_dmtcz_devmgr");
			appHomeFolder.mkdir();
			File mainConfig = new File(appHomeFolder, "confg.properties");
			Properties mainProps = new Properties();
			if (!mainConfig.exists()) {
				mainProps.put("key", gson.toJson(HomekitServer.generateKey()));
				mainProps.put("salt", gson.toJson(HomekitServer.generateSalt().toByteArray()));
				mainProps.put("mac", HomekitServer.generateMac());
				mainProps.put("pin", "123-45-678");
				try (FileOutputStream propsFileInputStream = new FileOutputStream(mainConfig)) {
					mainProps.store(propsFileInputStream, " x.mvmn.hap.domoticz.devmanager.DevMgrServer config");
				}
			} else {
				try (FileInputStream propsFileInputStream = new FileInputStream(mainConfig)) {
					mainProps.load(propsFileInputStream);
				}
			}
			read(mainProps);
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize config", e);
		}
	}

	protected void read(Properties props) {
		this.mac = props.getProperty("mac");
		this.pinCode = props.getProperty("pin");
		this.salt = new BigInteger(gson.fromJson(props.getProperty("salt"), byte[].class));
		this.key = gson.fromJson(props.getProperty("key"), byte[].class);
	}

	public String getPinCode() {
		return pinCode;
	}

	public byte[] getPrivateKey() {
		return key;
	}

	public BigInteger getSalt() {
		return salt;
	}

	public String getMac() {
		return mac;
	}
}
