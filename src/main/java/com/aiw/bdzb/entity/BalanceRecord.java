package com.aiw.bdzb.entity;

import com.aiw.base.entity.BaseEntity;

public class BalanceRecord extends BaseEntity {

		private Integer  tenantId;
		private String  method;
		private Integer  amount;//+表示增加  -表示消費
		private Integer  balance;
	
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
        public Integer getAmount() {
            return amount;
        }
        public void setAmount(Integer amount) {
            this.amount = amount;
        }
        public Integer getBalance() {
            return balance;
        }
        public void setBalance(Integer balance) {
            this.balance = balance;
        }
		
	
}
