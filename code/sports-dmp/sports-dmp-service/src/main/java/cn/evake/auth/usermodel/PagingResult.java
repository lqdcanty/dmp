package cn.evake.auth.usermodel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wangyi
 * @desc @param <T>
 * @version $Id: PagingResult.java, v 0.1 2017年10月29日 下午6:20:41 wangyi Exp $
 */
@SuppressWarnings("all")
public class PagingResult<T> implements Serializable {

    /**  */
    private static final long  serialVersionUID = 1628255073903569585L;

    public static final String STARTINDEX       = "startIndex";

    public static final String LIMITNUM         = "limitNum";

    private List<T>            list;
    private long               allRow;                                 //总记录数
    private int                totalPage;                              //总页数
    private int                currentPage;                            //当前页
    private int                pageSize;                               //每页记录数
    private boolean            isFirstPage;                            //是否为第一页
    private boolean            isLastPage;                             //是否为最后一页
    private boolean            hasPreviousPage;                        //是否有前一页
    private boolean            hasNextPage;                            //是否有下一页

    private int                beginPage;
    private int                pageLength;

    public PagingResult() {
        super();
    }

    public PagingResult(List<T> list, long allRow, int currentPage, int pageSize) {
        super();
        this.list = list;
        this.allRow = allRow;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        init();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getAllRow() {
        return allRow;
    }

    public void setAllRow(long allRow) {
        this.allRow = allRow;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void init() {
        //计算当前页
        countCurrentPage();
        //计算总页数
        countTotalPage();
        this.isFirstPage = isFirstPage();
        this.isLastPage = isLastPage();
        this.hasPreviousPage = isHasPreviousPage();
        this.hasNextPage = isHasNextPage();

        if (this.currentPage - 4 < 0) {
            this.beginPage = 1;
            this.pageLength = totalPage <= 4 ? totalPage - 1 : 4;
        } else if (this.totalPage - 4 < 0) {
            this.beginPage = this.totalPage - 4;
            this.pageLength = 4;
        } else if (this.totalPage - this.currentPage < 4) {
            this.beginPage = this.totalPage - 4;
            this.pageLength = 4;
        } else {
            this.beginPage = this.currentPage - 2;
            this.pageLength = 4;

        }
    }

    public boolean isFirstPage() {
        return currentPage == 1; // 如是当前页是第1页
    }

    public boolean isLastPage() {
        return currentPage == totalPage; //如果当前页是最后一页
    }

    public boolean isHasPreviousPage() {
        return currentPage != 1; //只要当前页不是第1页
    }

    public boolean isHasNextPage() {
        return currentPage != totalPage; //只要当前页不是最后1页
    }

    public void countTotalPage() {
        totalPage = (int) (allRow % pageSize == 0 ? allRow / pageSize : allRow / pageSize + 1);
    }

    public static int countOffset(final int currentPage, final int pageSize) {
        final int offset = (pageSize * (currentPage - 1));
        return offset;
    }

    public void countCurrentPage() {
        currentPage = (currentPage == 0 ? 1 : currentPage);
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getPageLength() {
        return pageLength;
    }

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }

    /**
     * 生成分页查询基本参数maps
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static Map<String, Object> getParamsMap(int currentPage, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startIndex", countOffset(currentPage, pageSize));
        params.put("limitNum", pageSize);
        return params;
    }

}
