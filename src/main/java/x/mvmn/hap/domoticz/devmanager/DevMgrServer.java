package x.mvmn.hap.domoticz.devmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevMgrServer {

	public static void main(String args[]) throws Exception {
		SpringApplication.run(SpringConfigMain.class, args);
	}
}
