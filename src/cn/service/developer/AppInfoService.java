package cn.service.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppInfo;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

public interface AppInfoService {
	/**
	 * 条件查询
	 * @param softwareName			软件名称
	 * @param queryStatus			app状态id
	 * @param queryCategoryLevel1	所属一级分类id
	 * @param queryCategoryLevel2	所属二级分类id
	 * @param queryCategoryLevel3	所属三级分类id
	 * @param queryFlatformId		app所属平台id
	 * @param devId					开发者id
	 * @param currentPage			当前页码
	 * @param pageSize				每页显示条数
	 * @return
	 */
	public List<Appinfo> getAppInfoList(String querySoftwareName,Integer queryStatus,
			Integer queryCategoryLevel1,Integer queryCategoryLevel2,
			Integer queryCategoryLevel3,Integer queryFlatformId,
			Integer devId,Integer currentPageNo,Integer pageSize);
	
	/**
	 * 按条件查询总数
	 * @param softwareName					软件名称
	 * @param queryStatus					app状态id
	 * @param queryCategoryLevel1			所属一级分类id
	 * @param queryCategoryLevel2			所属二级分类id
	 * @param queryCategoryLevel3			所属三级分类id
	 * @param queryFlatformId				app所属平台id
	 * @param devId							开发者id
	 * @return
	 */
	public int getAppInfoCount(String querySoftwareName, Integer queryStatus,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId);
	
	
	/**
	 * 添加app信息
	 * @param appInfo
	 * @return
	 */
	public int addAppInfo(AppInfo appInfo);
	
	
	/**
	 * 修改状态
	 * @param appInfo
	 * @return
	 */
	public boolean updateStatusByAppId(AppInfo appInfo);
	
	
	/**
	 * 修改
	 * @param appInfo
	 * @return
	 */
	public int modfi(AppInfo appInfo);
	
	/**
	 * 删除appinfo
	 * @param id
	 * @return
	 */
	public boolean delAppInfo(Integer id);
	
	
}
