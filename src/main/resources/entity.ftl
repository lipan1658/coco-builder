package ${entity.packageName};

<#if swagger>
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
</#if>
import lombok.Data;
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
@Data
<#if swagger>
@ApiModel("${table.comment!entity.name}")
</#if>
public class ${entity.name} {

<#-- 循环类型及属性 -->
<#list entity.fields as attr>
    /**
    * ${attr.comment!attr.name}
    */
<#if swagger>
    @ApiModelProperty("${attr.comment!attr.name}")
</#if>
    private ${attr.javaType} ${attr.field};

</#list>


}