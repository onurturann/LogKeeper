package net.logkeeper.managedController;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.logkeeper.spring.service.ParameterService;
import net.logkeeper.spring.service.UserService;

@ManagedBean(name = "loginRest")
@SessionScoped
@Component
public class LoginRest implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
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


	public static void addMessageInfo(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public static void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void sessionRest() throws JSONException, IOException {
		NetClientGet ncg = new NetClientGet();
		JSONObject obj = new JSONObject(ncg.jsonData(getParameterService()));
		HttpSession session = SessionBean.getSession();
		session.setAttribute("username", obj.getString("name"));
		Register register = new Register();
		register.addRestRegister(getParameterService(),getUserService());
		addMessageInfo("Welcome", obj.getString("name"));
	}

	public void logout() {
		HttpSession session = SessionBean.getSession();
		session.invalidate();
	}
}
