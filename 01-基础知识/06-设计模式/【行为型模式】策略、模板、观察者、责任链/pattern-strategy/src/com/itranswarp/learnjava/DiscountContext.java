package com.itranswarp.learnjava;

import java.math.BigDecimal;

public class DiscountContext {
	//指定默认持有的策略（普通会员）
	private DiscountStrategy strategy = new UserDiscountStrategy();

	//允许用户自己传入策略
	public void setStrategy(DiscountStrategy strategy) {
		this.strategy = strategy;
	}

	//计算折扣后的价（减去优惠的价格即可）
	public BigDecimal calculatePrice(BigDecimal total) {
		return total.subtract(this.strategy.getDiscount(total)).setScale(2);
	}
}
