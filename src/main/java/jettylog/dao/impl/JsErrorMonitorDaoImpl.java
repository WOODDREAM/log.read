package jettylog.dao.impl;

import jettylog.bean.JsErrorMonitor;
import jettylog.dao.IJsErrorMonitorDao;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * User:huangtao
 * Date:2015-10-09
 * description：
 */
@Repository(value = "jsErrorMonitorDao")
public class JsErrorMonitorDaoImpl implements IJsErrorMonitorDao {
    @Autowired
    private Dao nutzDao;

    @Override
    public void createJsErrorMonitorTable(final String date) throws Exception {
        try {
            Trans.exec(new Atom() {
                public void run() {
                    //如果dailyMonitor表中存在此日期的日志，先删除此日期的所有信息，再创建表
                    nutzDao.create(JsErrorMonitor.class, false);
                    List<JsErrorMonitor> jsErrorMonitorList = nutzDao.query(JsErrorMonitor.class, Cnd.where("run_date", "LIKE", date).asc("id"));
                    if (0 != jsErrorMonitorList.size()) {
                        for (JsErrorMonitor monitorDetail : jsErrorMonitorList) {
                            nutzDao.delete(monitorDetail);
                        }
                    }
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void insertJsErrorMonitorTable(JsErrorMonitor jsErrorMonitor) throws Exception {
        try {
            if (null != jsErrorMonitor) {
                nutzDao.insert(jsErrorMonitor);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public QueryResult queryJsErrorMonitorTable(String date,int pagerNumber, int pagerSize) throws Exception {

        Pager pager = nutzDao.createPager(pagerNumber,pagerSize);
        nutzDao.query(JsErrorMonitor.class,Cnd.where("run_date","LIKE",date),pager);
        LinkedList<JsErrorMonitor> linkedList = (LinkedList<JsErrorMonitor>) nutzDao.query(JsErrorMonitor.class,Cnd.where("run_date","LIKE",date),pager);
        pager.setRecordCount(nutzDao.count(JsErrorMonitor.class));
        return new QueryResult(linkedList,pager);
    }
}
