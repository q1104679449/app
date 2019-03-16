package cn.service.backend;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dao.appinfo.AppInfoMapper;
import cn.dao.appversion.AppVersionMapper;
import cn.pojo.AppVersion;

@Service
public class AppVersionServiceImpl implements AppVersionService {

	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Resource
	private AppInfoMapper appInfoMapper;
	
	@Override
	public AppVersion getAppVersionById(Integer id) {
		
		return appVersionMapper.getAppVersionById(id);
	}

	@Override
	public List<AppVersion> getAppVersionByAppId(Integer appid) {
		// TODO Auto-generated method stub
		return appVersionMapper.getAppVersionByAppId(appid);
	}

	@Override
	public int addAppVersion(AppVersion appVersion) {
		// TODO Auto-generated method stub
		int num=0;
		int num1=0;
		int num2=0;
		try {
			
			System.out.println(appVersion.getAppId());
			num1=appVersionMapper.addAppVersion(appVersion);
			System.out.println(appVersion.getId());
			if(num1>0){
				
				num2=appInfoMapper.updateAppVersion(appVersion.getId(), appVersion.getAppId());
			}
			if(num1>0&&num2>0){
				num=1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}

}
