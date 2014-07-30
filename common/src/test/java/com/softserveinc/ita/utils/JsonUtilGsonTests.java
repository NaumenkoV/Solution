package com.softserveinc.ita.utils;

import com.softserveinc.ita.BaseTest;
import com.softserveinc.ita.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;

@ContextConfiguration("classpath:spring-gson-test-config.xml")
public class JsonUtilGsonTests extends BaseTest {

	@Autowired
	private JsonUtil jsonUtil;

	@Test
	public void testJsonUtilFromJsonShouldRerurnValidObject() throws Exception {
		String expectedName = "test";
		User user = jsonUtil.fromJson("{name: " + expectedName + "}", User.class);
		assertEquals(expectedName, user.getName() );
	}

	@Test
	public void testJsonUtiltoJsonShouldRerurnValidJson() throws Exception {
		String expectedName = "test";
		User user = new User(expectedName);
		String actualJson = jsonUtil.toJson(user);
		assertEquals("{\"name\":\"" + expectedName + "\"}", actualJson);
	}

}
