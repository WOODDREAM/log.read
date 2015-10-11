package jettylog;

import jettylog.controller.JSErrorMonitorController;
import jettylog.controller.MonitorsController;
import jettylog.controller.WatchOrderPayMonitorController;
import jettylog.file.ReadFile;
import jettylog.task.QuartzTask;
import jettylog.utils.HttpUtil;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * User:huangtao
 * Date:2015-09-15
 * description：
 */
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseControllerTest {
    protected MockMvc mockMvc;
    @Autowired
    WatchOrderPayMonitorController watchOrderPayMonitorController;
    @Autowired
    MonitorsController monitorsController;
    @Autowired
    JSErrorMonitorController jsErrorMonitorController;
    @Autowired
    HttpUtil httpUtil;
    @Autowired
    QuartzTask quartzTask;
    @Autowired
    ReadFile readFile;

}
