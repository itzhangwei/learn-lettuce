package com.learn.lettuce;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnLettuceApplicationTests {

	@Test
	public void contextLoads() {
		Personal personal = new Personal();
		personal.setName("张三");
		HashMap<Personal, String> map = new HashMap<>();
		map.put(personal,"张三");
		System.out.println(map);
		
		System.out.println("hashcode--->"+ personal.hashCode());
		personal.setName("李四");
		String s = map.get(personal);
		System.out.println(s);
		System.out.println("hashcode=======>"+ personal.hashCode());
		
	}

	class Personal {
		String name;
		public void setName(String name) {
			this.name=name;
		}
		
		public String getName() {
			return this.name;
		}
	}
}
