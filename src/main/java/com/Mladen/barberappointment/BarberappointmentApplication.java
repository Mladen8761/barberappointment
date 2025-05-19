package com.Mladen.barberappointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BarberappointmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarberappointmentApplication.class, args);
	}

}
