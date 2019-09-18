package com.learn.lettuce;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zack.zhang
 * @projectName learn-lettuce
 * @title ApiTest
 * @package com.learn.lettuce
 * @description lettuce的api使用
 * @date 2019/9/11 3:04 下午
 */
public class ApiTest {
	
	
	@Test
	public void testFun(){
		// 通过redis连接穿件默认端口6379的 redis客户端
		RedisClient redisClient = RedisClient.create("redis://106.13.162.44");
		
		// 打开redis连接
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		
		// 获取执行同步命令的api
		RedisCommands<String, String> sync = connect.sync();
		
		// 设置数据
		String set = sync.set("name", "joka");
		System.out.println(set);
		
		//完成后关闭连接。这通常发生在您的应用程序的最后。连接设计为长寿命
		connect.close();
		
		//关闭客户端实例以释放线程和资源。这通常发生在您的应用程序的最后。
		redisClient.shutdown();
	}
	
	@Test
	public void testFunGet() throws ExecutionException, InterruptedException {
		// 创建客户端
		RedisClient redisClient = RedisClient.create("redis://106.13.162.44");
		
		// 连接redis
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		
		// 获取异步api
		RedisAsyncCommands<String, String> async = connect.async();
		
		RedisFuture<String> name = async.get("name");
		
		
		// 又叫侦听器，调用获取异步的方法交给未来完成的时候调用
		// 异步执行完成时候执行
		name.thenRun(()->{
			try {
				String s = name.get();
				System.out.println("then run->" +s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// 异步执行完成时候执行
		name.thenAccept(s -> System.out.println("then accept==>"+s));
		
//		String s = name.get();
//		System.out.println(s);
		Thread.sleep(500);
		//关闭资源
		connect.close();
		redisClient.shutdown();
	}
	
	@Test
	public void testFun2(){
		// 创建客户端
		RedisClient redisClient = RedisClient.create("redis://106.13.162.44");
		
		// 获取连接
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		
		// 获得异步api
		RedisAsyncCommands<String, String> async = connect.async();
		
		List<RedisFuture<String>> redisFutures = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			RedisFuture<String> set = async.set("key-" + i, "value-" + i);
			redisFutures.add(set);
		}
		long begin = System.currentTimeMillis();
		System.out.println(begin);
		LettuceFutures.awaitAll(1, TimeUnit.MINUTES, redisFutures.toArray(new RedisFuture[redisFutures.size()]));
		
		System.out.println(System.currentTimeMillis()-begin);
		
		//关闭资源
		connect.close();
		redisClient.shutdown();
	}
}
