package com.learn.future.entity;

import java.util.concurrent.TimeUnit;

/**
 * @author zack.zhang
 * @projectName learn-lettuce
 * @title Discount
 * @package com.learn.future.entity
 * @description
 * @date 2019/9/18 5:02 下午
 */
public class Discount {
	public enum Code{
		NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
		
		private final int percentage;
		
		Code(int percentage) {
			this.percentage =percentage;
		}
	}
	
	
	public static String applyDiscount(Quote quote) {
		return quote.getShopName() + " price is " +
				Discount.apply(quote.getPrice(),
						quote.getDiscountCode());
	}
	private static double apply(double price, Code code) {
		delay();
		String format = String.format("%.2f", price * (100 - code.percentage) / 100);
		return Double.valueOf(format);
	}
	
	
	public static void delay() {
		try {
//			Thread.sleep(1000L);
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
