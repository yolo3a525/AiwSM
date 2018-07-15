package com.aiw.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;

@Component
public class PageHelperUtil {

	public void paging(String pageNum,String pageSize) {
		   if (!StringUtils.isEmpty(pageNum)) {
			   if(!StringUtils.isEmpty(pageSize)) {
				   PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize)); 
			   }else {
				   PageHelper.startPage(Integer.parseInt(pageNum), Constants.PAGE_SIZE);
			   }
		   }else {
			   if(!StringUtils.isEmpty(pageSize)) {
				   PageHelper.startPage(1,Integer.parseInt(pageSize)); 
			   }else {
				   PageHelper.startPage(1, Constants.PAGE_SIZE);
			   }
		   }
	}
}
