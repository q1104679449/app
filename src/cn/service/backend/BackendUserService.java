package cn.service.backend;


import cn.pojo.BackendUser;

public interface BackendUserService {
	public BackendUser getLoginUser(String userCode,String userPassword);
}
