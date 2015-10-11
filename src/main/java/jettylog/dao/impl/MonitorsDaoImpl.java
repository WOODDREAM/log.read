package jettylog.dao.impl;

import jettylog.bean.MonitorsDetail;
import jettylog.dao.IMonitorsDao;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User:huangtao
 * Date:2015-09-14
 * description：
 */
@Repository(value = "monitorsDao")
public class MonitorsDaoImpl implements IMonitorsDao {

    @Autowired
    private Dao nutzDao;

    @Override
    public void createMonitorsTable(final String monitorName,final String date) throws Exception {
        try {
            Trans.exec(new Atom() {
                public void run() {
                    //如果dailyMonitor表中存在此日期的日志，先删除此日期的所有信息，再创建表
                    nutzDao.create(MonitorsDetail.class, false);
                    List<MonitorsDetail> monitorList = nutzDao.query(MonitorsDetail.class, Cnd.where("monitor_name", "LIKE", monitorName).and("run_date", "LIKE", date).asc("id"));
                    if (0 != monitorList.size()) {
                        for (MonitorsDetail monitorsDetail : monitorList) {
                            nutzDao.delete(monitorsDetail);
                        }
                    }
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void insertMonitors(MonitorsDetail monitorsDetail) throws Exception {
        try {
            if (null != monitorsDetail) {
                nutzDao.insert(monitorsDetail);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
