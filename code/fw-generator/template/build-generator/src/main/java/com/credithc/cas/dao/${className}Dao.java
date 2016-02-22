<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.credithc.cas.dao;

import com.credithc.common.dao.BaseDao;
import com.credithc.cas.dao.entity.${className}DO;

public interface ${className}Dao extends BaseDao<${className}DO>{

}
