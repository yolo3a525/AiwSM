package com.aiw.ruiyun.entity;

import com.aiw.entity.BaseEntity;

public class Salary extends BaseEntity {

		private java.math.BigDecimal  salary;
		private String  name;
		private boolean emailFlag;
	
		public java.math.BigDecimal getSalary() {
			return salary;
		}
		public void setSalary(java.math.BigDecimal salary) {
			this.salary = salary;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isEmailFlag() {
			return emailFlag;
		}
		public void setEmailFlag(boolean emailFlag) {
			this.emailFlag = emailFlag;
		}
	
}
