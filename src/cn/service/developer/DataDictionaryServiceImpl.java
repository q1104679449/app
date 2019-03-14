package cn.service.developer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dao.datadictionary.DataDictionaryMapper;
import cn.pojo.DataDictionary;


@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
	
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	
	@Override
	public List<DataDictionary> getDataDictionaryList(String typeCode) {
		// TODO Auto-generated method stub
		return dataDictionaryMapper.getDataDictionaryList(typeCode);
	}
	
}
