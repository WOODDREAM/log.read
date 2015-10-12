package jettylog;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * User:huangtao
 * Date:2015-10-10
 * description：
 */
public class MonitorsControllerTest extends BaseControllerTest {
    @Before
    public void init(){
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(monitorsController);
        this.mockMvc = standaloneMockMvcBuilder.build();
    }
    @Test
    public void testStartLogTask() throws Exception {
        mockMvc.perform(get("/read/log/"+Constants.Task.SERVICE_MONITOR)).andReturn().getResponse();
        mockMvc.perform(get("/read/log/"+Constants.Task.WATCH_MODIFY_CART_MONITOR)).andReturn().getResponse();
        mockMvc.perform(get("/read/log/"+Constants.Task.STOP_WATCH_MONITOR)).andReturn().getResponse();
        mockMvc.perform(get("/read/log/"+Constants.Task.OAUTH_MONITOR)).andReturn().getResponse();
        mockMvc.perform(get("/read/log/"+Constants.Task.PAYMENT_MONITOR)).andReturn().getResponse();
        mockMvc.perform(get("/read/log/"+Constants.Task.SYS_MONITOR)).andReturn().getResponse();
    }

    @Test
    public void teatBatchTask() throws Exception {
        mockMvc.perform(get("/read/log/batch/"+Constants.Task.SERVICE_MONITOR+"/31/2015-09-10")).andReturn().getResponse();
        mockMvc.perform(get("/read/log/batch/"+Constants.Task.WATCH_MODIFY_CART_MONITOR+"/31/2015-09-10")).andReturn().getResponse();
        mockMvc.perform(get("/read/log/batch/"+Constants.Task.STARTTIME+"/31/2015-09-10")).andReturn().getResponse();
        mockMvc.perform(get("/read/log/batch/"+Constants.Task.OAUTH_MONITOR+"/31/2015-09-10")).andReturn().getResponse();
        mockMvc.perform(get("/read/log/batch/"+Constants.Task.PAYMENT_MONITOR+"/31/2015-09-10")).andReturn().getResponse();
        mockMvc.perform(get("/read/log/batch/"+Constants.Task.SYS_MONITOR+"/31/2015-09-10")).andReturn().getResponse();
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(jsErrorMonitorController);
        this.mockMvc = standaloneMockMvcBuilder.build();
        mockMvc.perform(get("/read/log/batch/21/2015-09-10"))
                .andReturn().getResponse();
    }
}
