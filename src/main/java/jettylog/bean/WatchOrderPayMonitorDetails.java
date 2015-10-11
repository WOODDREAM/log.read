package jettylog.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.springframework.stereotype.Component;

import java.sql.Time;

/**
 * User:huangtao
 * Date:2015-09-11
 * description：
 */
@Component
@Table("watch_order_pay_monitor_details")
//@TableIndexes({@Index(name="idx_monitor_record_monitor_id",fields={"monitor_id"},unique=false)})
public class WatchOrderPayMonitorDetails {
    @Id
    private int id;
    @Column("task_name")
    private String taskName;
    @Column("run_time")
    private int runTime;
    @Column("time_percent")
    private String timePercent;
    @Column("task_id")
    private String taskId;
    @Column("end_time")
    private Time endTime;
    @Column("run_date")
    private String runDate;

    @Column("monitor_id")
    private int monitorId;
//    @One(target = Monitor.class,field = "id")
//    private Monitor monitor;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTimePercent() {
        return timePercent;
    }

    public void setTimePercent(String timePercent) {
        this.timePercent = timePercent;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }
}
