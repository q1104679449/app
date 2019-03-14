package cn.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.pojo.BackendUser;
import cn.service.backend.BackendUserService;
import cn.tools.Constants;


@Controller
@RequestMapping(value="/manager")
public class UserLoginController {
	
	@RequestMapping(value="/login")
	public String login(){
		return "backendlogin";
	}
	@Resource
	private BackendUserService backendUserService;
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode,@RequestParam String userPassword,HttpServletRequest request,HttpSession session){
		BackendUser user=null;
		user=backendUserService.getLoginUser(userCode, userPassword);
		if(user!=null){
			session.setAttribute(Constants.USER_SESSION, user);
		}
		return "redirect:/manager/backend/main";
	}
	
	@RequestMapping(value="/backend/main")
	public String main(HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION)==null){
			return "redirect:/manager/login";
		}
		return "backend/main";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "backendlogin";
	}
}
