package jettylog.dao.impl;

import jettylog.Constants;
import jettylog.bean.WatchOrderPayDailyMonitor;
import jettylog.bean.WatchOrderPayMonitor;
import jettylog.bean.WatchOrderPayMonitorDetails;
import jettylog.dao.IWatchOrderPayDao;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User:huangtao
 * Date:2015-10-09
 * description：
 */
@Repository(value = "watchOrderPayDao")
public class WatchOrderPayDaoImpl implements IWatchOrderPayDao {
    @Autowired
    private Dao nutzDao;
    @Override
    public void createWatchOrderPayTable(final String date) throws Exception {
        try {
            nutzDao.create(WatchOrderPayDailyMonitor.class, false);
            Trans.exec(new Atom() {
                public void run() {
                    //如果dailyMonitor表中存在此日期的日志，先删除此日期的所有信息，再创建表
                     WatchOrderPayDailyMonitor watchOrderPayDailyMonitor = nutzDao.fetch(WatchOrderPayDailyMonitor.class, Cnd.where("monitor_name", "LIKE", Constants.Task.WATCH_ORDER_PAY_MONITOR).and("run_date", "LIKE", date).asc("id"));
                    if (watchOrderPayDailyMonitor != null) {
                        List<WatchOrderPayMonitor> monitorList = nutzDao.query(WatchOrderPayMonitor.class, Cnd.where("monitor_name", "LIKE", Constants.Task.WATCH_ORDER_PAY_MONITOR).and("run_date", "LIKE", date).asc("id"));
                        if (0 != monitorList.size()) {
                            for (WatchOrderPayMonitor monitor : monitorList) {
                                monitor.setMonitorDetailsList(nutzDao.query(WatchOrderPayMonitorDetails.class, Cnd.where("monitor_id", "=", monitor.getId()).asc("id")));
                                nutzDao.deleteWith(monitor, "monitorDetailsList");
                            }
                            nutzDao.clear(WatchOrderPayDailyMonitor.class, Cnd.where("monitor_name", "LIKE", Constants.Task.WATCH_ORDER_PAY_MONITOR).and("run_date", "LIKE", date).asc("id"));
                        }

                    } else {
                        nutzDao.create(WatchOrderPayMonitor.class, false);
                        nutzDao.create(WatchOrderPayMonitorDetails.class, false);
                    }
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void insertWatchOrderPayMonitor(final WatchOrderPayMonitor watchOrderPayMonitor) throws Exception {
        try {
            if (watchOrderPayMonitor.getMonitorName() != null) {
                TableName.run(watchOrderPayMonitor.getMonitorName(), new Runnable() {
                    public void run() {
                        nutzDao.insertWith(watchOrderPayMonitor, "monitorDetailsList");
                    }
                });
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void insertWatchOrderPayDailyMonitor(WatchOrderPayDailyMonitor watchOrderPayDailyMonitor) throws Exception {
        try {
            if (watchOrderPayDailyMonitor.getMonitorName() != null) {
                nutzDao.insert(watchOrderPayDailyMonitor);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
