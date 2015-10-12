package jettylog.bean;

import org.nutz.dao.entity.annotation.*;

/**
 * User:huangtao
 * Date:2015-09-29
 * description：
 */
@Table("js_error_monitor")
public class JsErrorMonitor {
    @Id
    private int id;
    @Column("run_date")
    @ColDefine(type= ColType.VARCHAR, width=16)
    private String runDate;
    @Column("time")
    @ColDefine(type= ColType.VARCHAR, width=32)
    private String time;
    @Column("navigator")
    @ColDefine(type= ColType.VARCHAR, width=2014)
    private String navigator;
//    @Column("connection")
//    private String connection;
//    @Column("vendor")
//    private String vendor;
//    @Column("appCodeName")
//    private String appCodeName;
//    @Column("appVersion")
//    private String appVersion;
//    @Column("mimeTypes")
//    private String mimeTypes;
//    @Column("productSub")
//    private String productSub;
//    private String appName;
//    private String geolocation;
//    private String onLine;
//    private String userAgent;
//    private String webkitBattery;
    @Column("error_message")
    @ColDefine(type= ColType.VARCHAR, width=512)
    private String errorMessage;
    @Column("script_uri")
    @ColDefine(type= ColType.VARCHAR, width=512)
    private String scripturi;
    @Column("line_number")
    @ColDefine(type= ColType.VARCHAR, width=8)
    private String lineNumber;
    @Column("column_number")
    @ColDefine(type= ColType.VARCHAR, width=8)
    private String columnNumber;
    @Column("error_obj")
    @ColDefine(type= ColType.VARCHAR, width=1024)
    private String errorObj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNavigator() {
        return navigator;
    }

    public void setNavigator(String navigator) {
        this.navigator = navigator;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getScripturi() {
        return scripturi;
    }

    public void setScripturi(String scripturi) {
        this.scripturi = scripturi;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getErrorObj() {
        return errorObj;
    }

    public void setErrorObj(String errorObj) {
        this.errorObj = errorObj;
    }

    public String getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(String columnNumber) {
        this.columnNumber = columnNumber;
    }
}
