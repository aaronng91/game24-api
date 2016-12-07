package game24;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getArrayOf4CardsFromCardsEndpoint() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andReturn();

        MvcResult resultAgain = mvc.perform(MockMvcRequestBuilders.get("/cards"))
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(),
                resultAgain.getResponse().getContentAsString());
    }
}
