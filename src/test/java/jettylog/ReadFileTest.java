package jettylog;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * User:huangtao
 * Date:2015-09-15
 * description：
 */
public class ReadFileTest extends BaseControllerTest {



    @Test
    public void testReadFile() throws Exception {
        String s1 = "F:\\watch_order_pay_monitor.log.2015-09-10";
        String s2 = "F:\\watch_order_pay_monitor.log.2015-09-11";
        String s3 = "F:\\watch_order_pay_monitor.log.2015-09-12";
        String s4 = "F:\\watch_order_pay_monitor.log.2015-09-13";
        String s5 = "F:\\watch_order_pay_monitor.log.2015-09-14";
        String s6 = "F:\\watch_order_pay_monitor.log.2015-09-15";
        List<String> stringList = new LinkedList<String>();
//        stringList.add(s);
        stringList.add(s1);
        stringList.add(s2);
        stringList.add(s3);
        stringList.add(s4);
        stringList.add(s5);
        stringList.add(s6);
//
        for(String fileAddress : stringList){
            readFile.readMonitorFile(fileAddress);
            readFile.readFileByLine(fileAddress);
        }
    }

}
