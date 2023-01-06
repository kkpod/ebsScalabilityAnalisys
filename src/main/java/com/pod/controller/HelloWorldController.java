package com.pod.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pod.property.Car;
import com.pod.property.GetTimestamp;

@RestController
public class HelloWorldController {
	public static int count;

	public static boolean degradePerformance;
	
	@RequestMapping("/status")
	public String status() {
		return "Applicaiton deployed on AWS beanstalk";
	}

	@RequestMapping("/perf")
	public Boolean performance(@RequestParam boolean performance) {
		degradePerformance = !performance;
		return degradePerformance;
	}
	
	@RequestMapping("/")
	public List<Car> hello() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		java.util.Date date = new java.util.Date();
		String latest_timestamp = date.toString();

		List<Car> cars = new ArrayList<Car>();

//		String s = "TestTestTest";
//		for (long j=0; j<=10000000000000l; j++) {
//			s = s + j;
//		}

		if (degradePerformance) {
			String s = "TestTestTest";
			for (long j=0; j<=30000l; j++) {
				s = s + j;
			}
		}
		
		for (int i = 1; i <= 10; i++) {
			Car car = new Car();
			count = count + 1;
			car.setId(count);
			car.setName("NameOfCar" + count);
			cars.add(car);
		}

//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		stopWatch.stop();
		System.out.println("Elapsed Time in minutes: " + stopWatch.getLastTaskTimeMillis());

		return cars;
	}

	@RequestMapping("/hello")
	public String helloTimestamp() {
		System.out.println("");
		GetTimestamp t = new GetTimestamp();
		String prev_timestamp = t.read();
		System.out.println("prev timestamp-----> " + prev_timestamp);

		// System.out.println("---------------------------------------------REading
		// Again");

		java.util.Date date = new java.util.Date();
		String latest_timestamp = date.toString();
		t.write(latest_timestamp);

		String run_env;
		run_env = System.getenv("RUN_ENV");

		String output;
		output = "<center><H1>Greetings for the day</H1>";
		output = output + "<h2 style='color:green;'>version : 3.0</h2>";
		output = output + "<h2 style='color:red;'> Previous Timestamp :" + prev_timestamp + "</h2>";
		output = output + "<h2 style='color:blue;'> Latest Timestamp :" + latest_timestamp + "</h2>";
		if (run_env != null)
			output = output + "<h2 style='color:#f90e4e;'> Run Env :" + run_env + "</h2>";
		output = output + "";
		output = output + "</center>";

		return output;
	}

	public void readTestFile() {
		try {
			File myObj = new File("D:\\projSpring\\workspaceSB\\spring-boot-hello-world-master\\src\\main\\resources\\test.jar");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
//				System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}
