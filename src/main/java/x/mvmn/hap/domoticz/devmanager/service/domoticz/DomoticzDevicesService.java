package x.mvmn.hap.domoticz.devmanager.service.domoticz;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class DomoticzDevicesService {

	@Autowired
	protected Gson gson;

	@Autowired
	CloseableHttpClient httpClient;

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listDevices() {
		List<Map<String, Object>> result = null;
		// FIXME: FIX THIS!
		HttpGet get = new HttpGet("http://localhost:8080/json.htm?type=devices");
		try (CloseableHttpResponse response = httpClient.execute(get)) {
			Map<?, ?> responseAsMap = gson.fromJson(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), Map.class);
			result = (List<Map<String, Object>>) responseAsMap.get("result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getDeviceState(String idx) {
		String result = null;
		try {
			HttpGet get = new HttpGet("http://localhost:8080/json.htm?type=devices&rid=" + URLEncoder.encode(idx, StandardCharsets.UTF_8.name()));

			try (CloseableHttpResponse response = httpClient.execute(get)) {
				Map<?, ?> responseAsMap = gson.fromJson(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), Map.class);
				result = ((Map<?, ?>) ((List<?>) responseAsMap.get("result")).get(0)).get("Status").toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException use) {
			throw new RuntimeException(use);
		}
		return result;
	}

	public void setDeviceState(String idx, String svalue, Number nvalue) {
		try {
			StringBuilder url = new StringBuilder("http://localhost:8080/json.htm?type=command&param=udevice&idx=")
					.append(URLEncoder.encode(idx, StandardCharsets.UTF_8.name()));
			if (svalue != null) {
				url.append("&svalue=").append(URLEncoder.encode(svalue, StandardCharsets.UTF_8.name()));
			}
			if (nvalue != null) {
				url.append("&nvalue=").append(URLEncoder.encode(String.valueOf(nvalue), StandardCharsets.UTF_8.name()));
			}
			HttpGet get = new HttpGet(url.toString());

			try (CloseableHttpResponse response = httpClient.execute(get)) {
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException use) {
			throw new RuntimeException(use);
		}
	}
}
