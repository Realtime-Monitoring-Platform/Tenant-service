package com.realtime_monitorig.tenant_managment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TenantMangApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantMangApplication.class, args);
	}

}
