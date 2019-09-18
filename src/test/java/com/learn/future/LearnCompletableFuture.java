package com.learn.future;

import org.junit.Test;

import java.util.HashMap;
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
					int i =1/0;
					return "b";
				}), (a, b) -> {
					map.put(a, a);
					map.put(b, b);
					return map;
				});
		System.out.println((System.nanoTime() -start) /1000000000);
		HashMap<String, String> map2 = hashMapCompletableFuture.get();
		System.out.println((System.nanoTime() -start) /1000000000);
		System.out.println(map2);
		
	}
}
