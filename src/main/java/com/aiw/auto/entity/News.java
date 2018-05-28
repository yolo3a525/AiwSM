package com.aiw.auto.entity;

import com.aiw.entity.BaseEntity;

public class News extends BaseEntity {

		private String  content;
		private String  title;
		private String  module;
	
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
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
	
}
