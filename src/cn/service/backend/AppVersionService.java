package cn.service.backend;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppVersion;

public interface AppVersionService {
	/**
	 * ����id��ѯ�汾��Ϣ
	 * @param id
	 * @return
	 */
	public AppVersion getAppVersionById(@Param("id")Integer id);
}
