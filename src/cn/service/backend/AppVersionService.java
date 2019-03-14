package cn.service.backend;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppVersion;

public interface AppVersionService {
	/**
	 * 根据id查询版本信息
	 * @param id
	 * @return
	 */
	public AppVersion getAppVersionById(@Param("id")Integer id);
}
