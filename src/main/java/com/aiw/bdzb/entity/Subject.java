package com.aiw.bdzb.entity;

import com.aiw.entity.BaseEntity;

public class Subject extends BaseEntity {

		private String  content;
		private String  title;
		private Integer  imgFlag;
		private Integer  status;
	
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public Integer getImgFlag() {
			return imgFlag;
		}
		public void setImgFlag(Integer imgFlag) {
			this.imgFlag = imgFlag;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
	
}
