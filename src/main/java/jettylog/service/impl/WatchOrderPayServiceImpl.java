package jettylog.service.impl;

import jettylog.bean.WatchOrderPayDailyMonitor;
import jettylog.bean.WatchOrderPayMonitor;
import jettylog.bean.WatchOrderPayMonitorDetails;
import jettylog.dao.IWatchOrderPayDao;
import jettylog.service.IWatchOrderPayService;
import jettylog.utils.CombineString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User:huangtao
 * Date:2015-10-09
 * description：
 */
@Service
public class WatchOrderPayServiceImpl implements IWatchOrderPayService {

    @Autowired
    private IWatchOrderPayDao watchOrderPayDao;
    @Autowired
    private CombineString combineString;

    private int monitorCount;
    private int firstFailedCount;
    private int secondFailedCount;
    private int succeedTime;
    private int failedTime;
    private int firstFailedTime;
    private int secondFailedTime;
    private String monitorDate = null;
    private String dailyMonitorName = null;
    private WatchOrderPayDailyMonitor dailyMonitor = new WatchOrderPayDailyMonitor();
    @Override
    public void createWatchOrderPayMonitor(String date) throws Exception {
        monitorDate = date;
        watchOrderPayDao.createWatchOrderPayTable(date);
    }

    @Override
    public void insertWatchOrderPayMonitor(List<String> stringList,String monitorName) throws Exception {
        dailyMonitorName = monitorName;
        List<WatchOrderPayMonitorDetails> watchOrderPayMonitorDetailsList = new LinkedList<WatchOrderPayMonitorDetails>();
        if (null == stringList || stringList.size() == 0) {
            return;
        } else {
            WatchOrderPayMonitor watchOrderPayMonitor = null;
            int childTask = 0;
            for (String tempString : stringList) {
                //第一条为主表所需字符串
                if (childTask == 0) {
                    watchOrderPayMonitor = combinePrimaryTable(tempString);
                } else {
                    //解析子表
                    watchOrderPayMonitorDetailsList.add(combineSubordinateTableWOP(tempString));
                }
                childTask++;
            }
            if (null != watchOrderPayMonitor ) {
                int newChildTask = --childTask;
                watchOrderPayMonitor.setChildNumber(newChildTask);
                if (newChildTask != 0) {
                    watchOrderPayMonitor.setAverageTime(watchOrderPayMonitor.getRunTime() / newChildTask);
                }
                watchOrderPayMonitor.setMonitorDetailsList(watchOrderPayMonitorDetailsList);
                watchOrderPayMonitor.setMonitorName(monitorName);

                //统计日常表所需数据
                createDailyMonitorDetails(newChildTask, watchOrderPayMonitor);

                watchOrderPayDao.insertWatchOrderPayMonitor(watchOrderPayMonitor);
            }
            else {
                //错误处理
                System.out.println("不和法字符串!!!!");
            }
        }
    }

    /**
     * 统计数据
     *
     * @param newChildTask
     * @param monitor
     */
    private void createDailyMonitorDetails(int newChildTask, WatchOrderPayMonitor monitor) {
        if (null != monitor && 0 != monitor.getMonitorDetailsList().size()) {
            monitorCount += newChildTask;
            //失败任务的第一步
            if (newChildTask == 1) {
                if (null != monitor.getMonitorDetailsList().get(0)) {

                    firstFailedCount += newChildTask;
                    firstFailedTime += monitor.getMonitorDetailsList().get(0).getRunTime();
                }
            }
            //失败任务的第二步
            if (newChildTask == 2) {
                if (null != monitor.getMonitorDetailsList().get(1)) {

                    firstFailedCount += newChildTask;
                    firstFailedTime += monitor.getMonitorDetailsList().get(0).getRunTime();
                    secondFailedCount += newChildTask;
                    secondFailedTime += monitor.getMonitorDetailsList().get(1).getRunTime();
                }
            }
            //成功任务
            if (newChildTask >= 3) {
                succeedTime += monitor.getRunTime();
            } else {
                failedTime += monitor.getRunTime();
            }
        }
    }
    /**
     * 组装主表实体
     *
     * @param tempString
     */
    private WatchOrderPayMonitor combinePrimaryTable(String tempString) {
        WatchOrderPayMonitor monitor = new WatchOrderPayMonitor();
        StringBuffer stringBuffer = new StringBuffer();
        char tempChar;
        int strLength = tempString.length();
        for (int i = 0; i <= strLength; i++) {
            if (i != strLength && (tempChar = tempString.charAt(i)) != ' '
                    && tempChar != '\'') {
                stringBuffer.append(tempChar);
            } else {
                if (null != stringBuffer.toString() && !stringBuffer.toString().isEmpty()) {

                    if (combineString.recognizeDate(stringBuffer.toString().trim())) {
                        monitor.setRunDate(stringBuffer.toString().trim());
                    }
                    if (combineString.recognizeEndTime(stringBuffer.toString().trim())) {
                        monitor.setEndTime(stringBuffer.toString().trim().substring(0, 8));
                    }
                    if (combineString.recognizeTask(stringBuffer.toString().trim())) {
                        monitor.setTaskName(stringBuffer.toString().trim());
                    }
                    if (combineString.recognizeRunTime(stringBuffer.toString().trim())) {
                        String time = stringBuffer.toString().trim();
                        String newStr = time.replaceFirst("^0*", "");
                        System.out.println("time: " + newStr);
                        if (null != newStr && !newStr.isEmpty()) {
                            monitor.setRunTime(Integer.valueOf(newStr));
                        }
                    }
                    stringBuffer.delete(0, strLength);
                }
            }
        }
        return monitor;
    }

