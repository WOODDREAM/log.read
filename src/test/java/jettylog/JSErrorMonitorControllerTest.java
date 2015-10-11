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
public class JSErrorMonitorControllerTest extends BaseControllerTest {
    @Before
    public void init() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(jsErrorMonitorController);
        this.mockMvc = standaloneMockMvcBuilder.build();
    }

    @Test
    public void testStartLogTask() throws Exception {
        mockMvc.perform(get("/read/log/js/error")).andReturn().getResponse();
    }

    @Test
    public void teatBatchTask() throws Exception {
        mockMvc.perform(get("/read/log/batch/9/2015-10-01"))
                .andReturn().getResponse();
    }
}
