package csu.lch.violetapiinterface;

import csu.lch.violetapiclientsdk.client.VioletAPIClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class VioletApiInterfaceApplicationTests {

	@Resource
	private VioletAPIClient violetAPIClient;
	@Test
	void contextLoads() {
		String result = violetAPIClient.getNameByGet("fangyishabi");
	}
}
