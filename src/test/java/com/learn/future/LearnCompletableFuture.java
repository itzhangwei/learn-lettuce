package com.learn.future;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zack.zhang
 * @projectName learn-lettuce
 * @title LearnCompletableFuture
 * @package com.learn.future
 * @description 学习异步api
 * @date 2019/9/16 3:55 下午
 */
public class LearnCompletableFuture {
	
	@Test
	public void fun1() throws ExecutionException, InterruptedException {
		HashMap<String, String> map = new HashMap<>();
		
		long start = System.nanoTime();
		CompletableFuture<HashMap<String, String>> hashMapCompletableFuture = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(30);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "a";
		}).thenCombineAsync(
				CompletableFuture.supplyAsync(() -> {
					try {
						TimeUnit.SECONDS.sleep(30);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					int i = 1 / 0;
					return "b";
				}), (a, b) -> {
					map.put(a, a);
					map.put(b, b);
					return map;
				});
		System.out.println((System.nanoTime() - start) / 1000000000);
		HashMap<String, String> map2 = hashMapCompletableFuture.get();
		System.out.println((System.nanoTime() - start) / 1000000000);
		System.out.println(map2);
		
	}
	
	@Test
	public void fun2() throws ExecutionException, InterruptedException {
		CompletableFuture fut = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
				throw new RuntimeException("自定义异常");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		});
		TimeUnit.SECONDS.sleep(10);
		fut.get();
		System.out.println(123);
	}
	
	@Test
	public void fun3() {
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		System.out.println((1111/1000 + (1111%1000==0?0:1)));
		List<String> list = new ArrayList<>();
		for (int i = 0; i <=100 ; i++) {
			list.add(i + "");
		}
		
		// 并行流，保证顺序输出
		list.parallelStream().forEachOrdered(s-> System.out.println(s));
		
		// 无法保证顺序
//		list.parallelStream().forEach(s-> System.out.println(s));
	}
	
}
