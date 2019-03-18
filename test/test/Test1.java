package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.dao.appversion.AppVersionMapper;
import cn.pojo.AppInfo;
import cn.pojo.AppVersion;
import cn.service.backend.AppService;

public class Test1 {

	@Test
	public void test() {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("app*.xml");
		AppService as=(AppService) ctx.getBean("appService");
		AppInfo a=null;
		a=as.getAppInfo(48, null);
		System.out.println(a);
	}
	
	@Test
	public void test1(){
		ApplicationContext ctx=new ClassPathXmlApplicationContext("app*.xml");
		AppVersionMapper k = ctx.getBean(AppVersionMapper.class);
		List<AppVersion> appVersionByAppId = k.getAppVersionByAppId(59);
		for (AppVersion appVersion : appVersionByAppId) {
			System.out.println(appVersion);
		}
		
	}

}
