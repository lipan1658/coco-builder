<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${dao.fullName}">
    <resultMap id="BaseResultMap" type="${entity.fullName}">
<#list entity.fields as attr>
    <#if attr.primary>
        <id column="${attr.name}" property="${attr.field}" jdbcType="${attr.jdbcType}" />
    <#else>
        <result column="${attr.name}" property="${attr.field}" jdbcType="${attr.jdbcType}" />
    </#if>
</#list>
    </resultMap>

    <!--查询单个-->
    <select id="queryById" resultMap="BaseResultMap">
        select
        <#list entity.fields as attr>
            ${attr.name}<#if attr_has_next>,</#if>
        </#list>
        from <#if scheme>`${table.scheme}`.</#if>${table.name}
        where ${entity.primary.name} = <#noparse>#{</#noparse>${entity.primary.field}<#noparse>}</#noparse>
    </select>
    <!--查询指定行数据-->
    <select id="queryAllByLimit" resultMap="BaseResultMap">
        select
        <#list entity.fields as attr>
            ${attr.name}<#if attr_has_next>,</#if>
        </#list>
        from <#if scheme=true>`${table.scheme}`.</#if>${table.name}
        <where>
    <#list entity.fields as attr>
        <#if attr.fullJavaType == "java.lang.String">
            <if test="${attr.field} != null and ${attr.field} != ''">
                and ${attr.name} = <#noparse>#{entity.</#noparse>${attr.field}<#noparse>}</#noparse>
            </if>
        <#else>
            <if test="${attr.field} != null">
                and ${attr.name} = <#noparse>#{entity.</#noparse>${attr.field}<#noparse>}</#noparse>
            </if>
        </#if>
    </#list>
        </where>
        limit <#noparse>#{offset}</#noparse>, <#noparse>#{limit}</#noparse>
    </select>

    <!--通过实体作为筛选条件查询-->
    <select id="queryAll" resultMap="BaseResultMap">
        select
        <#list entity.fields as attr>
            ${attr.name}<#if attr_has_next>,</#if>
        </#list>
        from <#if scheme=true>`${table.scheme}`.</#if>${table.name}
        <where>
    <#list entity.fields as attr>
        <#if attr.fullJavaType == "java.lang.String">
            <if test="${attr.field} != null and ${attr.field} != ''">
                and ${attr.name} = <#noparse>#{</#noparse>${attr.field}<#noparse>}</#noparse>
            </if>
        <#else>
            <if test="${attr.field} != null">
                and ${attr.name} = <#noparse>#{</#noparse>${attr.field}<#noparse>}</#noparse>
            </if>
        </#if>
    </#list>
        </where>
    </select>

    <!--统计总行数-->
    <select id="count" resultType="java.lang.Long">
        select count(1)
        from <#if scheme=true>`${table.scheme}`.</#if>${table.name}
        <where>
    <#list entity.fields as attr>
        <#if attr.fullJavaType == "java.lang.String">
            <if test="${attr.field} != null and ${attr.field} != ''">
                and ${attr.name} = <#noparse>#{</#noparse>${attr.field}<#noparse>}</#noparse>
            </if>
        <#else>
            <if test="${attr.field} != null">
                and ${attr.name} = <#noparse>#{</#noparse>${attr.field}<#noparse>}</#noparse>
            </if>
        </#if>
    </#list>
        </where>
    </select>

    <!--新增所有列-->
    <insert id="insert" keyProperty="${entity.primary.field}" useGeneratedKeys="true">
        insert into <#if scheme=true>`${table.scheme}`.</#if>${table.name}(<#list entity.fields as attr><#if !attr.primary>${attr.name}<#if attr_has_next>,</#if></#if></#list>)
        values (<#list entity.fields as attr><#if !attr.primary><#noparse>#{</#noparse>${attr.field}<#noparse>}</#noparse><#if attr_has_next>,</#if></#if></#list>)
    </insert>

    <insert id="insertBatch" keyProperty="${entity.primary.field}" useGeneratedKeys="true">
        insert into <#if scheme=true>`${table.scheme}`.</#if>${table.name}(<#list entity.fields as attr><#if !attr.primary>${attr.name}<#if attr_has_next>,</#if></#if></#list>)
        values
        <foreach collection="entities" item="entity" separator=",">
            (<#list entity.fields as attr><#if !attr.primary><#noparse>#{entity.</#noparse>${attr.field}<#noparse>}</#noparse><#if attr_has_next>,</#if></#if></#list>)
        </foreach>
    </insert>

    <insert id="insertOrUpdateBatch" keyProperty="${entity.primary.field}" useGeneratedKeys="true">
        insert into <#if scheme=true>`${table.scheme}`.</#if>${table.name}(<#list entity.fields as attr>${attr.name}<#if attr_has_next>,</#if></#list>)
        values
        <foreach collection="entities" item="entity" separator=",">
            (<#list entity.fields as attr><#noparse>#{entity.</#noparse>${attr.field}<#noparse>}</#noparse><#if attr_has_next>,</#if></#list>)
        </foreach>
        on duplicate key update
<#list entity.fields as attr>
    <#if !attr.primary>
        ${attr.name} = values(${attr.name})<#if attr_has_next>,</#if>
    </#if>
</#list>
    </insert>

    <!--通过主键修改数据-->
    <update id="update">
        update <#if scheme=true>`${table.scheme}`.</#if>${table.name}
        <set>
    <#list entity.fields as attr>
    <#if !attr.primary>
        <#if attr.fullJavaType == "java.lang.String">
            <if test="${attr.field} != null and ${attr.field} != ''">
                ${attr.name} = <#noparse>#{</#noparse>${attr.field}<#noparse>}</#noparse>,
            </if>
        <#else>
            <if test="${attr.field} != null">
                ${attr.name} = <#noparse>#{</#noparse>${attr.field}<#noparse>}</#noparse>,
            </if>
        </#if>
    </#if>
    </#list>
        </set>
        where ${entity.primary.name} = <#noparse>#{</#noparse>${entity.primary.field}<#noparse>}</#noparse>
    </update>

    <!--通过主键删除-->
    <delete id="deleteById">
        delete from <#if scheme=true>`${table.scheme}`.</#if>${table.name} where ${entity.primary.name} = <#noparse>#{</#noparse>${entity.primary.field}<#noparse>}</#noparse>
    </delete>

</mapper>