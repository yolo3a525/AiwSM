package com.aiw.bdzb.entity;

import java.math.BigDecimal;
import java.util.List;

import com.aiw.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Order extends BaseEntity {
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
		private java.sql.Timestamp  startTime;//借戴开始时间
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
		private java.sql.Timestamp  endTime;//借戴结束时间
		
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
		private java.sql.Timestamp  returnTime;//实际归还时间
		
		
		private java.lang.Integer  price;//暂时不用,珠宝总金额
		private java.lang.Integer  days;
		private java.lang.Integer  status;//0.预约  1.租赁中  2.完成  3.取消   4.删除    100非预约 99标识全部
		private Integer locked;//0 没锁定，1表示锁定。只有status为0的时候才有可能被锁定。
		
		
		private java.lang.Integer deposit;//押金,保证金
		private Integer depositMethod;//押金支付方式  1表示现金支付   2表示余额转押金
		
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
		private java.sql.Timestamp  appointmentTime;//预约上门办理时间.
		
		private List<OrderDetail> orderDetailList;
		private Integer tenantid;
		private BigDecimal excessAmount;//超出部分应付金额
		private BigDecimal overdueAmount;//逾期应付金额
		
		
		private Integer appointmentRemindFlag;//0 没有提醒，1表示提醒过--暂时没用。
		
		private String tPhone;//订单生成时候的联系人与联系电话，如果没有默认是所有人的联系方式。
		private String tName;//订单生成时候的联系人与联系电话，如果没有默认是所有人的联系方式。
		
		//非DB字段
		private String  phone;
		private Integer jewelryCount;//订单中珠宝的数量
		private String  statusName;
		
		private Integer  chaochuDays;
		
		private List<OrderDetail> detailList;
		
	

		public Integer getLocked() {
            return locked;
        }
        public void setLocked(Integer locked) {
            this.locked = locked;
        }
        public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public java.lang.Integer getPrice() {
			return price;
		}
		public void setPrice(java.lang.Integer price) {
			this.price = price;
		}
		public java.lang.Integer getDays() {
			return days;
		}
		public void setDays(java.lang.Integer days) {
			this.days = days;
		}
		public java.lang.Integer getStatus() {
			return status;
		}
		public void setStatus(java.lang.Integer status) {
			this.status = status;
		}
		public List<OrderDetail> getOrderDetailList() {
			return orderDetailList;
		}
		public void setOrderDetailList(List<OrderDetail> orderDetailList) {
			this.orderDetailList = orderDetailList;
		}
		public java.lang.Integer getDeposit() {
			return deposit;
		}
		public void setDeposit(java.lang.Integer deposit) {
			this.deposit = deposit;
		}

		public java.sql.Timestamp getAppointmentTime() {
			return appointmentTime;
		}
		public void setAppointmentTime(java.sql.Timestamp appointmentTime) {
			this.appointmentTime = appointmentTime;
		}
		public java.sql.Timestamp getStartTime() {
			return startTime;
		}
		public void setStartTime(java.sql.Timestamp startTime) {
			this.startTime = startTime;
		}
		public java.sql.Timestamp getEndTime() {
			return endTime;
		}
		public void setEndTime(java.sql.Timestamp endTime) {
			this.endTime = endTime;
		}
		public Integer getTenantid() {
			return tenantid;
		}
		public void setTenantid(Integer tenantid) {
			this.tenantid = tenantid;
		}
		public BigDecimal getExcessAmount() {
			return excessAmount;
		}
		public void setExcessAmount(BigDecimal excessAmount) {
			this.excessAmount = excessAmount;
		}
		public BigDecimal getOverdueAmount() {
			return overdueAmount;
		}
		public void setOverdueAmount(BigDecimal overdueAmount) {
			this.overdueAmount = overdueAmount;
		}
		public java.sql.Timestamp getReturnTime() {
			return returnTime;
		}
		public void setReturnTime(java.sql.Timestamp returnTime) {
			this.returnTime = returnTime;
		}
		public Integer getAppointmentRemindFlag() {
			return appointmentRemindFlag;
		}
		public void setAppointmentRemindFlag(Integer appointmentRemindFlag) {
			this.appointmentRemindFlag = appointmentRemindFlag;
		}
        public String gettName() {
            return tName;
        }
        public void settName(String tName) {
            this.tName = tName;
        }
        public String gettPhone() {
            return tPhone;
        }
        public void settPhone(String tPhone) {
            this.tPhone = tPhone;
        }
        public Integer getJewelryCount() {
            return jewelryCount;
        }
        public void setJewelryCount(Integer jewelryCount) {
            this.jewelryCount = jewelryCount;
        }
        public String getStatusName() {
            return statusName;
        }
        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }
        public List<OrderDetail> getDetailList() {
            return detailList;
        }
        public void setDetailList(List<OrderDetail> detailList) {
            this.detailList = detailList;
        }
        public Integer getDepositMethod() {
            return depositMethod;
        }
        public void setDepositMethod(Integer depositMethod) {
            this.depositMethod = depositMethod;
        }
        public Integer getChaochuDays() {
            return chaochuDays;
        }
        public void setChaochuDays(Integer chaochuDays) {
            this.chaochuDays = chaochuDays;
        }
	
}
