package com.itranswarp.learnjava;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PreOverDiscountStrategy implements DiscountStrategy {

	@Override
	public BigDecimal getDiscount(BigDecimal total) {
		//如果没有满100，还是按照普通prime会员计算折扣
		if (total.compareTo(BigDecimal.valueOf(100)) < 0) {
			System.out.println("少于100元");
			return total.multiply(new BigDecimal("0.3")).setScale(2, RoundingMode.DOWN);
		}else {
			System.out.println("大于100元");
			BigDecimal overVale = BigDecimal.valueOf(20);
			BigDecimal prime = total.subtract(new BigDecimal("20")).multiply(new BigDecimal("0.3")).setScale(2, RoundingMode.DOWN);
			BigDecimal res = overVale.add(prime).setScale(2, RoundingMode.DOWN);
			return res;
		} 
		
	}
}