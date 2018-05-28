package com.aiw.dominos.entity;

import com.aiw.entity.BaseEntity;

public class InfoDetail extends BaseEntity {

		private String  content;
		private String  openid;
		private Integer  infoId;
	
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
		public Integer getInfoId() {
			return infoId;
		}
		public void setInfoId(Integer infoId) {
			this.infoId = infoId;
		}
	
}
