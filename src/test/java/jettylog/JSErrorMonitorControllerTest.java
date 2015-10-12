package jettylog;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        mockMvc.perform(get("/read/log/js/error/read/file")).andReturn().getResponse();
    }

    @Test
    public void testBatchTask() throws Exception {
        mockMvc.perform(get("/read/log/js/error/batch/21/2015-09-10"))
                .andReturn().getResponse();
    }
    @Test
    public void testQuery() throws Exception{
        mockMvc.perform(post("/read/log/js/error/query").param("date", "2015-09-30").param("page_number","1").param("page_size","10"))
                .andReturn().getResponse().getContentAsString();
    }
}
