package cn.service.backend;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dao.appversion.AppVersionMapper;
import cn.pojo.AppVersion;

@Service
public class AppVersionServiceImpl implements AppVersionService {

	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Override
	public AppVersion getAppVersionById(Integer id) {
		
		return appVersionMapper.getAppVersionById(id);
	}

}
