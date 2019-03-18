package cn.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppVersion;

public interface AppVersionMapper {
	
	/**
	 * 根据id查询版本信息
	 * @param id
	 * @return
	 */
	public AppVersion getAppVersionById(@Param("id")Integer id);
	
	
	/**
	 * 根据appid查询历史版本
	 * @param appid
	 * @return
	 */
	public List<AppVersion> getAppVersionByAppId(@Param("appid")Integer appid);
	
	/**
	 * 根据实体添加版本信息
	 * @param AppVersion
	 * @return
	 */
	public int addAppVersion(AppVersion appVersion);
	
	/**
	 * 修改
	 * @param appVersion
	 * @return
	 */
	public int modify(AppVersion appVersion);
	
	/**
	 * 查询跟appid相关的所有版本记录数
	 * @param id
	 * @return
	 */
	public int getCountByAppId(@Param("id")Integer id);
	
	/**
	 * 删除与appid有关的版本信息
	 * @param id
	 * @return
	 */
	public int delByAppId(@Param("id")Integer id);
	

}
