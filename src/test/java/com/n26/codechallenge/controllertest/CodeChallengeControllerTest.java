package com.n26.codechallenge.controllertest;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.n26.codechallenge.N26CodeChallengeApplication;
import com.n26.codechallenge.model.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = N26CodeChallengeApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodeChallengeControllerTest {

	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private WebApplicationContext webApplicationContext;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Test
	@Ignore
	public void addTransactionTest() throws Exception {
		
		for (int i : new ArrayList<Integer>(Arrays.asList(1,2,3))) {
			Transaction transaction = new Transaction();
			transaction.setAmount(12.34 + i);
			transaction.setTimestamp(Instant.now().toEpochMilli());

			mockMvc.perform(
					post("http://localhost:8082/transactions").content(this.json(transaction)).contentType(contentType))
					.andExpect(status().isCreated());
		}

	}

	@Test
	@Ignore
	public void addTransactionFailTest() throws Exception {
		
		for (int i : new ArrayList<Integer>(Arrays.asList(1))) {
			
			Instant t1 = Instant.now();
		    long minutes = 2;
		    Instant t2 = t1.minus(minutes, ChronoUnit.MINUTES);
		    
		    Transaction transaction = new Transaction();
			transaction.setAmount(12.34 + i);
			transaction.setTimestamp(t2.toEpochMilli());
			
			mockMvc.perform(
					post("http://localhost:8082/transactions").content(this.json(transaction)).contentType(contentType))
					.andExpect(status().isNoContent());
		}

	}
	
	@Test
	public void getStatisticsTest() throws Exception {
		mockMvc.perform(get("http://localhost:8082/statistics"))
		.andExpect(status().isOk());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
