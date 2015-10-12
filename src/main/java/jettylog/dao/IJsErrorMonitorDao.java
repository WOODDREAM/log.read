package jettylog.dao;

import jettylog.bean.JsErrorMonitor;
import org.nutz.dao.QueryResult;

/**
 * User:huangtao
 * Date:2015-10-09
 * description：
 */
public interface IJsErrorMonitorDao {
    public void createJsErrorMonitorTable(String date) throws Exception;
    public void insertJsErrorMonitorTable(JsErrorMonitor jsErrorMonitor) throws Exception;
    public QueryResult queryJsErrorMonitorTable(String date,int pagerNumber, int pagerSize) throws Exception;
}
