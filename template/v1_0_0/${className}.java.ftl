package ${packageName}.entity;

import com.baomidou.mybatisplus.annotation.TableName;
<#if importSearch>
import com.izhimu.seas.data.annotation.Search;
</#if>
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

<#list importList as import>
import ${import};
</#list>

/**
 * ${tableDesc}实体
 *
 * @author ${author}
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("${tableName}")
public class ${className} extends IdEntity {

<#list fieldList as field>
    <#if field.isPk == 0>
    /**
     * ${field.showName}
     */
        <#if field.searchable == 1>
    @Search
        </#if>
    private ${field.javaType} ${field.attrName};
    </#if>
</#list>
}
