package com.aiw.bdzb.entity;

import com.aiw.entity.BaseEntity;

public class Sence extends BaseEntity {

		private Integer  imageFlag;//1.有图片 0 没图片
		private Integer  isShow;//1 显示 0 不显示
		private String  name;
		private Integer order;
	
		public Integer getImageFlag() {
			return imageFlag;
		}
		public void setImageFlag(Integer imageFlag) {
			this.imageFlag = imageFlag;
		}
		public Integer getIsShow() {
			return isShow;
		}
		public void setIsShow(Integer isShow) {
			this.isShow = isShow;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getOrder() {
			return order;
		}
		public void setOrder(Integer order) {
			this.order = order;
		}
	
}
