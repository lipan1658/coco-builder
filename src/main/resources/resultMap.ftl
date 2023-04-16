    <resultMap id="${entity.name}Map" type="${entity.fullName}">
<#list entity.fields as attr>
        <result column="${attr.name}" property="${attr.field}" jdbcType="${attr.jdbcType}" />
</#list>
    </resultMap>