package cn.controller.backend;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.pojo.AppCategory;
import cn.pojo.AppInfo;
import cn.pojo.AppVersion;
import cn.pojo.DataDictionary;
import cn.service.backend.AppService;
import cn.service.backend.AppVersionService;
import cn.service.developer.AppCategoryService;
import cn.service.developer.AppInfoService;
import cn.service.developer.DataDictionaryService;
import cn.tools.Constants;
import cn.tools.PageSupport;

@Controller
@RequestMapping(value="/manager/backend/app")
public class AppCheckController {
	
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	@Resource
	private AppService appService;
	
	@Resource
	private AppVersionService appVersionService;
	
	@RequestMapping(value="/list")
	public String getAppInfoList(Model model,HttpSession session,
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryCategoryLevel1",required=false) String _queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false) String _queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false) String _queryCategoryLevel3,
			@RequestParam(value="queryFlatformId",required=false) String _queryFlatformId,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		List<Appinfo> appInfoList = null;
		List<DataDictionary> statusList = null;			//状态信息List
		List<DataDictionary> flatFormList = null;		//所属平台List
		List<AppCategory> categoryLevel1List = null;//列出一级分类列表，注：二级和三级分类列表通过异步ajax获取
		List<AppCategory> categoryLevel2List = null;
		List<AppCategory> categoryLevel3List = null;
		//页面容量
		int pageSize = Constants.pageSize;
		//当前页码
		Integer currentPageNo = 1;
		
		if(pageIndex != null){
			try{
				currentPageNo = Integer.valueOf(pageIndex);
			}catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		Integer queryCategoryLevel1 = null;
		if(_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")){
			queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
		}
		Integer queryCategoryLevel2 = null;
		if(_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")){
			queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
		}
		Integer queryCategoryLevel3 = null;
		if(_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")){
			queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
		}
		Integer queryFlatformId = null;
		if(_queryFlatformId != null && !_queryFlatformId.equals("")){
			queryFlatformId = Integer.parseInt(_queryFlatformId);
		}
		
		//总数量（表）
		int totalCount = 0;
		try {
			totalCount = appInfoService.getAppInfoCount(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		//控制首页和尾页
		if(currentPageNo < 1){
			currentPageNo = 1;
		}else if(currentPageNo > totalPageCount){
			currentPageNo = totalPageCount;
		}
		try {
			appInfoList = appInfoService.getAppInfoList(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, null, currentPageNo, pageSize);
			statusList = this.getDataDictionaryList("APP_STATUS");
			flatFormList = this.getDataDictionaryList("APP_FLATFORM");
			categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("pages", pages);
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		model.addAttribute("queryFlatformId", queryFlatformId);
		
		//二级分类列表和三级分类列表---回显
		if(queryCategoryLevel1 != null && !queryCategoryLevel1.equals("")){
			categoryLevel2List = getCategoryList(queryCategoryLevel1.toString());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
			categoryLevel3List = getCategoryList(queryCategoryLevel2.toString());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		return "backend/applist";
	}
	
	/**
	 * 回显查询
	 * @param typeCode
	 * @return
	 */
	public List<DataDictionary> getDataDictionaryList(String typeCode){
		List<DataDictionary> dataDictionatyList=null;
		if(typeCode.equals("")){
			typeCode=null;
		}
		dataDictionatyList=dataDictionaryService.getDataDictionaryList(typeCode);
		return dataDictionatyList;
	}
	
	
	/**
	 * 三级联动，点击一级显示二级分类菜单
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> getCategoryList(String pid){
		List<AppCategory> categoryLevelList = null;
		Integer parentId=null;
		if(pid.equals("")){
			parentId=null;
		}else{
			parentId=Integer.parseInt(pid);
		}
		categoryLevelList = appCategoryService.getAppCategoryListByParentId(parentId);
		return categoryLevelList;
	}
	
	
	
	@RequestMapping(value="/appinfoadd")
	public String appinfoadd(Model model){
		List<DataDictionary> flatFormList = null;
		flatFormList = this.getDataDictionaryList("APP_FLATFORM");
		model.addAttribute("flatFormList", flatFormList);
		return "developer/appinfoadd";
	}
	
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<DataDictionary> getDataDicList(@RequestParam String tcode){
		
		return this.getDataDictionaryList(tcode);
	}
	
	@RequestMapping(value="/check/{aid}/{vid}")
	public String check(@PathVariable String aid,@PathVariable String vid,Model model){
		AppInfo appInfo = null;	//app
		appInfo=appService.getAppInfo(Integer.parseInt(aid),null);//得出app的信息
		AppVersion appVersion=null;	//版本
		appVersion=appVersionService.getAppVersionById(Integer.parseInt(vid));//得到版本信息
		
		model.addAttribute("appInfo", appInfo);
		model.addAttribute("appVersion", appVersion);
		return "backend/appcheck";
	}
	
	@RequestMapping(value="/checksave")
	public String checksave(@RequestParam(value="id",required=false)String id,@RequestParam(value="status",required=false)String status,@RequestParam(value="vid",required=false)String vid){
		if(appService.updateSatus(Integer.parseInt(status), Integer.parseInt(id))>0){
			return "redirect:/manager/backend/app/list";
		}
		return "redirect:/manager/backend/app/check/"+id+"/"+vid+"";
	}
}
