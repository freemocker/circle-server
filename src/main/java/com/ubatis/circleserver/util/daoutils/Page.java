package com.ubatis.circleserver.util.daoutils;


import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {

	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final int DEFAULT_CURRENT_PAGE_NUM = 1;

	private static final long serialVersionUID = 1L;

	private int currentPageNum = 1;// 当前页页码

	private boolean hasPreviousPage = false;// 是否有上一页

	private int previousPageNum;// 上一页页码

	private boolean hasNextPage = false;// 是否有下一页

	private int nextPageNum;// 下一页页码

	private boolean hasFirstPage;

	private boolean hasLastPage;

	private int totalPages = 0;// 总页数

	private int totalRecords = 0;// 总记录数

	private int pageSize = DEFAULT_PAGE_SIZE;// 每页显示记录条数

	@SuppressWarnings("rawtypes")
	private List pageDatas;// 分页数据

	/**
	 * 以"默认当前页码"与"每页显示记录数"创建分页对象
	 */
	public Page() {
		this.currentPageNum = DEFAULT_CURRENT_PAGE_NUM;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

	/**
	 * 以传入参数当前页码与每页显示记录数创建对象，如果当前页码小于0,则使用默认当前页设置<br />
	 * 如果每页显示记录数小于0,则使用默认每页显示记录数设置
	 * 
	 * @param currentPageNum
	 *        当前页码
	 * @param pageSize
	 *        每页显示记录数
	 */
	public Page(int currentPageNum, int pageSize) {
		this.currentPageNum = currentPageNum > 0 ? currentPageNum : DEFAULT_CURRENT_PAGE_NUM;
		this.pageSize = pageSize > 0 ? pageSize	: DEFAULT_PAGE_SIZE;
	}

	//getter setter

	public int getNextPageNum() {
		return currentPageNum == totalPages ? totalPages : currentPageNum + 1;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = currentPageNum + 1;
	}

	public int getPreviousPageNum() {
		return currentPageNum - 1;
	}

	public void setPreviousPageNum(int previousPageNum) {
		this.previousPageNum = previousPageNum;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = (currentPageNum < 1) ? 1	: currentPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = (pageSize < 1) ? DEFAULT_PAGE_SIZE : pageSize;
	}

	public boolean isHasNextPage() {
		return this.getTotalRecords() == 0 ? false:(currentPageNum == this.getTotalPages() ? false:true);
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return currentPageNum != 1;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public int getTotalPages() {
		return pageSize == 0 ? 0 : (totalRecords + pageSize - 1) / pageSize;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@SuppressWarnings("rawtypes")
	public List getPageDatas() {
		return pageDatas;
	}

	@SuppressWarnings("rawtypes")
	public void setPageDatas(List pageDatas) {
		this.pageDatas = pageDatas;
	}

	public boolean isHasFirstPage() {
		return currentPageNum > 1;
	}

	public void setHasFirstPage(boolean hasFirstPage) {
		this.hasFirstPage = hasFirstPage;
	}

	public boolean isHasLastPage() {
		return currentPageNum < totalPages;
	}

	public void setHasLastPage(boolean hasLastPage) {
		this.hasLastPage = hasLastPage;
	}

	//
	class PageInfo{
		private int current;
		private int total;
		private int amount;
		private boolean ismore;

		public int getCurrent() {
			return current;
		}

		public void setCurrent(int current) {
			this.current = current;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public boolean isIsmore() {
			return ismore;
		}

		public void setIsmore(boolean ismore) {
			this.ismore = ismore;
		}

		public PageInfo(){
			this.current = getCurrentPageNum();
			this.total = getTotalPages();
			this.amount = getTotalRecords();
			this.ismore = isHasNextPage();
		}
	}
	//返回页码信息
	public PageInfo getPageInfo(){
		return new PageInfo();
	}

	// 临时类，因为没有getter setter，如果直接返回继承此类的参数对象，则报错
	// 用于防止直接返回此对象，防止内部参数泄露
	class Temp {
		String a;

		public Temp() {
			this.a = "临时";
		}
	}
	// 删掉次返回，上面说的情况不会报错
	public Temp getTemp(){
		return new Temp();
	}
}
