package jettylog.vo;

import java.io.Serializable;

/**
 * User:huangtao
 * Date:2015-10-12
 * description：
 */
public class JsErrormonitorForm implements Serializable {
    private int id;
    private String runDate;
    private String time;
    private String navigator;
    private String errorMessage;
    private String scripturi;
    private String lineNumber;
    private String columnNumber;
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

    public String getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(String columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getErrorObj() {
        return errorObj;
    }

    public void setErrorObj(String errorObj) {
        this.errorObj = errorObj;
    }
}
