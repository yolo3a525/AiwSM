package com.aiw.bdzb.entity;

import java.util.List;

import com.aiw.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Jewelry extends BaseEntity {

		private java.lang.Integer  rent;//租用价格,暂时没用了.
		private java.lang.Integer  price;//事物价格
		private String  name;
		private java.lang.Integer  typeid;
		private String type;
		private String sid; //珠宝的唯一编号,长度不能超过50
		
		
		private Integer movieflag;// 0  没有 1有
		private Integer imageflag;// 0  没有 1有
		
		
		private Integer orderStatus;  
		//0 没上架    1上架没租出去    2上架并被人租用了(可能是订单预约中,也可能是租赁中.如果已经完成.则状态修改成1)
		/**
		 * 100状态,在自己的购物车中已经有了.仅仅为app 展示使用.虽然在自己的购物车中,但是仍然可能被别人租戴.
		 * 不作为业务状态考虑范围.
		 * 
		 */
		
		
		private Integer hotflag;// 0  null  不是新品  1是新品
		private Integer gysid;//供应商id
		private Integer showflag;// 0  null  不上架  1上架显示
		
		private String relatedSid;	 //相关推荐产品
		private String designerWords;//设计师推荐话语
		
		//标签
		private Integer label_pinlei;
		private Integer label_fengge;
		private Integer label_faxing;
		private Integer label_fuse;
		private Integer label_lianxing;
		private Integer label_changjing;
		
		//图片更新次数
		private Integer seq;
		////标签
        private String label_pinlei_name;
        private String label_fengge_name;
        private String label_faxing_name;
        private String label_fuse_name;
        private String label_lianxing_name;
        private String label_changjing_name;
		
		
		
		
		//不是DB中的数据,仅仅为了显示
		private Integer orderid;//看是否有正在租赁的订单.
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
		private java.sql.Timestamp endTime;
		private String username;
		private Integer times;
		private Integer onlineTime;
		
		//非DB中数据,为了查询方便.
		private String nameOrSid;
		
		//非Jewelry表中数据
		private Integer boxStatus;//宝箱中的状态
		//获取搭配推荐
		private List<Jewelry> relatedList;//搭配推荐
			
	
		public java.lang.Integer getRent() {
			return rent;
		}
		public void setRent(java.lang.Integer rent) {
			this.rent = rent;
		}
		public java.lang.Integer getPrice() {
			return price;
		}
		public void setPrice(java.lang.Integer price) {
			this.price = price;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public java.lang.Integer getTypeid() {
			return typeid;
		}
		public void setTypeid(java.lang.Integer typeid) {
			this.typeid = typeid;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Integer getMovieflag() {
			return movieflag;
		}
		public void setMovieflag(Integer movieflag) {
			this.movieflag = movieflag;
		}
		public Integer getOrderid() {
			return orderid;
		}
		public void setOrderid(Integer orderid) {
			this.orderid = orderid;
		}
		public Integer getHotflag() {
			return hotflag;
		}
		public void setHotflag(Integer hotflag) {
			this.hotflag = hotflag;
		}
		public Integer getImageflag() {
			return imageflag;
		}
		public void setImageflag(Integer imageflag) {
			this.imageflag = imageflag;
		}
		public String getSid() {
			return sid;
		}
		public void setSid(String sid) {
			this.sid = sid;
		}
		public java.sql.Timestamp getEndTime() {
			return endTime;
		}
		public void setEndTime(java.sql.Timestamp endTime) {
			this.endTime = endTime;
		}
		public Integer getGysid() {
			return gysid;
		}
		public void setGysid(Integer gysid) {
			this.gysid = gysid;
		}
		public Integer getShowflag() {
			return showflag;
		}
		public void setShowflag(Integer showflag) {
			this.showflag = showflag;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public Integer getLabel_pinlei() {
			return label_pinlei;
		}
		public void setLabel_pinlei(Integer label_pinlei) {
			this.label_pinlei = label_pinlei;
		}
		public Integer getLabel_fengge() {
			return label_fengge;
		}
		public void setLabel_fengge(Integer label_fengge) {
			this.label_fengge = label_fengge;
		}
		public Integer getLabel_faxing() {
			return label_faxing;
		}
		public void setLabel_faxing(Integer label_faxing) {
			this.label_faxing = label_faxing;
		}
		public Integer getLabel_fuse() {
			return label_fuse;
		}
		public void setLabel_fuse(Integer label_fuse) {
			this.label_fuse = label_fuse;
		}
		public Integer getLabel_lianxing() {
			return label_lianxing;
		}
		public void setLabel_lianxing(Integer label_lianxing) {
			this.label_lianxing = label_lianxing;
		}
		public Integer getLabel_changjing() {
			return label_changjing;
		}
		public void setLabel_changjing(Integer label_changjing) {
			this.label_changjing = label_changjing;
		}
		public String getRelatedSid() {
			return relatedSid;
		}
		public void setRelatedSid(String relatedSid) {
			this.relatedSid = relatedSid;
		}
		public String getDesignerWords() {
			return designerWords;
		}
		public void setDesignerWords(String designerWords) {
			this.designerWords = designerWords;
		}
		public Integer getTimes() {
			return times;
		}
		public void setTimes(Integer times) {
			this.times = times;
		}
        public Integer getOnlineTime() {
            return onlineTime;
        }
        public void setOnlineTime(Integer onlineTime) {
            this.onlineTime = onlineTime;
        }
        public String getLabel_pinlei_name() {
            return label_pinlei_name;
        }
        public void setLabel_pinlei_name(String label_pinlei_name) {
            this.label_pinlei_name = label_pinlei_name;
        }
        public String getLabel_fengge_name() {
            return label_fengge_name;
        }
        public void setLabel_fengge_name(String label_fengge_name) {
            this.label_fengge_name = label_fengge_name;
        }
        public String getLabel_faxing_name() {
            return label_faxing_name;
        }
        public void setLabel_faxing_name(String label_faxing_name) {
            this.label_faxing_name = label_faxing_name;
        }
        public String getLabel_fuse_name() {
            return label_fuse_name;
        }
        public void setLabel_fuse_name(String label_fuse_name) {
            this.label_fuse_name = label_fuse_name;
        }
        public String getLabel_lianxing_name() {
            return label_lianxing_name;
        }
        public void setLabel_lianxing_name(String label_lianxing_name) {
            this.label_lianxing_name = label_lianxing_name;
        }
        public String getLabel_changjing_name() {
            return label_changjing_name;
        }
        public void setLabel_changjing_name(String label_changjing_name) {
            this.label_changjing_name = label_changjing_name;
        }
        public Integer getOrderStatus() {
            return orderStatus;
        }
        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }
        public Integer getSeq() {
            return seq;
        }
        public void setSeq(Integer seq) {
            this.seq = seq;
        }
        public String getNameOrSid() {
            return nameOrSid;
        }
        public void setNameOrSid(String nameOrSid) {
            this.nameOrSid = nameOrSid;
        }
        public Integer getBoxStatus() {
            return boxStatus;
        }
        public void setBoxStatus(Integer boxStatus) {
            this.boxStatus = boxStatus;
        }
        public List<Jewelry> getRelatedList() {
            return relatedList;
        }
        public void setRelatedList(List<Jewelry> relatedList) {
            this.relatedList = relatedList;
        }
	


	
}
