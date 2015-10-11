package jettylog.bean;


import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

/**
 * User:huangtao
 * Date:2015-09-16
 * description：
 */
@Table("watch_order_pay_daily_monitor")
@PK({"monitorName","runDate"})
public class WatchOrderPayDailyMonitor {
    @Id
    private int id;
    @Column("monitor_name")
    private String monitorName;
    @Column("run_date")

    private String runDate;
    @Column("monitor_count")
    private int monitorCount;
    @Column("succeed_percent")
    private String succeedPercent;
    @Column("failed_percent")
    private String failedPercent;
    @Column("succeed_average_time")
    private int succeedAverageTime;
    @Column("failed_average_time")
    private int failedAverageTime;

    @Column("first_failed_average_time")
    private int firtFailedAverageTime;
    @Column("second_failed_average_time")
    private int secondFailedAverageTime;

    @Column("first_failed_percent")
    private String firstFailedPercent;
    @Column("second_failed_percent")
    private String secondFailedPercent;

    @Column("succeed_time")
    private int succeedTime;
    @Column("first_failed_count")
    private int firstFailedCount;
    @Column("first_failed_time")
    private int firstFailedTime;
    @Column("second_failed_count")
    private int secondFailedCount;
    @Column("second_failed_time")
    private int secondFailedTime;
//    @Many(target = Monitor.class,field = "dailyMonitorId")
//    private List<Monitor> monitorList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public int getMonitorCount() {
        return monitorCount;
    }

    public void setMonitorCount(int monitorCount) {
        this.monitorCount = monitorCount;
    }

    public String getSucceedPercent() {
        return succeedPercent;
    }

    public void setSucceedPercent(String succeedPercent) {
        this.succeedPercent = succeedPercent;
    }

    public String getFailedPercent() {
        return failedPercent;
    }

    public void setFailedPercent(String failedPercent) {
        this.failedPercent = failedPercent;
    }

    public int getSucceedAverageTime() {
        return succeedAverageTime;
    }

    public void setSucceedAverageTime(int succeedAverageTime) {
        this.succeedAverageTime = succeedAverageTime;
    }

    public int getFailedAverageTime() {
        return failedAverageTime;
    }

    public void setFailedAverageTime(int failedAverageTime) {
        this.failedAverageTime = failedAverageTime;
    }

    public int getFirtFailedAverageTime() {
        return firtFailedAverageTime;
    }

    public void setFirtFailedAverageTime(int firtFailedAverageTime) {
        this.firtFailedAverageTime = firtFailedAverageTime;
    }

    public int getSecondFailedAverageTime() {
        return secondFailedAverageTime;
    }

    public void setSecondFailedAverageTime(int secondFailedAverageTime) {
        this.secondFailedAverageTime = secondFailedAverageTime;
    }

    public String getFirstFailedPercent() {
        return firstFailedPercent;
    }

    public void setFirstFailedPercent(String firstFailedPercent) {
        this.firstFailedPercent = firstFailedPercent;
    }

    public String getSecondFailedPercent() {
        return secondFailedPercent;
    }

    public void setSecondFailedPercent(String secondFailedPercent) {
        this.secondFailedPercent = secondFailedPercent;
    }

    public int getSucceedTime() {
        return succeedTime;
    }

    public void setSucceedTime(int succeedTime) {
        this.succeedTime = succeedTime;
    }

    public int getFirstFailedCount() {
        return firstFailedCount;
    }

    public void setFirstFailedCount(int firstFailedCount) {
        this.firstFailedCount = firstFailedCount;
    }

    public int getFirstFailedTime() {
        return firstFailedTime;
    }

    public void setFirstFailedTime(int firstFailedTime) {
        this.firstFailedTime = firstFailedTime;
    }

    public int getSecondFailedCount() {
        return secondFailedCount;
    }

    public void setSecondFailedCount(int secondFailedCount) {
        this.secondFailedCount = secondFailedCount;
    }

    public int getSecondFailedTime() {
        return secondFailedTime;
    }

    public void setSecondFailedTime(int secondFailedTime) {
        this.secondFailedTime = secondFailedTime;
    }

    //    public List<Monitor> getMonitorList() {
//        return monitorList;
//    }
//
//    public void setMonitorList(List<Monitor> monitorList) {
//        this.monitorList = monitorList;
//    }
}
