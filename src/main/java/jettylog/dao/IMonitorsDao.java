package jettylog.dao;

import jettylog.bean.MonitorsDetail;

/**
 * User:huangtao
 * Date:2015-09-14
 * description：
 */
public interface IMonitorsDao {
    void createMonitorsTable(String monitorName, String date) throws Exception;

    void insertMonitors(MonitorsDetail monitorsDetail) throws Exception;
}
