package jettylog.bean;

import org.nutz.dao.entity.annotation.*;

/**
 * User:huangtao
 * Date:2015-09-22
 * description：
 */
@Table("monitors_detail")
public class MonitorsDetail {
    @Id
    private int id;
    @Column("run_date")
    @ColDefine(type= ColType.VARCHAR, width=16)
    private String runDate;
    @Column("end_time")
    @ColDefine(type= ColType.VARCHAR, width=16)
    private String endTime;
    @Column("pro_time")
    private int proTime;
    @Column("opt_time")
    @ColDefine(type= ColType.VARCHAR, width=32)
    private String optTime;
    @Column("method")
    @ColDefine(type= ColType.VARCHAR, width=64)
    private String method;
    @Column("request_path")
    @ColDefine(type= ColType.VARCHAR, width=128)
    private String requestPath;
    @Column("error_code")
    @ColDefine(type= ColType.VARCHAR, width=16)
    private String errorCode;
    @Column("host")
    @ColDefine(type= ColType.VARCHAR, width=128)
    private String host;
    @Column("logger_marker")
    @ColDefine(type= ColType.VARCHAR, width=32)
    private String loggerMarker;
    @ColDefine(type= ColType.VARCHAR, width=32)
    @Column("monitor_name")
    private String monitorName;


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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getProTime() {
        return proTime;
    }

    public void setProTime(int proTime) {
        this.proTime = proTime;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLoggerMarker() {
        return loggerMarker;
    }

    public void setLoggerMarker(String loggerMarker) {
        this.loggerMarker = loggerMarker;
    }
}
