package jettylog.vo;

import java.io.Serializable;
import java.util.List;

/**
 * User:huangtao
 * Date:2015-10-12
 * description：
 */
public class JsErrorMonitorVo implements Serializable {
    private int pageNumber;
    private int pageSize;
    private int pageCount;
    private int recordCount;
    private List<JsErrormonitorForm> jsErrormonitorForms;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public List<JsErrormonitorForm> getJsErrormonitorForms() {
        return jsErrormonitorForms;
    }

    public void setJsErrormonitorForms(List<JsErrormonitorForm> jsErrormonitorForms) {
        this.jsErrormonitorForms = jsErrormonitorForms;
    }
}
