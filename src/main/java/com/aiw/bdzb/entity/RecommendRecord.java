package com.aiw.bdzb.entity;

import com.aiw.base.entity.BaseEntity;

public class RecommendRecord extends BaseEntity {

		private String  recommended;
		private String  referrer;
		private Integer  days;
		private java.sql.Date oldVipDeadLine;
		private java.sql.Date newVipDeadLine;
		private Integer  jifen;
	
		public String getRecommended() {
			return recommended;
		}
		public void setRecommended(String recommended) {
			this.recommended = recommended;
		}
		public String getReferrer() {
			return referrer;
		}
		public void setReferrer(String referrer) {
			this.referrer = referrer;
		}
		public Integer getDays() {
			return days;
		}
		public void setDays(Integer days) {
			this.days = days;
		}
		public Integer getJifen() {
			return jifen;
		}
		public void setJifen(Integer jifen) {
			this.jifen = jifen;
		}
        public java.sql.Date getOldVipDeadLine() {
            return oldVipDeadLine;
        }
        public void setOldVipDeadLine(java.sql.Date oldVipDeadLine) {
            this.oldVipDeadLine = oldVipDeadLine;
        }
        public java.sql.Date getNewVipDeadLine() {
            return newVipDeadLine;
        }
        public void setNewVipDeadLine(java.sql.Date newVipDeadLine) {
            this.newVipDeadLine = newVipDeadLine;
        }
	
}
