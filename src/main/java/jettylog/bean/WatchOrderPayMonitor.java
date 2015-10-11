package jettylog.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User:huangtao
 * Date:2015-09-11
 * description：
 */
@Component
@Table("watch_order_pay_monitor")
public class WatchOrderPayMonitor {

    @Id
    private int id;
    @Column("end_time")
    private String endTime;
    @Column("task_name")
    private String taskName;
    @Column("run_time")
    private int runTime;
    @Column("run_date")
    private String runDate;
    @Column("child_number")
    private int childNumber;

    @Column("average_time")
    private int averageTime;
    @Column("monitor_name")
    private String monitorName;
    @Many(target = WatchOrderPayMonitorDetails.class,field = "monitorId")
    private List<WatchOrderPayMonitorDetails> monitorDetailsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public int getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(int childNumber) {
        this.childNumber = childNumber;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public List<WatchOrderPayMonitorDetails> getMonitorDetailsList() {
        return monitorDetailsList;
    }

    public void setMonitorDetailsList(List<WatchOrderPayMonitorDetails> monitorDetailsList) {
        this.monitorDetailsList = monitorDetailsList;
    }
}
