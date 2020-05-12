package com.itranswarp.learnjava;

import java.math.BigDecimal;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) {
		DiscountContext ctx = new DiscountContext();
		// 默认使用普通会员折扣:
		BigDecimal pay1 = ctx.calculatePrice(BigDecimal.valueOf(105));
		System.out.println(pay1);
		// 使用满减折扣:
		ctx.setStrategy(new OverDiscountStrategy());
		BigDecimal pay2 = ctx.calculatePrice(BigDecimal.valueOf(105));
		System.out.println(pay2);
		// 使用Prime会员折扣:
		ctx.setStrategy(new PrimeDiscountStrategy());
		BigDecimal pay3 = ctx.calculatePrice(BigDecimal.valueOf(105));
		System.out.println(pay3);
		//使用满减+prime会员折扣
		ctx.setStrategy(new PreOverDiscountStrategy());
		BigDecimal pay4 = ctx.calculatePrice(BigDecimal.valueOf(125));
		System.out.println(pay4);
	}
}
