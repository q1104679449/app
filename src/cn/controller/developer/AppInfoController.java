package cn.controller.developer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.pojo.AppCategory;
import cn.pojo.AppInfo;
import cn.pojo.AppVersion;
import cn.pojo.DataDictionary;
import cn.pojo.DevUser;
import cn.service.backend.AppService;
import cn.service.backend.AppVersionService;
import cn.service.developer.AppCategoryService;
import cn.service.developer.AppInfoService;
import cn.service.developer.DataDictionaryService;
import cn.tools.Constants;
import cn.tools.PageSupport;

@Controller
@RequestMapping(value="/dev/flatform/app")
public class AppInfoController {
	private Logger logger = Logger.getLogger(AppInfoController.class);
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
	public String appInfoList(Model model,HttpSession session,
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryStatus",required=false) String _queryStatus,
			@RequestParam(value="queryCategoryLevel1",required=false) String _queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false) String _queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false) String _queryCategoryLevel3,
			@RequestParam(value="queryFlatformId",required=false) String _queryFlatformId,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		logger.info("getAppInfoList -- > querySoftwareName: " + querySoftwareName);
		logger.info("getAppInfoList -- > queryStatus: " + _queryStatus);
		logger.info("getAppInfoList -- > queryCategoryLevel1: " + _queryCategoryLevel1);
		logger.info("getAppInfoList -- > queryCategoryLevel2: " + _queryCategoryLevel2);
		logger.info("getAppInfoList -- > queryCategoryLevel3: " + _queryCategoryLevel3);
		logger.info("getAppInfoList -- > queryFlatformId: " + _queryFlatformId);
		logger.info("getAppInfoList -- > pageIndex: " + pageIndex);
		
		Integer devId = ((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId();
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
		Integer queryStatus = null;
		if(_queryStatus != null && !_queryStatus.equals("")){
			queryStatus = Integer.parseInt(_queryStatus);
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
			totalCount = appInfoService.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
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
			appInfoList = appInfoService.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, currentPageNo, pageSize);
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
		model.addAttribute("queryStatus", queryStatus);
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
		return "developer/appinfolist";
		
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
	
	
	@RequestMapping(value="/apkexist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object apkNameIsExit(@RequestParam String APKName){
		HashMap<String, String> resultMap=new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(APKName)){
			resultMap.put("APKName", "empty");
		}else{
			AppInfo appInfo=null;
			appInfo=appService.getAppInfo(null, APKName);
			if(appInfo!=null){
				resultMap.put("APKName", "exist");
			}else{
				resultMap.put("APKName", "noexist");
			}
		}
		return JSONArray.toJSON(resultMap);
	}
	
	
	@RequestMapping(value="/appversionadd/{id}")
	public String appversionadd(@PathVariable String id,Model model){
		List<AppVersion> appVersions=null;
		AppVersion appVersion=new AppVersion();
		appVersions=appVersionService.getAppVersionByAppId(Integer.parseInt(id));
		System.out.println(appVersions.get(0).getAppId());
		Integer appId=appVersions.get(0).getAppId();
		appVersion.setAppId(appId);
		model.addAttribute("appVersion",appVersion);
		model.addAttribute("appVersionList", appVersions);
		return "developer/appversionadd";
	}
	@RequestMapping(value="/addversionsave",method=RequestMethod.POST)
	public String addVersionSave(AppVersion appVersion,HttpSession session,HttpServletRequest request,@RequestParam String appIds,
					@RequestParam(value="a_downloadLink",required=false)MultipartFile attach,
					 Model model){
		String downloadLink=null;
		String apkLocPath=null;
		String apkFileName=null;
		String fileUploadError="";
		Integer appId=null;
		if(appIds!=null && !appIds.equals("")){
			appId=Integer.parseInt(appIds);
			appVersion.setAppId(appId);
		}
		if(!attach.isEmpty()){//不为空，则是文件
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			logger.info("uploadfiles path:"+path);
			String oldFileName=attach.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀名
			if(prefix.equalsIgnoreCase("apk")){//apk文件名：apk名称+版本号+.apk
				String apkName=null;
				
				
				try {
					apkName=appService.getAppInfo(appId, null).getAPKName();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				if(apkName==null || apkName.equals("")){
					model.addAttribute("fileUploadError",fileUploadError);
					return "developer/appversionadd";
				}
				int filesize=1024*1024*100;
				if(attach.getSize()>filesize){
					model.addAttribute("fileUploadError", "上传文件不能大于100M");
					return "developer/appversionadd";
				}
				apkFileName=apkName+"_"+appVersion.getVersionNo()+".apk";
				File targetFile=new File(path,apkFileName);
				if(!targetFile.exists()){
					targetFile.mkdirs();
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
					model.addAttribute("fileUploadError", fileUploadError);
					return "developer/appversionadd";
				}
				downloadLink=request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				apkLocPath=path+File.separator+apkName;
			}else{
				fileUploadError = Constants.FILEUPLOAD_ERROR_3;
				model.addAttribute("fileUploadError", fileUploadError);
				return "developer/appversionadd";
			}
		}
		appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		//SimpleDateFormat df=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		appVersion.setCreationDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		if(appVersionService.addAppVersion(appVersion)>0){
			return "redirect:/dev/flatform/app/list";
		}
		return "redirect:/dev/flatform/app/appversionadd/"+appVersion.getAppId();
	}
	@RequestMapping(value="/aaa")
	public String aaa(@ModelAttribute("appVersion")AppVersion appVersion){
		System.out.println("apkname:"+appVersion.getApkFileName());
		System.out.println("appid:"+appVersion.getAppId());
		return "redirect:/dev/flatform/app/list";
	}
	
	

	
	
}
