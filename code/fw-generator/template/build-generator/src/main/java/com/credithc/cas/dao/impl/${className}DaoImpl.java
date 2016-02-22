<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.credithc.cas.dao.impl;

import com.credithc.common.dao.AbsBaseDao;
import com.credithc.cas.dao.${className}Dao;
import com.credithc.cas.dao.entity.${className}DO;

public class ${className}DaoImpl extends AbsBaseDao<${className}DO> implements ${className}Dao {

}
