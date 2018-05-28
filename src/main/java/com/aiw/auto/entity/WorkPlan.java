package com.aiw.auto.entity;

import com.aiw.entity.BaseEntity;

public class WorkPlan extends BaseEntity {

		private Integer  progress;
		private String  name;
	
		public Integer getProgress() {
			return progress;
		}
		public void setProgress(Integer progress) {
			this.progress = progress;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
}
