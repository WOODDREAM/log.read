package jettylog.service;

/**
 * User:huangtao
 * Date:2015-09-29
 * description：
 */
public interface IJsErrorMonitorService {
    public void createJSTable(String date) throws Exception;

    public void insertJsErrorMonitor(String string) throws Exception;
}
