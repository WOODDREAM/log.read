package jettylog;

import org.nutz.dao.entity.annotation.Comment;

/**
 * User:huangtao
 * Date:2015-09-18
 * description：
 */
@Comment
public class Constants {

   public class File{


       public static final String WATCH_ORDER_PAY_MONITOR_PATH =  "F:\\monitor\\watch_order_pay_monitor.log.";

       public static final String WATCH_ORDER_PAY_MONITOR_URL = "http://viewlog.2dfire.com/down.php?file=/var/log/jetty/logs/watch_order_pay_monitor.log%s&ip=10.161.129.69&hostname=weixin-meal_publish_10-161-129-69";

       public static final String STOP_WATCH_MONITOR_URL = "http://viewlog.2dfire.com/down.php?file=/var/log/jetty/logs/stopwatch_monitor.log%s&ip=10.161.129.69&hostname=weixin-meal_publish_10-161-129-69";

       public static final String STOP_WATCH_MONITOR_PATH = "F:\\monitor\\stopwatch_monitor.log.";

       public static final String WATCH_MODIFY_CART_MONITOR_URL = "http://viewlog.2dfire.com/down.php?file=/var/log/jetty/logs/watch_modify_cart_monitor.log%s&ip=10.161.129.69&hostname=weixin-meal_publish_10-161-129-69";

       public static final String WATCH_MODIFY_CART_MONITOR_PATH = "F:\\monitor\\watch_modify_cart_monitor.log.";

       public static final String SERVICE_MONITOR_URL = "http://viewlog.2dfire.com/down.php?file=/var/log/jetty/logs/service_monitor.log%s&ip=10.161.129.69&hostname=weixin-meal_publish_10-161-129-69";

       public static final String SERVICE_MONITOR_PATH = "F:\\monitor\\service_monitor.log.";

       public static final String JS_ERROR_MONITOR_URL = "http://viewlog.2dfire.com/down.php?file=/var/log/jetty/logs/js_error_monitor.log%s&ip=10.175.201.239&amp;hostname=weixin-meal_publish_10-175-201-239";

       public static final String JS_ERROR_MONITOR_PATH = "F:\\monitor\\js_error_monitor.log.";

       public static final String SYS_MONITOR_URL = "http://viewlog.2dfire.com/down.php?file=/var/log/jetty/logs/sys_monitor.log%s&ip=10.175.201.239&amp;hostname=weixin-meal_publish_10-175-201-239";

       public static final String SYS_MONITOR_PATH = "F:\\monitor\\sys_monitor.log.";

       public static final String OAUTH_MONITOR_URL = "http://viewlog.2dfire.com/down.php?file=/var/log/jetty/logs/oauth_monitor.log%s&ip=10.175.201.239&amp;hostname=weixin-meal_publish_10-175-201-239";

       public static final String OAUTH_MONITOR_PATH = "F:\\monitor\\oauth_monitor.log.";

       public static final String PAYMENT_MONITOR_URL = "http://viewlog.2dfire.com/down.php?file=/var/log/jetty/logs/payment_monitor.log%s&ip=10.175.201.239&amp;hostname=weixin-meal_publish_10-175-201-239";

       public static final String PAYMENT_MONITOR_PATH = "F:\\monitor\\payment_monitor.log.";


       public static final String PATH = "F:\\monitor\\";

   }
    public class Task{
        public static final String STARTTIME = "23:57:00";

        public static final String START_DATE = "2015-10";

        public static final int TASK_NUMBER = 8;

        public static final String STOP_WATCH_MONITOR = "stopwatch_monitor";

        public static final String WATCH_ORDER_PAY_MONITOR = "watch_order_pay_monitor";

        public static final String SERVICE_MONITOR = "service_monitor";

        public static final String WATCH_MODIFY_CART_MONITOR = "watch_modify_cart_monitor";

        public static final String JS_ERROR_MONITOR = "js_error_monitor";

        public static final String SYS_MONITOR = "sys_monitor";

        public static final String OAUTH_MONITOR = "oauth_monitor";

        public static final String PAYMENT_MONITOR = "payment_monitor";

        public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    }
}
