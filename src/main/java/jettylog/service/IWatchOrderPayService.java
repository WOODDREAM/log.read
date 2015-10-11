package jettylog.service;

import java.util.List;

/**
 * User:huangtao
 * Date:2015-10-09
 * description：
 */
public interface IWatchOrderPayService {
    public void createWatchOrderPayMonitor(String date) throws Exception;
    public void insertWatchOrderPayMonitor(List<String> stringList, String monitorName) throws Exception;
    public void insertWatchOrderPayDailyMonitor() throws Exception;
}
