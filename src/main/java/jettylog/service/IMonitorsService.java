package jettylog.service;

/**
 * User:huangtao
 * Date:2015-09-14
 * description：
 */
public interface IMonitorsService {
    public void insertMonitors(String string, String monitorName,String date) throws Exception;
    public void createMonitorsTable(String monitorName, String date) throws Exception;
}
