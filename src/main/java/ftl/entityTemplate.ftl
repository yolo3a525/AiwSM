package com.aiw.${module}.entity;

import com.aiw.entity.BaseEntity;

public class ${component} extends BaseEntity {

	<#list entity?keys as key>  
		private ${entity[key]}  ${key};
	</#list>  
	
	<#list entity?keys as key>  
		public ${entity[key]} get${key?cap_first}() {
			return ${key};
		}
		public void set${key?cap_first}(${entity[key]} ${key}) {
			this.${key} = ${key};
		}
	</#list> 
	
}
