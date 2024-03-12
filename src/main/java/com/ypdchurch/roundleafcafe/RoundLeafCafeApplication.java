package com.ypdchurch.roundleafcafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
class RoundLeafCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoundLeafCafeApplication.class, args);
	}

}
