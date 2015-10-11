package jettylog.service.impl;

import jettylog.bean.JsErrorMonitor;
import jettylog.dao.IJsErrorMonitorDao;
import jettylog.service.IJsErrorMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User:huangtao
 * Date:2015-09-29
 * description：
 */
@Service
public class JsErrorMonitorServiceImpl implements IJsErrorMonitorService {

    @Autowired
    private IJsErrorMonitorDao jsErrorMonitorDao;

    @Override
    public void createJSTable(String date) throws Exception {

        try {
            //创建表
            jsErrorMonitorDao.createJsErrorMonitorTable(date);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void insertJsErrorMonitor(String string) throws Exception {
        if (string.isEmpty()) {
            return;
        } else {
            JsErrorMonitor jsErrorMonitor = new JsErrorMonitor();

            jsErrorMonitor.setRunDate(string.substring(0, 10));
            String[] tempString = string.split("\"navigator\":");
            if (tempString[0].contains("time")) {
                String timeStr = tempString[0].toString().trim();
                jsErrorMonitor.setTime(timeStr.substring(timeStr.length() - 26, timeStr.length() - 3));
            }
            String[] secondStr = tempString[1].split(",\"errorMessage\":");
            jsErrorMonitor.setNavigator(secondStr[0].toString().trim());
            String[] thirdStr = secondStr[1].split(",\"scripturi\":");
            String errorStr = thirdStr[0].toString().trim();
            if (!errorStr.isEmpty()) {
                jsErrorMonitor.setErrorMessage(errorStr.substring(1, errorStr.length() - 2));
            }
            String[] fourthStr = thirdStr[1].split(",\"lineNumber\":");
            String scriptUrlStr = fourthStr[0].toString().trim();
            if (!scriptUrlStr.isEmpty()) {
                jsErrorMonitor.setScripturi(scriptUrlStr);
            }
            String[] fiveStr = null;
            if (fourthStr[1].contains("columnNumber")) {
                String[] columnStr = fourthStr[1].split(",\"columnNumber\":");
                if (!columnStr[0].isEmpty()) {
                    jsErrorMonitor.setLineNumber(columnStr[0]);
                }
                fourthStr[1] = columnStr[1];
                fiveStr = fourthStr[1].split(",\"errorObj\":");
                if(!fiveStr[0].isEmpty()){
                    jsErrorMonitor.setColumnNumber(fiveStr[0]);
                }
            }else {
                fiveStr = fourthStr[1].split(",\"errorObj\":");
                jsErrorMonitor.setLineNumber(fiveStr[0].toString().trim());
            }
            String errorObjStr = fiveStr[1].toString().trim();
            jsErrorMonitor.setErrorObj(errorObjStr.substring(0, errorObjStr.length() - 1));
            jsErrorMonitorDao.insertJsErrorMonitorTable(jsErrorMonitor);
        }

    }
}
