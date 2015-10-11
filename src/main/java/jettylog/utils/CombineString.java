package jettylog.utils;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User:huangtao
 * Date:2015-09-14
 * description：
 */
@Component
public class CombineString {

    private final static String contentDataRegExp = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
    private final static String runTimeRegExp = "^[0-9]\\d*$";
    private final static String functionRegExp = "\\d{1,4}[:][g][e][t]w*";
    private final static String percentageRegExp = "\\d{1,3}[%]$";
    private final static String timeRegExp = "\\d{2}[:]\\d{2}[:]\\d{2}";
    private final static String taskRegExp = "^[g][e][t]"; //匹配行的开始处
    private final static String startWithDatRegExp = "^\\d{4}[-]\\d{2}[-]\\d{2}";
    private final static String dataRegExp = "^\\d{4}[-]\\d{2}[-]\\d{2}$";//纯日期格式
    private final static String dateRegExp = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";//任务年月格式

    private final static String errorCodeExp = "^[1][0]\\d{2}[-]\\d";
    List<String> dateTask = new LinkedList<String>();

    /**
     * 识别主表与从表所需的string
     *
     * @param tempString 传入的string
     * @return
     */
    public boolean identifyTempString(String tempString) {

        if (tempString.indexOf("running time (millis) =") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean recognizeTaskDate(String str) {
        Pattern pat = Pattern.compile(dataRegExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            return true;
        }
        return false;
    }
    public boolean recognizeStartWithErrorCode(String str){
        Pattern pat = Pattern.compile(errorCodeExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            System.out.println("错误码开头: " + str);
            return true;
        }
        return false;
    }

    /**
     * 解析是否是以日期开头的字符串
     *
     * @param str
     * @return
     */
    public boolean recognizeStartWithData(String str) {
        Pattern pat = Pattern.compile(startWithDatRegExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            System.out.println("日期开头的字符串: " + str);
            return true;
        }
        return false;
    }

    public boolean recognizeDate(String str) {
        Pattern pat = Pattern.compile(dataRegExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            System.out.println("日期: " + str);
            return true;
        }
        return false;
    }

    /**
     * 解析是否包含日期
     *
     * @param str
     * @return
     */
    public boolean recognizeContentDate(String str) {
        Pattern pat = Pattern.compile(contentDataRegExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            System.out.println("datawww: " + str);
            return true;
        }
        return false;
    }

    /**
     * 解析运行结束时间 HH:MM:SS
     *
     * @param str
     * @return
     */
    public boolean recognizeEndTime(String str) {
        Pattern pat = Pattern.compile(timeRegExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            System.out.println("endTime: " + str);
            return true;
        }
        return false;
    }

    /**
     * 解析运行所需时间
     *
     * @param str
     * @return
     */
    public boolean recognizeRunTime(String str) {
        Pattern pat = Pattern.compile(runTimeRegExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            System.out.println("runtime: " + str.toString());
            return true;
        }
        return false;
    }

    /**
     * 解析运行时间所占百分比
     *
     * @param str
     * @return
     */
    public boolean recognizePercentage(String str) {
        Pattern pat = Pattern.compile(percentageRegExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            System.out.println("percentatge: " + str);

            return true;
        }
        return false;
    }

    /**
     * 解析运行函数所在行窜
     *
     * @param str
     * @return
     */
    public boolean recognizeGetFunction(String str) {
        Pattern pat = Pattern.compile(functionRegExp);
        Matcher mat = pat.matcher(str);

        boolean rs = mat.find();
        if (rs) {
            System.out.println("funtion: " + str);
            return true;
        }
        return false;
    }

    /**
     * 解析任务名称
     *
     * @param str
     * @return
     */
    public boolean recognizeTask(String str) {
        Pattern pat = Pattern.compile(taskRegExp);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        if (rs) {
            System.out.println("funtion: " + str);
            return true;
        }
        return false;
    }

    public List<String> taskTime(String startTime,int taskNumber) {
        if(taskNumber >0) {
            int lastInt  = startTime.charAt(startTime.length() - 1) -'0';
            int last2Int = startTime.charAt(startTime.length() - 2) - '0';
            int last3Int = startTime.charAt(startTime.length() - 4) - '0';
            int last4Int = startTime.charAt(startTime.length() - 5) - '0';
            int last5Int = startTime.charAt(startTime.length() - 7) - '0';
            int last6Int = startTime.charAt(startTime.length() - 8) - '0';
            int last7Int = startTime.charAt(startTime.length() - 9) - '0';
            int last8Int = startTime.charAt(startTime.length() - 10) - '0';
            int month = last4Int * 10 + last3Int;
            int day = last2Int * 10 + lastInt ;
            int year = last8Int*1000+last7Int*100+last6Int * 10 + last5Int;
            String date = getDate(month, day, year);
            dateTask.add(date);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(year+"-"+month+"-");
            day++;
            if(day<10){
                stringBuilder.append("0"+day);
            }
            taskTime(stringBuilder.toString(), taskNumber - 1);
        }
            return dateTask;
    }

    private String getDate(int month, int day,int year) {
        StringBuilder strbuilder = new StringBuilder();
        if(0 == (year%4)) {
            if(2 == month && day == 29 ){
                month++;
                day = 1;
                strbuilder.append(year+"-"+month+"-"+day);
                return strbuilder.toString();
            }
        }
        if(2 == month && day == 28 ){
            month++;
            day = 1;
            strbuilder.append(year+"-"+month+"-"+day);
            return strbuilder.toString();
        }
        if(4 == month || 6 == month || 9 == month || 11 == month){
            if(30 == day){
                month++;
                day = 1;
                strbuilder.append(year+"-"+month+"-"+day);
                return strbuilder.toString();
            }
        }
        if(31 == day){
                month++;
                day = 1;
                strbuilder.append(year+"-"+month+"-"+day);
                return strbuilder.toString();
        }
        strbuilder.append(year+"-"+month+"-");
        if(day<=9){
            strbuilder.append("0"+day);
        }else {
            strbuilder.append(day);
        }
        return strbuilder.toString();
    }
}


