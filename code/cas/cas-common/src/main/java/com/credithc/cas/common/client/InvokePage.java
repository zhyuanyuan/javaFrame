package com.credithc.cas.common.client;

import java.util.Collection;

/**
 * 
 * @author yangyang151020
 *
 */
public class InvokePage<T> {
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageCount;
	private Integer totalCount;
	private Collection<T> entityDatas;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Collection<T> getEntityDatas() {
		return entityDatas;
	}

	public void setEntityDatas(Collection<T> entityDatas) {
		this.entityDatas = entityDatas;
	}
}