    /**
     * 组装watch_order_pay从表实体
     *
     * @param tempString
     */
    private WatchOrderPayMonitorDetails combineSubordinateTableWOP(String tempString) {

        WatchOrderPayMonitorDetails monitorDetails = new WatchOrderPayMonitorDetails();
        StringBuffer stringBuffer = new StringBuffer();
        char tempChar;
        int strLength = tempString.length();
        for (int i = 0; i <= strLength; i++) {
            if (i != strLength && (tempChar = tempString.charAt(i)) != ' ') {
                stringBuffer.append(tempChar);
            } else {
                if (null != stringBuffer.toString() && !stringBuffer.toString().isEmpty()) {
                    if (combineString.recognizeDate(stringBuffer.toString().trim())) {
                        monitorDetails.setRunDate(stringBuffer.toString().trim());
                        stringBuffer.delete(0, stringBuffer.length());
                        continue;
                    }
                    if (combineString.recognizeRunTime(stringBuffer.toString().trim())) {
                        monitorDetails.setRunTime(Integer.valueOf(stringBuffer.toString().trim()));
                        stringBuffer.delete(0, stringBuffer.length());
                        continue;
                    }
                    if (combineString.recognizeEndTime(stringBuffer.toString().trim())) {
                        monitorDetails.setEndTime(Time.valueOf(stringBuffer.toString().trim()));
                    }
                    if (combineString.recognizePercentage(stringBuffer.toString().trim())) {
                        String newStr = stringBuffer.toString().trim().replaceFirst("^0*", "");
                        if (null != newStr && !newStr.isEmpty()) {
                            monitorDetails.setTimePercent(newStr);
                        }
                        stringBuffer.delete(0, stringBuffer.length());
                        continue;
                    }
                    if (combineString.recognizeGetFunction(stringBuffer.toString().trim())) {
                        Map map = fetchAttribute(stringBuffer.toString().trim());
                        if (map != null) {
                            assert monitorDetails != null;
                            monitorDetails.setTaskName((String) map.get("taskName"));
                            monitorDetails.setTaskId((String) map.get("taskId"));
                            stringBuffer.delete(0, stringBuffer.length());
                            continue;
                        }
                    }
                    //如果没有一个匹配到
                    stringBuffer.delete(0, strLength);
                }
            }
        }
        return monitorDetails;
    }
    /**
     * 解析连续字符串
     *
     * @param tempString
     * @return
     */
    private Map fetchAttribute(String tempString) {

        Map<String, String> propertyMap = new HashMap<String, String>();
        char tempChar;
        StringBuffer stringBuffer = new StringBuffer();
        int strLength = tempString.length() - 1;
        for (int i = 0; i <= strLength; i++) {
            tempChar = tempString.charAt(i);
            if (i != strLength && tempChar != ':' && tempChar != '(' && tempChar != ')') {
                stringBuffer.append(tempChar);
            } else {
                if (null != stringBuffer.toString() && !stringBuffer.toString().trim().isEmpty()) {
                    if (combineString.recognizeRunTime(stringBuffer.toString().trim())) {
                        //暂时不做处理
                        stringBuffer.delete(0, stringBuffer.length());
                        continue;
                    }
                    if (combineString.recognizeTask(stringBuffer.toString().trim())) {
                        propertyMap.put("taskName", stringBuffer.toString().trim());
                    } else {
                        if (stringBuffer.toString().trim() != "null") {
                            propertyMap.put("taskId", stringBuffer.toString().trim());
                        }
                    }
                    stringBuffer.delete(0, stringBuffer.length());
                }

            }
        }
        return propertyMap;
    }
    @Override
    public void insertWatchOrderPayDailyMonitor() throws Exception {
        try {
            if (createDailyMonitor()) {
                watchOrderPayDao.insertWatchOrderPayDailyMonitor(dailyMonitor);
                dailyMonitor = null;
                dailyMonitor = new WatchOrderPayDailyMonitor();
                monitorCount = 0;
                firstFailedCount = 0;
                firstFailedTime = 0;
                secondFailedCount = 0;
                secondFailedTime = 0;
                succeedTime = 0;
                failedTime = 0;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean createDailyMonitor() throws Exception {

        try {
            dailyMonitor.setRunDate(monitorDate);
            dailyMonitor.setSucceedTime(succeedTime);
            dailyMonitor.setMonitorCount(monitorCount);
            dailyMonitor.setMonitorName(dailyMonitorName);
            dailyMonitor.setFirstFailedCount(firstFailedCount);
            dailyMonitor.setFirstFailedTime(firstFailedTime);
            dailyMonitor.setSecondFailedCount(secondFailedCount);
            dailyMonitor.setSecondFailedTime(secondFailedTime);
            if (monitorCount != 0) {
                dailyMonitor.setSucceedPercent(String.valueOf(100 * (monitorCount - (firstFailedCount)) / monitorCount) + "%");
                dailyMonitor.setSecondFailedPercent(String.valueOf((100 * secondFailedCount / monitorCount)) + "%");
                dailyMonitor.setFirstFailedPercent(String.valueOf((100 * firstFailedCount / monitorCount)) + "%");
                dailyMonitor.setFailedPercent(String.valueOf((100 * firstFailedCount) / monitorCount) + "%");
            }
            if (firstFailedCount != 0) {
                dailyMonitor.setFirtFailedAverageTime(firstFailedTime / firstFailedCount);
                dailyMonitor.setFailedAverageTime(failedTime / (firstFailedCount + secondFailedCount));
            }
            if (secondFailedCount != 0) {
                dailyMonitor.setSecondFailedAverageTime(secondFailedTime / secondFailedCount);
            }
            if (firstFailedCount != monitorCount) {
                dailyMonitor.setSucceedAverageTime(succeedTime / (monitorCount - firstFailedCount));
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
