package cn.service.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.pojo.AppCategory;

public interface AppCategoryService {
	/**
	 * 查询分类，根据父级id
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> getAppCategoryListByParentId(@Param("parentId")Integer parentId);
}
