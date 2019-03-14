package cn.dao.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppCategory;

public interface AppCategoryMapper {
	
	/**
	 * 查询分类，根据父级id
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> getAppCategoryListByParentId(@Param("parentId")Integer parentId);
	
}
