package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.pojo.AppInfo;
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

}
