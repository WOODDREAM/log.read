package jettylog;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;

/**
 * User:huangtao
 * Date:2015-09-17
 * description：
 */
public class QuartzTaskTest extends BaseControllerTest {
    @Before
    public void init(){
        this.mockMvc = MockMvcBuilders.standaloneSetup().build();
    }
    @Test
    public void testTask() throws ParseException {
//        scheduleTask.executeTask();
    }
    @Test
    public void testQuartzTask(){
        quartzTask.execute();
    }
}
