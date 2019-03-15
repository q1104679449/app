package cn.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.pojo.AppInfo;

public interface AppInfoMapper {
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
	public List<Appinfo> getAppInfoList(@Param(value="softwareName")String querySoftwareName,
			@Param(value="status")Integer queryStatus,
			@Param(value="categoryLevel1")Integer queryCategoryLevel1,
			@Param(value="categoryLevel2")Integer queryCategoryLevel2,
			@Param(value="categoryLevel3")Integer queryCategoryLevel3,
			@Param(value="flatformId")Integer queryFlatformId,
			@Param(value="devId")Integer devId,
			@Param(value="from")Integer currentPageNo,
			@Param(value="pageSize")Integer pageSize);
	
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
	public int getAppInfoCount(@Param(value="softwareName")String querySoftwareName,
			   @Param(value="status")Integer queryStatus,
			   @Param(value="categoryLevel1")Integer queryCategoryLevel1,
			   @Param(value="categoryLevel2")Integer queryCategoryLevel2,
			   @Param(value="categoryLevel3")Integer queryCategoryLevel3,
			   @Param(value="flatformId")Integer queryFlatformId,
			   @Param(value="devId")Integer devId);
	
	/**
	 * 条件查询
	 * @param id apkId
	 * @param APKName	apk名称
	 * @return
	 */
	public AppInfo getAppInfo(@Param(value="id")Integer id,@Param(value="APKName")String APKName);
	
	/**
	 * 审核app
	 */
	public int updateSatus(@Param(value="status")Integer status,@Param(value="id")Integer id);
}
