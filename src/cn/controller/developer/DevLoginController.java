package cn.controller.developer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.pojo.BackendUser;
import cn.pojo.DevUser;
import cn.service.backend.BackendUserService;
import cn.service.developer.DevUserService;
import cn.tools.Constants;

@Controller
@RequestMapping(value="/dev")
public class DevLoginController {
	
	
	@RequestMapping(value="/login")
	public String login(){
		return "devlogin";
	}
	
	@Resource
	private DevUserService devUserService;
	
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String devCode,@RequestParam String devPassword,HttpServletRequest request,HttpSession session){
		DevUser user=null;
		user=devUserService.login(devCode, devPassword);
		if(user!=null){
			session.setAttribute(Constants.DEV_USER_SESSION, user);
			System.out.println(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			return "redirect:/dev/flatform/main";
		}else{
			request.setAttribute("error", "用户名或密码错误！");
			return "devlogin";
		}
		
	}
	
	@RequestMapping(value="/flatform/main",method=RequestMethod.GET)
	public String main(HttpSession session){
		if(session.getAttribute(Constants.DEV_USER_SESSION)==null){
			return "redirect:/dev/login";
		}
		return "developer/main";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "devlogin";
	}
}
