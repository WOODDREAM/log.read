package jettylog.dao;

import jettylog.bean.WatchOrderPayDailyMonitor;
import jettylog.bean.WatchOrderPayMonitor;

/**
 * User:huangtao
 * Date:2015-10-09
 * description：
 */
public interface IWatchOrderPayDao {
    public void createWatchOrderPayTable(String date) throws Exception;
    public void insertWatchOrderPayMonitor(WatchOrderPayMonitor watchOrderPayMonitor) throws Exception;
    public void insertWatchOrderPayDailyMonitor(WatchOrderPayDailyMonitor watchOrderPayDailyMonitor) throws Exception;
}
