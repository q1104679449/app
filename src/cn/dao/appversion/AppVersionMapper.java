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

}
