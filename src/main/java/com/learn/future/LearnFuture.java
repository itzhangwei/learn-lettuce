package com.learn.future;

import com.learn.future.entity.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zack.zhang
 * @projectName learn-lettuce
 * @title LearnFuture
 * @package com.learn.future
 * @description
 * @date 2019/9/17 4:28 下午
 */
public class LearnFuture {
	
	public static void main(String[] args) {
		Shop shop2 = null;
		final Shop shop = new Shop();
		shop2 = shop;
		System.out.println(shop== shop2);
		
//		System.out.println("-------------------------顺序-----------------------");
//		testFindPrice();
//
//		System.out.println("--------------------------并行流----------------------");
//		testFindPriceParallel();
//
//		System.out.println("------------------------async------------------------");
//		testFindPriceAsync();
//
//		System.out.println("------------------------async，自己提供合适的线程池------------------------");
//		testFindPriceAsyncWitheThreadPool();
	}
	
	/**
	 * 异步方式，自己提供合适的线程池
	 */
	private static void testFindPriceAsyncWitheThreadPool() {
		List<Shop> shops = getShops();
		long start = System.currentTimeMillis();
		List<String> prices = Shop.findPriceAsyncWithThreadPool(shops, "myPhone27S");
		System.out.println(prices);
		System.out.println("done in " + (System.currentTimeMillis() - start) + " milliseconds");
	}
	
	/**
	 * 异步方式，获取价格测试
	 */
	private static void testFindPriceAsync() {
		List<Shop> shops = getShops();
		long start = System.currentTimeMillis();
		List<String> prices = Shop.findPricesAsync(shops, "myPhone27S");
		System.out.println(prices);
		System.out.println("done in " + (System.currentTimeMillis() - start) + " milliseconds");
	}
	
	private static List<Shop> getShops() {
		return Arrays.asList(new Shop("BestPrice"),
				new Shop("LetsSaveBig"),
				new Shop("MyFavoriteShop"),
				new Shop("ShopEasy"),
				new Shop("淘宝"),
				new Shop("京东"),
				new Shop("天猫"),
				new Shop("苏宁"),
				new Shop("BuyItAll"));
	}
	
	/**
	 * 并行流查询所有商店中指定商品的不同价格
	 */
	private static void testFindPriceParallel() {
		List<Shop> shops = getShops();
		long start = System.currentTimeMillis();
		List<String> pricesParallel = Shop.findPricesParallel(shops, "myPhone27S");
		
		System.out.println(pricesParallel);
		
		System.out.println("done in " + (System.currentTimeMillis() - start) + " millisecond");
	}
	
	/**
	 * 多个商店查找商品价格方法测试
	 */
	private static void testFindPrice() {
		List<Shop> shops = getShops();
		
		long start = System.currentTimeMillis();
		List<String> result = Shop.findPrices(shops, "myPhone27S");
		System.out.println(result);
		
		System.out.println("Done in " + (System.currentTimeMillis()-start) +" millisecond");
	}
	
	/**
	 * 体验异步，输出每个节点的耗时时间
	 */
	private static void testPrice() {
		Shop shop = new Shop("BastShop");
		long start = System.currentTimeMillis();
		Future<Double> future = shop.getPriceAsyncByNew("my favorite product");
		long invocationTime = System.currentTimeMillis() - start;
		
		System.out.println("invocation return time :" + invocationTime + " milliseconds");
		try {
			double price = future.get();
			System.out.printf("price is %.2f%n", price);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("invocation return time:" + (System.currentTimeMillis() - start) + " milliseconds");
	}
	
	/**
	 * jdk8之前手动书写异步
	 */
	private static void futureBeforeJDK8() {
		// 手动创建线程池
		ExecutorService executorService =new ThreadPoolExecutor(1, 10,
				60L, TimeUnit.MILLISECONDS,
				new SynchronousQueue<Runnable>(),
				Executors.defaultThreadFactory());
		
		//异步线程执行别的
		Future<String> future = executorService.submit(() -> {
			try {
				System.out.println("我要开始睡5秒");
				Thread.sleep(1000);
				System.out.println("4。。。。");
				Thread.sleep(1000);
				System.out.println("3。。。。");
				Thread.sleep(1000);
				System.out.println("2。。。。");
				Thread.sleep(1000);
				System.out.println("1。。。。");
				Thread.sleep(1000);
				System.out.println("0。。。。");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "abcd";
		});
		
		// 执行当前计算
		
		System.out.println("执行当前线程计算---");
		
		// 获取异步值
		try {
			String s = future.get();
			System.out.println(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		
		// 关闭线程池
		
		// 调用关闭方法，如果有没有结束的线程就让主线程沉睡会
		executorService.shutdown();
		
		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
