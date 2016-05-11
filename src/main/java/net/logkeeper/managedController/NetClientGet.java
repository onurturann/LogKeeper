package net.logkeeper.managedController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.logkeeper.spring.service.ParameterService;

@ManagedBean(name = "netClientGet")
@SessionScoped
public class NetClientGet implements Serializable {
	private static final long serialVersionUID = 4778576272893200307L;

	public NetClientGet() {
	}

	private static String jsonDataStr;

	public static String getJsonDataStr() {
		return jsonDataStr;
	}

	public static void setJsonDataStr(String jsonDataStr) {
		NetClientGet.jsonDataStr = jsonDataStr;
	}

	public InputStream restfulConn(ParameterService parameterService) {
		try {
			URL url = new URL(parameterService.findByParameter("JIRA_URL").getValue());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() == 404) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			System.out.println("Output from Server .... \n");
			return conn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String jsonData(ParameterService parameterService) {
		InputStream is = restfulConn(parameterService);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String jsonData = "";
		try {
			String line;
			while ((line = br.readLine()) != null) {
				jsonData += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		jsonDataStr=jsonData;
		return jsonData;
	}
}
