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
*
* @author ${author}
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