package com.realtime_monitorig.tenant_managment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {"grpc.server.enabled=false"})
@ActiveProfiles("test")
class TenantMangApplicationTests {

	@Test
	void contextLoads() {
	}

}
