package jettylog.controller;

import jettylog.Constants;
import jettylog.file.ReadFile;
import jettylog.service.IMonitorsService;
import jettylog.utils.CombineString;
import jettylog.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User:huangtao
 * Date:2015-10-09
 * description：
 */
@RestController
@RequestMapping("/read/log")
public class MonitorsController {
    @Autowired
    private ReadFile readFile;
    @Autowired
    private IMonitorsService monitorsService;
    @Autowired
    private CombineString combineString;

    /**
     * 下载并读取日志文件
     *
     * @param monitorName 监控器名称，示例：watch_order_pay(注意不带monitor。统一制定)
     */
    @RequestMapping(value = "/{monitor}", method = RequestMethod.GET)
    @ResponseBody
    public synchronized String startLogTask(@PathVariable("monitor") String monitorName) {
        if (null != monitorName && !monitorName.isEmpty() && (
                monitorName.equals(Constants.Task.OAUTH_MONITOR) ||
                        monitorName.endsWith(Constants.Task.SERVICE_MONITOR) ||
                        monitorName.equals(Constants.Task.STOP_WATCH_MONITOR) ||
                        monitorName.equals(Constants.Task.SYS_MONITOR) ||
                        monitorName.equals(Constants.Task.WATCH_MODIFY_CART_MONITOR) ||
                        monitorName.endsWith(Constants.Task.PAYMENT_MONITOR))) {
            Date nowDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat(Constants.Task.SIMPLE_DATE_FORMAT);
            String nowTime = format.format(nowDate);
            StringBuffer dateBuffer = new StringBuffer();
            dateBuffer.append(nowTime);
            String localFileLocalPath = ReadFile.getFilePath(dateBuffer.toString(), monitorName);
            String url = ReadFile.getFileUrl("", monitorName);
            try {
                HttpUtil.downloadFile(localFileLocalPath, url);
                Map<String, String> parMap = readFile.readMonitorFile(localFileLocalPath);
                if (null != parMap) {
                    String date = parMap.get("date");
                    String monitor = parMap.get("monitorName");
                    if (null != date && null != monitor) {
                        monitorsService.createMonitorsTable(monitorName, date);
                        List<String> stringList = readFile.readFileByLine(localFileLocalPath);
                        if (null != stringList) {
                            for (String string : stringList) {
                                if(combineString.recognizeStartWithData(string) || combineString.recognizeStartWithErrorCode(string)){
                                    monitorsService.insertMonitors(string, monitorName, date);
                                }
                            }
                        }
                    }
                    return "ok";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "error";
    }

    /**
     * 批量操作
     *
     * @param monitorName 监控器名称，示例：watch_order_pay(注意不带monitor。统一制定)
     */
    @ResponseBody
    @RequestMapping("/batch/{monitorName}/{taskNumber}/{startTime}")
    public String batchTask(@PathVariable("monitorName") String monitorName,
                            @PathVariable("taskNumber") Integer taskNumber,
                            @PathVariable("startTime") String startTime) throws Exception {
        if (null == taskNumber || 0 == taskNumber.intValue()
                || null == startTime || startTime.trim().isEmpty()
                || !combineString.recognizeTaskDate(startTime)) {
            return "params is illegal";
        }
        List<String> dateTask = combineString.taskTime(startTime, taskNumber);
        for (int i = 0; i < dateTask.size(); i++) {
            String localFileLocalPath = ReadFile.getFilePath(dateTask.get(i).toString(), monitorName);
            String url = ReadFile.getFileUrl("." + dateTask.get(i).toString(), monitorName);
            try {
                HttpUtil.downloadFile(localFileLocalPath, url);
                Map<String, String> parMap = readFile.readMonitorFile(localFileLocalPath);
                if (null != parMap) {
                    String date = parMap.get("date");
                    if (null != date && null != monitorName && !date.isEmpty() && !monitorName.isEmpty()) {
                        monitorsService.createMonitorsTable(monitorName, date);
                        List<String> stringList = readFile.readFileByLine(localFileLocalPath);
                        if (null != stringList) {
                            for (String tempString : stringList) {
                                if(combineString.recognizeStartWithData(tempString) || combineString.recognizeStartWithErrorCode(tempString)){
                                    monitorsService.insertMonitors(tempString, monitorName, date);
                                }
                            }
                        }
                        return "ok";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "error";
    }

}
