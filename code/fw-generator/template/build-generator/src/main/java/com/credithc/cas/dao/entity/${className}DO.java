<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.credithc.cas.dao.entity;

import java.util.Date;


import com.credithc.common.dao.AbsEntity;

public class ${className}DO extends AbsEntity{
	
	<@generateFields/>
	<@generateProperties/>
}

<#macro generateFields>
	<#list table.columns as column>
	private ${column.javaType} ${column.columnNameLower};
	</#list>
</#macro>

<#macro generateProperties>

	<#list table.columns as column>
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	
	</#list>
</#macro>
