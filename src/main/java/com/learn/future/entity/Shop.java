package com.learn.future.entity;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zack.zhang
 * @projectName learn-lettuce
 * @title Shop
 * @package com.learn.future.entity
 * @description
 * @date 2019/9/17 5:31 下午
 */
public class Shop {
	
	
	public Shop() {
	}
	
	public Shop(String name) {
		this.name = name;
	}
	
	private String name;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static void delay() {
		try {
//			Thread.sleep(1000L);
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取商品价格
	 * @param product 商品
	 * @return
	 */
	public String getPrice(String product) {
		Discount.Code code = Discount.Code.values()[new Random().nextInt(Discount.Code.values().length)];
		double price = calculatePrice(product);
		return String.format("%s:%.2f:%s", name, price, code);
	}
	
	/**
	 * 计算商品价格
	 * @param product 商品
	 * @return
	 */
	private double calculatePrice(String product) {
		// 延迟操作
		delay();
		return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
	}
	
	/**
	 * new的方法获取异步
	 * @param product
	 * @return
	 */
	public Future<Double> getPriceAsyncByNew(String product) {
		CompletableFuture<Double> future = new CompletableFuture<>();
		
		ExecutorService pool = Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());
		pool.submit(() -> {
			try {
//				int i = 1 / 0;
				double price = this.calculatePrice(product);
				// 结果设置到异步结果中
				future.complete(price);
			} catch (Exception e) {
				//如果异步线程出现错误，吧错误放入futrue的completeExceptionally带出
				// 若果不捕获一次，异步get将持续阻塞等待异步线程的结果
				// 异常如果是出现在future的complete的后面的话，异常也不回出现在completeExceptionally中
				future.completeExceptionally(e);
			}
		});
//		new Thread(() ->{
//			try {
////				int i = 1 / 0;
//				double price = this.calculatePrice(product);
//				// 结果设置到异步结果中
//				future.complete(price);
//			} catch (Exception e) {
//				//如果异步线程出现错误，吧错误放入futrue的completeExceptionally带出
//				// 若果不捕获一次，异步get将持续阻塞等待异步线程的结果
//				// 异常如果是出现在future的complete的后面的话，异常也不回出现在completeExceptionally中
//				future.completeExceptionally(e);
//			}
//		}).start();
		
		
		// 异步线程
		future.thenRunAsync(() -> {
			// 关闭线程池
			pool.shutdown();
			while (!pool.isTerminated()) {
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		return future;
	}
	
	/**
	 * 通过工程模式穿件异步方法
	 * @param product
	 * @return
	 */
	public Future<Double> getPriceAsync(String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product));
	}
	
	
	/**
	 * 查找在指定商店中的商品价格，使用getPrice方法
	 * @param shops
	 * @param product
	 * @return
	 */
	public static List<String> findPrices(List<Shop>shops, String product){
		return shops.stream()
				.map(shop ->  shop.getPrice(product))
				.map(Quote::parse)
				.map(Discount::applyDiscount)
				.collect(Collectors.toList());
	}
	
	
	/**
	 * 采用并行的方式
	 * 查找在指定商店中的商品价格，使用getPrice方法
	 * @param shops
	 * @param product
	 * @return
	 */
	public static List<String> findPricesParallel(List<Shop>shops, String product){
		return shops.parallelStream()
				.map(shop ->  shop.getPrice(product))
				.map(Quote::parse)
				.map(Discount::applyDiscount)
				.collect(Collectors.toList());
	}
	
	
	/**
	 * 采用异步的方式
	 * 查找在指定商店中的商品价格，使用getPrice方法
	 * @param shops
	 * @param product
	 * @return
	 */
	public static List<String> findPricesAsync(List<Shop>shops, String product){
		List<CompletableFuture<String>> priceFuture = shops.stream()
				.map(shop -> CompletableFuture
						.supplyAsync(() -> shop.getPrice(product)))
				.map(future->future.thenApply(Quote::parse))
				.map(future->future.thenCompose(quote -> CompletableFuture.supplyAsync(()->Discount.applyDiscount(quote))))
				.collect(Collectors.toList());
		// join 和get方法的用处是一样的，只是join不需要进行异常try catch捕获
		// 没有直接在上面的代码流水线中在接着map来join，原因是如果那样做就会导致join阻塞完成后才去进行下一个map查找价格
		
		return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	/**
	 * 创建线程池
	 * @param minCount
	 * @return
	 */
	private static Executor buildThreadPool(int minCount){
		
		return new ThreadPoolExecutor(minCount, 100,
				60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>(),
				(r)-> {
					Thread thread = new Thread(r);
					// 使用守护线程，不阻止程序的关停，程序退出，线程被回收，否则线程一直等待无法发生的事件
					thread.setDaemon(true);
					thread.setName("shop-thread");
					return thread; });
	}
	
	/**
	 * 自己提供合适的线程池，
	 * 采用异步的方式，
	 * 查找在指定商店中的商品价格，使用getPrice方法
	 * @param shops
	 * @param product
	 * @return
	 */
	public static List<String> findPriceAsyncWithThreadPool(List<Shop> shops, String product) {
		Executor pool = buildThreadPool(shops.size());
		List<CompletableFuture<String>> priceFuture = shops.stream()
				.map(shop ->
						CompletableFuture.supplyAsync(
								() ->  shop.getPrice(product),
								pool))
				.map(future->future.thenApply(Quote::parse))
				.map(future->
						future.thenCompose(quote -> CompletableFuture.supplyAsync(()->Discount.applyDiscount(quote),pool)))
				.collect(Collectors.toList());
		return priceFuture
				.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
	}
}
