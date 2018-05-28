<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--命名空间应该是对应接口的包名+类名 -->
<mapper namespace="com.aiw.${module}.mapper.${component}Mapper">
	
	<!--表名 -->
	<sql id="tableName">
		${module}_${componentLower}
	</sql>
	
	
    <insert id="insert" useGeneratedKeys="true" keyProperty="id" parameterType="com.aiw.${module}.entity.${component}">
		insert into  <include refid="tableName"></include> 
		 (<#list entity?keys as key>  
			`${key}`
			<#if !key_has_next>
   				
   			<#else> 
   				,
			</#if>
		 </#list>  
		 <include refid="com.aiw.mapper.Common.createEntityColumn"></include> ) 
		 values (
		 <#list entity?keys as key>  
			${r"#{"}${key}${r"}"}
			<#if !key_has_next>
   				
   			<#else> 
   				,
			</#if>
		 </#list>
		 <include refid="com.aiw.mapper.Common.createEntityColumnValue"></include>) 
	</insert>
	
	
	<update id="update" parameterType="com.aiw.${module}.entity.${component}" >
	    update  <include refid="tableName"></include> 
	    <set > 
	       lastUpdateTime = current_timestamp(),
	       lastUpdateUser = 'admin'
	       <#list entity?keys as key>  
         		<if test="${key} != null">
			         , ${key} = ${r"#{"}${key}${r"}"}
			    </if> 
		  </#list>
	    </set>
	    where id = ${r"#{id}"}
	 </update>
    
    <select id="get" resultType="com.aiw.${module}.entity.${component}">
        select * from 
        <include refid="tableName"></include>  
          <where> 
		    <if test="id != null">
		        id = ${r"#{id}"}  
		    </if> 
		  </where>
    </select>
    
    
    
    <select id="select" resultType="com.aiw.${module}.entity.${component}">
        select * from 
        <include refid="tableName"></include>  
          <where> 
		    <if test="id != null">
		        id = ${r"#{id}"}  
		    </if> 
		  </where>
    </select>
    
    <select id="selectPage" resultType="com.aiw.${module}.entity.${component}">
        select * from 
        <include refid="tableName"></include> 
         <where> 
         	<#list entity?keys as key>  
         		<if test="t.${key} != null">
			         ${key} = ${r"#{t."}${key}${r"}"}
			    </if> 
		 </#list>
		  </where>
        order by id desc
    </select>
    <delete id="delete">
    	delete from <include refid="tableName"></include> 
    	where id = ${r"#{id}"}  
    </delete>
    
</mapper>