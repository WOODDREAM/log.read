package jettylog.file;


import jettylog.Constants;
import jettylog.utils.CombineString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * User:huangtao
 * Date:2015-09-11
 * description：
 */
@Component
public class ReadFile {
    private final Logger LOGGER = Logger.getLogger(ReadFile.class.getName());

    @Autowired
    private CombineString combineString;

    private String monitorName;
    private String date;

    /**
     * 解析本地文件路径
     *
     * @param localFileLocalPath 本地文件路径
     * @return
     */
    public Map<String, String> readMonitorFile(String localFileLocalPath) {
        Map<String, String> parMap = new HashMap<String, String>();
        char tempChar;
        StringBuffer stringBuffer = new StringBuffer();

        int strLength = localFileLocalPath.length() - 1;
        for (int i = 0; i <= strLength; i++) {
            tempChar = localFileLocalPath.charAt(i);
            stringBuffer.append(tempChar);
            //去除.log
            if (stringBuffer.toString().equals("log.")) {
                stringBuffer.delete(0, stringBuffer.length());
                continue;
            }
            //去除\
            if (tempChar == '\\') {
                stringBuffer.delete(0, stringBuffer.length());
                continue;
            }
            //去除.，取出monitor名称
            if (tempChar == '.') {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                monitorName = stringBuffer.toString();
                stringBuffer.delete(0, stringBuffer.length());
                continue;
            }
            //取出日期
            if (combineString.recognizeDate(stringBuffer.toString().trim())) {
                date = stringBuffer.toString().trim();
            }
            //如果读到路径末尾，开始解析文件
            if (i == strLength) {
                if (null != monitorName && null != date) {
                    parMap.put("monitorName", monitorName);
                    parMap.put("date", date);
                    return parMap;
                }
            }
        }
        return null;
    }

    /**
     * @param localFileLocalPath 本地文件地址
     * @return
     * @throws Exception
     */
    public List<String> readFileByLine(String localFileLocalPath) throws Exception {
        List<String> stringList = new LinkedList<String>();
        File file = new File(localFileLocalPath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                stringList.add(tempString);
            }
            reader.close();
            return stringList;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getFilePath(String dateParam, String monitorName) {
        StringBuffer pathBuffer = new StringBuffer();
        if (monitorName.equalsIgnoreCase(Constants.Task.WATCH_ORDER_PAY_MONITOR)) {
            pathBuffer.append(Constants.File.WATCH_ORDER_PAY_MONITOR_PATH).append(dateParam);
        }
        if (monitorName.equalsIgnoreCase(Constants.Task.STOP_WATCH_MONITOR)) {
            pathBuffer.append(Constants.File.STOP_WATCH_MONITOR_PATH).append(dateParam);
        }
        if (monitorName.equals(Constants.Task.WATCH_MODIFY_CART_MONITOR)) {
            pathBuffer.append(Constants.File.WATCH_MODIFY_CART_MONITOR_PATH).append(dateParam);
        }
        if (monitorName.equals(Constants.Task.SERVICE_MONITOR)) {
            pathBuffer.append(Constants.File.SERVICE_MONITOR_PATH).append(dateParam);
        }
        if (monitorName.endsWith(Constants.Task.JS_ERROR_MONITOR)) {
            pathBuffer.append(Constants.File.JS_ERROR_MONITOR_PATH).append(dateParam);
        }
        if (monitorName.equals(Constants.Task.OAUTH_MONITOR)) {
            pathBuffer.append(Constants.File.OAUTH_MONITOR_PATH).append(dateParam);
        }
        if (monitorName.equals(Constants.Task.SYS_MONITOR)) {
            pathBuffer.append(Constants.File.SYS_MONITOR_PATH).append(dateParam);
        }
        if (monitorName.equals(Constants.Task.PAYMENT_MONITOR)) {
            pathBuffer.append(Constants.File.PAYMENT_MONITOR_PATH).append(dateParam);
        }
        return pathBuffer.toString().trim();
    }

    public static String getFileUrl(String dateParam, String monitorName) {
        if (monitorName.equalsIgnoreCase(Constants.Task.WATCH_ORDER_PAY_MONITOR)) {
            return String.format(Constants.File.WATCH_ORDER_PAY_MONITOR_URL, dateParam);
        }
        if (monitorName.equalsIgnoreCase(Constants.Task.STOP_WATCH_MONITOR)) {
            return String.format(Constants.File.STOP_WATCH_MONITOR_URL, dateParam);
        }
        if (monitorName.equals(Constants.Task.SERVICE_MONITOR)) {
            return String.format(Constants.File.SERVICE_MONITOR_URL, dateParam);
        }
        if (monitorName.equals(Constants.Task.WATCH_MODIFY_CART_MONITOR)) {
            return String.format(Constants.File.WATCH_MODIFY_CART_MONITOR_URL, dateParam);
        }
        if (monitorName.equals(Constants.Task.JS_ERROR_MONITOR)) {
            return String.format(Constants.File.JS_ERROR_MONITOR_URL, dateParam);
        }
        if (monitorName.equals(Constants.Task.OAUTH_MONITOR)) {
            return String.format(Constants.File.OAUTH_MONITOR_URL,dateParam);
        }
        if (monitorName.equals(Constants.Task.SYS_MONITOR)) {
            return String.format(Constants.File.SYS_MONITOR_URL,dateParam);
        }
        if (monitorName.equals(Constants.Task.PAYMENT_MONITOR)) {
            return String.format(Constants.File.PAYMENT_MONITOR_URL,dateParam);
        }
        return "";
    }
}
