package cn.service.backend;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dao.appinfo.AppInfoMapper;
import cn.pojo.AppInfo;

@Service("appService")
public class AppServiceImpl implements AppService {
	
	@Resource
	private AppInfoMapper appInfoMapper;

	@Override
	public List<AppInfo> getAppInfoList(String querySoftwareName,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer queryFlatformId,
			Integer currentPageNo, Integer pageSize) {
		List<AppInfo> appInfos=null;
		// TODO Auto-generated method stub
		/*return appInfoMapper.getAppInfoList(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, 
	            queryCategoryLevel3, queryFlatformId, null, (currentPageNo-1)*pageSize, pageSize);*/
		return appInfos;
	}

	@Override
	public int getAppInfoCount(String querySoftwareName,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer queryFlatformId) {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoCount(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, 
				queryCategoryLevel3, queryFlatformId, null);
	}

	@Override
	public AppInfo getAppInfo(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfo(id);
	}

	@Override
	public int updateSatus(Integer status, Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateSatus(status, id);
	}
	
	
	
	
	


}
