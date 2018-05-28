package com.aiw.auto.entity;

import com.aiw.entity.BaseEntity;

public class CRUD extends BaseEntity {

		private java.sql.Date  birthday;
		private String  email;
		private String  name;
		private Integer  age;
	
		public java.sql.Date getBirthday() {
			return birthday;
		}
		public void setBirthday(java.sql.Date birthday) {
			this.birthday = birthday;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
	
}
