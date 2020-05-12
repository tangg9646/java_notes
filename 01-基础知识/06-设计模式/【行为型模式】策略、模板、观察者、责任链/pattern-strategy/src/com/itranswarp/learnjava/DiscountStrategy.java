package com.itranswarp.learnjava;

import java.math.BigDecimal;

public interface DiscountStrategy {

	BigDecimal getDiscount(BigDecimal total);

}
