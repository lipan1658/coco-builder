package ${entity.packageName};

<#list entity.javaTypeSet as javaType>
import ${javaType};
</#list>

/**
* ${table.comment!""}Entity
* 〈功能详细描述〉
*
* @author xxxxx
* @see [相关类/方法]（可选）
* @since [产品/模块版本] （可选）
*/
public class ${entity.name} {

<#-- 循环类型及属性 -->
<#list entity.fields as attr>
<#if attr.comment??>
    /**
     * ${attr.comment}
     */
</#if>
    private ${attr.javaType} ${attr.field};

</#list>


}