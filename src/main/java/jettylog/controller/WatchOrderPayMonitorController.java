package jettylog.controller;

import jettylog.Constants;
import jettylog.file.ReadFile;
import jettylog.service.IWatchOrderPayService;
import jettylog.utils.CombineString;
import jettylog.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User:huangtao
 * Date:2015-10-09
 * description：
 */
@RestController
@RequestMapping("/read/log")
public class WatchOrderPayMonitorController {

    @Autowired
    private IWatchOrderPayService watchOrderPayService;
    @Autowired
    private ReadFile readFile;
    @Autowired
    private CombineString combineString;

    @RequestMapping(value = "/watch/order/pay", method = RequestMethod.GET)
    @ResponseBody
    public synchronized String startLogTask() {
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat(Constants.Task.SIMPLE_DATE_FORMAT);
        String nowTime = format.format(nowDate);
        StringBuffer dateBuffer = new StringBuffer();
        dateBuffer.append(nowTime);
        String localFileLocalPath = ReadFile.getFilePath(dateBuffer.toString(), Constants.Task.WATCH_ORDER_PAY_MONITOR);
        String url = ReadFile.getFileUrl("", Constants.Task.WATCH_ORDER_PAY_MONITOR);
        try {
            HttpUtil.downloadFile(localFileLocalPath, url);
            Map<String, String> parMap = readFile.readMonitorFile(localFileLocalPath);
            if (null != parMap) {
                String date = parMap.get("date");
                String monitorName = parMap.get("monitorName");
                if (null != date && null != monitorName && !date.isEmpty() && !monitorName.isEmpty()) {
                    watchOrderPayService.createWatchOrderPayMonitor(date);
                    List<String> stringList = readFile.readFileByLine(localFileLocalPath);
                    List<String> tempList = new LinkedList<String>();
                    if (null != stringList) {
                        for (String tempString : stringList) {
                                if (!tempString.isEmpty() && null != tempString.trim()) {
                                    if (!tempString.startsWith("-----------------------------------------")
                                            && !tempString.startsWith("ms     %     Task name")) {
                                        tempList.add(tempString);
                                    }
                                } else {
                                    watchOrderPayService.insertWatchOrderPayMonitor(tempList, monitorName);
                                    tempList.clear();
                                }
                        }
                    }
                    watchOrderPayService.insertWatchOrderPayDailyMonitor();
                }
                return "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 批量操作
     *
     * @param taskNumber 监控器名称，示例：watch_order_pay(注意不带monitor。统一制定)
     * @param taskNumber 任务数量
     * @param startTime  起始时间（示例：2015-09-10）
     */
    @RequestMapping(value = "/watch/order/pay/{taskNumber}/{startTime}", method = RequestMethod.GET)
    @ResponseBody
    public String batchTask(@PathVariable("taskNumber")Integer taskNumber, @PathVariable("startTime")String startTime) {
            if(!(null == taskNumber || 0 == taskNumber.intValue()
                || null == startTime || startTime.trim().isEmpty()
                || !combineString.recogizePureDate(startTime))){
        List<String> dateTask = combineString.taskTime(startTime,taskNumber);
        String monitorName = Constants.Task.WATCH_ORDER_PAY_MONITOR;
        for (int i=0; i < dateTask.size(); i++) {
            String localFileLocalPath = ReadFile.getFilePath(dateTask.get(i).toString(), monitorName);
            String url = ReadFile.getFileUrl("." + dateTask.get(i).toString(), monitorName);
            try {
                HttpUtil.downloadFile(localFileLocalPath, url);
                Map<String, String> parMap = readFile.readMonitorFile(localFileLocalPath);
                if (null != parMap) {
                    String date = parMap.get("date");
                    if (null != date && null != monitorName && !date.isEmpty() && !monitorName.isEmpty()) {
                        watchOrderPayService.createWatchOrderPayMonitor(date);
                        List<String> stringList = readFile.readFileByLine(localFileLocalPath);
                        List<String> tempList = new LinkedList<String>();
                        if (null != stringList && stringList.size()>0) {
                            for (String tempString : stringList) {
                                if (!tempString.isEmpty() && null != tempString.trim()) {
                                    if (!tempString.startsWith("-----------------------------------------")
                                            && !tempString.startsWith("ms     %     Task name")) {
                                        tempList.add(tempString);
                                    }
                                } else {
                                    watchOrderPayService.insertWatchOrderPayMonitor(tempList, monitorName);
                                    tempList.clear();
                                }
                            }
                        }
                        watchOrderPayService.insertWatchOrderPayDailyMonitor();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }
                return "ok";
        }
        return "params is illegal";
    }
}
