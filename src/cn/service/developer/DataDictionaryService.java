package cn.service.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.pojo.DataDictionary;

public interface DataDictionaryService {
	public List<DataDictionary> getDataDictionaryList(@Param("typeCode") String typeCode);
}
