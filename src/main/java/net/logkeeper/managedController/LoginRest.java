package net.logkeeper.managedController;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.logkeeper.spring.service.ParameterService;
import net.logkeeper.spring.service.UserService;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

import sun.security.krb5.internal.NetClient;

@ManagedBean(name = "loginRest")
@SessionScoped
@Component
public class LoginRest implements Serializable {
	private Logger logger = Logger.getLogger(LoginRest.class);
	private static final long serialVersionUID = 1094801825228386363L;
	JSONObject resp1;
	JSONObject resp2;
	String avatarUrl;
	private String txtUsername;
	private String txtpassword;
	public static String jsessionId;
	JSONObject kullaniciBilgileri;
	
	@Autowired
	public ParameterService parameterService;

	@Autowired
	private UserService userService;	
	
	
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}
	
	public String getUsername() {
		return txtUsername;
	}

	public void setUsername(String username) {
		this.txtUsername = username;
	}

	public String getPassword() {
		return txtpassword;
	}

	public void setPassword(String password) {
		this.txtpassword = password;
	}

	public static void addMessageInfo(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public static void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void sessionRest() throws JSONException, IOException {
				
		NetClientGet netClientGet = new NetClientGet();
		NetClientGet ncg = new NetClientGet();
		HttpSession session = SessionBean.getSession();
		Register register = new Register();
		kullaniciBilgileri=new JSONObject();
		String donenJson=null;
		kullaniciBilgileri.put("username",txtUsername);
		kullaniciBilgileri.put("password",txtpassword);

		donenJson=netClientGet.connectionRest("POST", kullaniciBilgileri, "http://jira.genband.com:8080/rest/auth/1/session");
		
		if(donenJson==null)
		{
			addMessageInfo("Login failed!!"," ");
		}
		
		else
		{
		JSONObject donenJsonObject=new JSONObject(donenJson);
		jsessionId=donenJsonObject.getJSONObject("session").getString("value");
		session.setAttribute("jsessionId", jsessionId);
		resp1 = new JSONObject(ncg.jsonData(getParameterService(), parameterService.findByParameter("JIRA_URL").getValue(),jsessionId));
		resp2 = new JSONObject(ncg.jsonData(getParameterService(), resp1.getString("self"),jsessionId));
				
		session.setAttribute("username", resp2.getString("name"));
		
		addMessageInfo("Welcome", resp2.getString("displayName"));
		register.addRestRegister(getParameterService(),getUserService());
		avatarUrl=resp2.getJSONObject("avatarUrls").getString("24x24");
		session.setAttribute("avatar24x24", avatarUrl);
		imageLoading();		
	
		}
		
	}
	
	public void imageLoading()
	{
		GraphicImage graphicImage=new GraphicImage();
		try 
		{
			graphicImage.getImage();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void logout() 
	{
		NetClientGet netClientGet=new NetClientGet();
		netClientGet.restfulConn("DELETE", getParameterService(), "http://jira.genband.com:8080/rest/auth/1/session", jsessionId);
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		
	}
}
