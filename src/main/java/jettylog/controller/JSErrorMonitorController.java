package jettylog.controller;

import jettylog.Constants;
import jettylog.file.ReadFile;
import jettylog.service.IJsErrorMonitorService;
import jettylog.utils.CombineString;
import jettylog.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
public class JSErrorMonitorController {

    @Autowired
    private ReadFile readFile;
    @Autowired
    private IJsErrorMonitorService jsErrorMonitorService;
    @Autowired
    private CombineString combineString;

    @ResponseBody
    @RequestMapping(value = "/js/error")
    public String readJsErrorMonitorFile() {
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat(Constants.Task.SIMPLE_DATE_FORMAT);
        String nowTime = format.format(nowDate);
        StringBuffer dateBuffer = new StringBuffer();
        dateBuffer.append(nowTime);
        String localFileLocalPath = ReadFile.getFilePath(dateBuffer.toString(), Constants.Task.JS_ERROR_MONITOR);
        String url = ReadFile.getFileUrl("", Constants.Task.JS_ERROR_MONITOR);
        try {
            HttpUtil.downloadFile(localFileLocalPath, url);
            Map<String, String> parMap = readFile.readMonitorFile(localFileLocalPath);
            if (null != parMap) {
                String date = parMap.get("date");
                String monitor = parMap.get("monitorName");
                if (null != date && null != monitor) {
                    jsErrorMonitorService.createJSTable(date);
                    List<String> stringList = readFile.readFileByLine(localFileLocalPath);
                    if (null != stringList) {
                        for (String string : stringList) {
                            if (combineString.recognizeStartWithData(string)) {
                                jsErrorMonitorService.insertJsErrorMonitor(string);
                            }
                        }
                    }
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
     */
    @ResponseBody
    @RequestMapping("/batch/{taskNumber}/{startTime}")
    public String batchTask(@PathVariable("taskNumber") Integer taskNumber,
                            @PathVariable("startTime") String startTime) throws Exception {
        if (!(null == taskNumber || 0 == taskNumber.intValue()
                || null == startTime || startTime.trim().isEmpty()
                || !combineString.recogizePureDate(startTime))) {
            List<String> dateTask = combineString.taskTime(startTime, taskNumber);
            for (int i = 0; i < dateTask.size(); i++) {
                String localFileLocalPath = ReadFile.getFilePath(dateTask.get(i).toString(), Constants.Task.JS_ERROR_MONITOR);
                String url = ReadFile.getFileUrl("." + dateTask.get(i).toString(), Constants.Task.JS_ERROR_MONITOR);
                try {
                    HttpUtil.downloadFile(localFileLocalPath, url);
                    Map<String, String> parMap = readFile.readMonitorFile(localFileLocalPath);
                    if (null != parMap) {
                        String date = parMap.get("date");
                        if (null != date && null != Constants.Task.JS_ERROR_MONITOR
                                && !date.isEmpty() && !Constants.Task.JS_ERROR_MONITOR.isEmpty()) {
                            jsErrorMonitorService.createJSTable(date);
                            List<String> stringList = readFile.readFileByLine(localFileLocalPath);
                            if (null != stringList && stringList.size()>0) {
                                for (String tempString : stringList) {
                                    jsErrorMonitorService.insertJsErrorMonitor(tempString);
                                }
                            }

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
