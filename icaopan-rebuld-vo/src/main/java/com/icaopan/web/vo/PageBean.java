/**
 *
 */
package com.icaopan.web.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class PageBean<E> {

    private Integer pageNo;//页码
    private Integer pageSize;//页面大小
    private Integer total;//总条数
    private Integer sumPages;//总页数
    private List<Integer> pageList = new ArrayList<Integer>();//页码列表
    private List<E> dataList;//页面数据列表

    /**
     * @param pageNo
     * @param pageSize
     * @param total
     * @param sumPages
     * @param pageList
     * @param dataList
     */
    public PageBean(Integer pageNo, Integer pageSize, int total, int sumPages,
                    List<Integer> pageList, List<E> dataList) {
        super();
        if (pageNo == null || pageNo.intValue() == 0) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
        if (pageSize == null || pageSize.intValue() == 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
        this.total = total;
        this.sumPages = sumPages;
        this.pageList = pageList;
        this.dataList = dataList;
    }

    public PageBean(Integer pageNo, Integer pageSize, Map<String, Object> dataMap) {
        if (pageNo == null || pageNo.intValue() == 0) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
        if (pageSize == null || pageSize.intValue() == 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
        //总条数
        total = (Integer) dataMap.get("total");
        //数据list
        dataList = (List<E>) dataMap.get("rows");
        if (total == null || total == 0) {
            dataList = null;
            return;
        }
        //计算总页数
        sumPages = total % this.pageSize == 0 ? total / this.pageSize
                : ((total / this.pageSize) + 1);
        //计算页码显示
        int pageNoTemp = this.pageNo;
        for (int i = pageNoTemp - 4; i <= pageNoTemp + 4; i++) {
            if (i <= 0) {
                pageNoTemp++;
                continue;
            }
            if (i > sumPages) {
                break;
            }
            pageList.add(i);
        }
    }

    public PageBean(Integer pageNo, Integer pageSize, Integer total, List<E> dataList) {
        if (pageNo == null || pageNo.intValue() == 0) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
        if (pageSize == null || pageSize.intValue() == 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
        if (total == null || total == 0) {
            dataList = null;
            return;
        }
        this.dataList = dataList;
        //计算总页数
        sumPages = total % this.pageSize == 0 ? total / this.pageSize
                : ((total / this.pageSize) + 1);
        //计算页码显示
        int pageNoTemp = this.pageNo;
        for (int i = pageNoTemp - 4; i <= pageNoTemp + 4; i++) {
            if (i <= 0) {
                pageNoTemp++;
                continue;
            }
            if (i > sumPages) {
                break;
            }
            pageList.add(i);
        }
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSumPages() {
        return sumPages;
    }

    public void setSumPages(Integer sumPages) {
        this.sumPages = sumPages;
    }

    public List<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(List<Integer> pageList) {
        this.pageList = pageList;
    }

    public List<?> getDataList() {
        if (dataList == null) {
            dataList = new ArrayList();
        }
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }


}
