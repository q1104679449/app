package cn.service.backend;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dao.backenduser.BackendUserMapper;
import cn.pojo.BackendUser;

@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {
	
	@Resource
	private BackendUserMapper backendUserMapper;
	
	@Override
	public BackendUser getLoginUser(String userCode, String userPassword) {
		// TODO Auto-generated method stub
		BackendUser user =null;
		user=backendUserMapper.getLoginUser(userCode);
		if(user!=null){
			if(!user.getUserPassword().equals(userPassword)){
				user=null;
			}
		}
		return user;
	}

}
