export interface ${className} {
<#list fieldList as field>
    ${field.attrName}: ${field.jsType} | null;
</#list>
}

export const d${className} = (): ${className} => ({
<#list fieldList as field>
    ${field.attrName}: null,
</#list>
});
