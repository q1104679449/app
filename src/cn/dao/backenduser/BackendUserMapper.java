package cn.dao.backenduser;

import org.apache.ibatis.annotations.Param;

import cn.pojo.BackendUser;

public interface BackendUserMapper {
	public BackendUser getLoginUser(@Param("userCode")String userCode);
}
