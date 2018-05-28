package com.aiw.dominos.entity;

import com.aiw.entity.BaseEntity;

public class Info extends BaseEntity {

		private String  regulation;
		private String  openid;
		private String  title;
	
		public String getRegulation() {
			return regulation;
		}
		public void setRegulation(String regulation) {
			this.regulation = regulation;
		}
		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
	
}
