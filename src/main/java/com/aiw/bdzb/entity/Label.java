package com.aiw.bdzb.entity;

import com.aiw.entity.BaseEntity;

public class Label extends BaseEntity {

		private Integer  type;
		private String  name;
	
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
}
