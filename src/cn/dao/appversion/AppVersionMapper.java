package cn.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppVersion;

public interface AppVersionMapper {
	
	/**
	 * ����id��ѯ�汾��Ϣ
	 * @param id
	 * @return
	 */
	public AppVersion getAppVersionById(@Param("id")Integer id);
	
	
	/**
	 * ����appid��ѯ��ʷ�汾
	 * @param appid
	 * @return
	 */
	public List<AppVersion> getAppVersionByAppId(@Param("appid")Integer appid);
	
	/**
	 * ����ʵ����Ӱ汾��Ϣ
	 * @param AppVersion
	 * @return
	 */
	public int addAppVersion(AppVersion appVersion);
	

}
