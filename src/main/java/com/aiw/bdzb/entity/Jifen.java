package com.aiw.bdzb.entity;

import com.aiw.base.entity.BaseEntity;

public class Jifen extends BaseEntity {

		private Integer  tenantId;
		private String  method;
		private Integer  afterJifen;
		private Integer  jifen;
		private Integer  consume;
	
		public Integer getTenantId() {
			return tenantId;
		}
		public void setTenantId(Integer tenantId) {
			this.tenantId = tenantId;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public Integer getAfterJifen() {
			return afterJifen;
		}
		public void setAfterJifen(Integer afterJifen) {
			this.afterJifen = afterJifen;
		}
		public Integer getJifen() {
			return jifen;
		}
		public void setJifen(Integer jifen) {
			this.jifen = jifen;
		}
		public Integer getConsume() {
			return consume;
		}
		public void setConsume(Integer consume) {
			this.consume = consume;
		}
	
}
