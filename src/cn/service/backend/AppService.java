package cn.service.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppInfo;

public interface AppService {
	/**
	 * 根据条件查询出待审核的APP列表并分页显示
	 * @param querySoftwareName
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<AppInfo> getAppInfoList(@Param(value="softwareName")String querySoftwareName,
										@Param(value="categoryLevel1")Integer queryCategoryLevel1,
										@Param(value="categoryLevel2")Integer queryCategoryLevel2,
										@Param(value="categoryLevel3")Integer queryCategoryLevel3,
										@Param(value="flatformId")Integer queryFlatformId,
										@Param(value="from")Integer currentPageNo,
										@Param(value="pageSize")Integer pageSize);
	/**
	 * 查询出待审核（status=1）的APP数量
	 * @param querySoftwareName
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @return
	 * @throws Exception
	 */
	public int getAppInfoCount(@Param(value="softwareName")String querySoftwareName,
							   @Param(value="categoryLevel1")Integer queryCategoryLevel1,
							   @Param(value="categoryLevel2")Integer queryCategoryLevel2,
							   @Param(value="categoryLevel3")Integer queryCategoryLevel3,
							   @Param(value="flatformId")Integer queryFlatformId);
	
	/**
	 * 条件查询
	 * @param id apkId
	 * @param APKName	apk名称
	 * @return
	 */
	public AppInfo getAppInfo(Integer id,String APKName);
	
	/**
	 * 审核app
	 */
	public int updateSatus(@Param(value="status")Integer status,@Param(value="id")Integer id);
	
}
