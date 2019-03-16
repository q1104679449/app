package cn.service.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppVersion;

public interface AppVersionService {
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
	public int addAppVersion(@Param("AppVersion")AppVersion AppVersion);
}
