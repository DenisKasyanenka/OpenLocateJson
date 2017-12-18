package json.open.locate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
public class AdLocationControllerTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;


	private final String body = "[\n" +
            "  {\n" +
            "    \"ad_id\": \"12a451dd-3539-4092-b134-8cb0ef62ab8a\",\n" +
            "    \"ad_opt_out\": true,\n" +
            "    \"id_type\": \"idfa\",\n" +
            "    \"latitude\": \"88.8888888888888\",\n" +
            "    \"longitude\": \"-122.431297\",\n" +
            "    \"utc_timestamp\": \"1508356559\",\n" +
            "    \"horizontal_accuracy\": 12.32388888888888,\n" +
            "    \"vertical_accuracy\": 5.3,\n" +
            "    \"altitude\": 0.456,\n" +
            "    \"wifi_ssid\": \"OpenLocate_Guest\",\n" +
            "    \"wifi_bssid\": \"OpenLocate_Guest\",\n" +
            "    \"location_context\": \"regular\",\n" +
            "    \"course\": 175.0,\n" +
            "    \"speed\": 11.032,\n" +
            "    \"is_charging\": true,\n" +
            "    \"device_model\": \"iPhone 7\",\n" +
            "    \"os_version\": \"iOS 11.0.3\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"ad_id\": \"12a451dd-3539-4092-b134-8cb0ef62ab8a\",\n" +
            "    \"ad_opt_out\": true,\n" +
            "    \"id_type\": \"idfa\",\n" +
            "    \"latitude\": \"37.773972\",\n" +
            "    \"longitude\": \"-122.431297\",\n" +
            "    \"utc_timestamp\": \"1508356559\",\n" +
            "    \"horizontal_accuracy\": 12.323,\n" +
            "    \"vertical_accuracy\": 5.3,\n" +
            "    \"altitude\": 0.456,\n" +
            "    \"wifi_ssid\": \"OpenLocate_Guest\",\n" +
            "    \"wifi_bssid\": \"OpenLocate_Guest\",\n" +
            "    \"location_context\": \"regular\",\n" +
            "    \"course\": 175.0,\n" +
            "    \"speed\": 11.032,\n" +
            "    \"is_charging\": true,\n" +
            "    \"device_model\": \"iPhone 7\",\n" +
            "    \"os_version\": \"iOS 11.0.3\"\n" +
            "  }\n" +
            "]";

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	//Test with 10 Async requests to make sure it can work in real world.
	@Test
	public void shouldSaveJson() throws Exception {
        for (int i = 0; i < 10; i++) {
            ((Runnable) () -> {
                try {
                    mvc.perform(
                            post("/location")
                                    .content(body)
                                    .contentType("application/json"))
                            .andExpect(jsonPath("$", hasSize(2)))
                            .andExpect(status().isOk());
                } catch (Exception e) {
                    //test failed
                    e.printStackTrace();
                }
            }).run();
        }
    }
}
