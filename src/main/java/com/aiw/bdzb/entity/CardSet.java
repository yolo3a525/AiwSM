package com.aiw.bdzb.entity;

import com.aiw.entity.BaseEntity;

public class CardSet extends BaseEntity {

		private Integer  dues;
		private Integer  type;
		//private Integer  type2; //用id代替，就是数据字典中的类型
		private Integer  userTime;
		private Integer  wearTotalMoney;//可以佩戴总金额
	
		public Integer getDues() {
			return dues;
		}
		public void setDues(Integer dues) {
			this.dues = dues;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Integer getUserTime() {
			return userTime;
		}
		public void setUserTime(Integer userTime) {
			this.userTime = userTime;
		}
		public Integer getWearTotalMoney() {
			return wearTotalMoney;
		}
		public void setWearTotalMoney(Integer wearTotalMoney) {
			this.wearTotalMoney = wearTotalMoney;
		}
	
}
