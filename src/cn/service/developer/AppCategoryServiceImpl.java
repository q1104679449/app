package cn.service.developer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dao.appcategory.AppCategoryMapper;
import cn.pojo.AppCategory;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {
	
	@Resource
	private AppCategoryMapper appCategoryMapper;
	@Override
	public List<AppCategory> getAppCategoryListByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return appCategoryMapper.getAppCategoryListByParentId(parentId);
	}

}
