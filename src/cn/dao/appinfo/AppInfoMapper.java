package cn.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.pojo.AppInfo;

public interface AppInfoMapper {
	/**
	 * ������ѯ
	 * @param softwareName			��������
	 * @param queryStatus			app״̬id
	 * @param queryCategoryLevel1	����һ������id
	 * @param queryCategoryLevel2	������������id
	 * @param queryCategoryLevel3	������������id
	 * @param queryFlatformId		app����ƽ̨id
	 * @param devId					������id
	 * @param currentPage			��ǰҳ��
	 * @param pageSize				ÿҳ��ʾ����
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
	 * ��������ѯ����
	 * @param softwareName					��������
	 * @param queryStatus					app״̬id
	 * @param queryCategoryLevel1			����һ������id
	 * @param queryCategoryLevel2			������������id
	 * @param queryCategoryLevel3			������������id
	 * @param queryFlatformId				app����ƽ̨id
	 * @param devId							������id
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
	 * ������ѯ
	 * @param id apkId
	 * @param APKName	apk����
	 * @return
	 */
	public AppInfo getAppInfo(@Param(value="id")Integer id);
	
	/**
	 * ���app
	 */
	public int updateSatus(@Param(value="status")Integer status,@Param(value="id")Integer id);
}