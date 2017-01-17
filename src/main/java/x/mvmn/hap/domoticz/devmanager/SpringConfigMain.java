package x.mvmn.hap.domoticz.devmanager;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan("x.mvmn.hap.domoticz.devmanager")
public class SpringConfigMain {

	@Bean
	@Scope("singleton")
	public Gson gson() {
		return new Gson();
	}

	@Bean
	@Scope("singleton")
	public CloseableHttpClient httpClient() {
		CloseableHttpClient result = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager()).build();

		return result;
	}
}
