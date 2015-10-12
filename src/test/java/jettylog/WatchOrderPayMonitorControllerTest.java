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
public class WatchOrderPayMonitorControllerTest extends BaseControllerTest {
    @Before
    public void init(){
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(watchOrderPayMonitorController);
        this.mockMvc = standaloneMockMvcBuilder.build();
    }
    @Test
    public void testStartLogTask() throws Exception {
        mockMvc.perform(get("/read/log/watch/order/pay")).andReturn().getResponse();
    }

    @Test
    public void teatBatchTask() throws Exception {
        mockMvc.perform(get("/read/log/watch/order/pay/30/2015-09-10"))
                .andReturn().getResponse();
    }
}
