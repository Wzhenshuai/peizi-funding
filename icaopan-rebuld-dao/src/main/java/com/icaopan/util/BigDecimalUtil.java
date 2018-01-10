package com.icaopan.util;

import java.math.BigDecimal;

/**
 * @author yueb
 * @description
 * @create 2012-10-25 上午08:50:23
 */
public class BigDecimalUtil {
    public static final int        STANDARD_ROUND_HALF = BigDecimal.ROUND_HALF_UP;
    public static final int        STANDARD_SCALE      = 4;
    /**
     * 0
     **/
    public static final BigDecimal ZERO                = new BigDecimal("0");
    /**
     * 100
     **/
    public static final BigDecimal HUNDRED             = new BigDecimal("100");

    public static BigDecimal valueOf(BigDecimal num) {
        if (num == null) {
            return ZERO;
        }
        return num;
    }

    public static BigDecimal valueOfScale4(BigDecimal num) {
        if (num == null) {
            return ZERO;
        }
        return num.setScale(4, BigDecimal.ROUND_HALF_UP);
    }
    
    public static BigDecimal valueOfScale8(BigDecimal num) {
        if (num == null) {
            return ZERO;
        }
        return num.setScale(8, BigDecimal.ROUND_HALF_UP);
    }
    
    public static BigDecimal valueOfScale2(BigDecimal num) {
        if (num == null) {
            return ZERO;
        }
        return num.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 求和
     *
     * @return num1 + num2+.....+numN
     */
    public static BigDecimal add(BigDecimal... nums) {
        BigDecimal result = ZERO;
        for (BigDecimal num : nums) {
            result = result.add(valueOfScale8(num));
        }
        return result;
    }

    /**
     * 求和
     *
     * @return num1 + num2
     */
    public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
        return valueOfScale8(num1).add(valueOfScale8(num2));
    }

    /**
     * @return num1 - num2
     */
    public static BigDecimal minus(BigDecimal num1, BigDecimal num2) {
        return valueOf(num1).subtract(valueOf(num2));
    }

    /**
     * @return num1 * num2
     */
    public static BigDecimal multiplyScale4(BigDecimal num1, BigDecimal num2) {
        return valueOfScale8(num1).multiply(valueOfScale8(num2));
    }

    /**
     * @return num1 * num2
     */
    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
        return valueOf(num1).multiply(valueOf(num2));
    }

    public static BigDecimal multiply(BigDecimal... nums) {
        BigDecimal result = BigDecimal.ONE;
        for (BigDecimal num : nums) {
            result = result.multiply(valueOf(num));
        }
        return result;
    }

    /**
     * 取整
     *
     * @return num1 / num2
     */
    public static BigDecimal divideToIntegralValue(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return ZERO;
        }
        if (ZERO.compareTo(valueOf(num2)) == 0) {
            return ZERO;
        }
        return valueOf(num1).divideToIntegralValue(valueOf(num2));
    }

    /**
     * 取余
     *
     * @return num1 % num2
     */
    public static BigDecimal remainder(BigDecimal num1, BigDecimal num2) {
        if (ZERO.compareTo(valueOf(num2)) == 0) {
            return ZERO;
        }
        return valueOf(num1).remainder(valueOf(num2));
    }

    /**
     * @return num1 / num2
     */
    public static BigDecimal divide(BigDecimal num1, BigDecimal num2) {
        return divide(num1, num2, 8, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * <pre>
     * BigDecimalUtil.divideToNull(null, null)          = null
     * BigDecimalUtil.divideToNull(null, 2)             = null
     * BigDecimalUtil.divideToNull(2, null)             = null
     * BigDecimalUtil.divideToNull(0, 0)                = 0
     * BigDecimalUtil.divideToNull(0, 2)                = 0
     * BigDecimalUtil.divideToNull(2, 0)                = 0
     * BigDecimalUtil.divideToNull(6, 2)                = 3
     * </pre>
     *
     * @return num1 / num2
     */
    public static BigDecimal divideToNull(BigDecimal num1, BigDecimal num2) {
        return divideToNull(num1, num2, 4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @return num1 / num2
     */
    public static BigDecimal divide(BigDecimal num1, BigDecimal num2, int scale, int roundingMode) {
        if (ZERO.compareTo(valueOf(num2)) == 0) {
            return ZERO;
        }
        return valueOf(num1).divide(valueOf(num2), scale, roundingMode);
    }

    /**
     * <pre>
     * BigDecimalUtil.divideToNull(null, null, 4, BigDecimal.ROUND_HALF_UP)          = null
     * BigDecimalUtil.divideToNull(null, 2, 4, BigDecimal.ROUND_HALF_UP)             = null
     * BigDecimalUtil.divideToNull(2, null, 4, BigDecimal.ROUND_HALF_UP)             = null
     * BigDecimalUtil.divideToNull(0, 0, 4, BigDecimal.ROUND_HALF_UP)                = 0
     * BigDecimalUtil.divideToNull(0, 2, 4, BigDecimal.ROUND_HALF_UP)                = 0
     * BigDecimalUtil.divideToNull(2, 0, 4, BigDecimal.ROUND_HALF_UP)                = 0
     * BigDecimalUtil.divideToNull(6, 2, 4, BigDecimal.ROUND_HALF_UP)                = 3
     * </pre>
     *
     * @return num1 / num2
     */
    public static BigDecimal divideToNull(BigDecimal num1, BigDecimal num2, int scale, int roundingMode) {
        if (num1 == null || num2 == null) {
            return null;
        }
        if (ZERO.compareTo(num2) == 0) {
            return ZERO;
        }
        return num1.divide(num2, scale, roundingMode);
    }

    /**
     * (num1 / num2)百分比
     *
     * @return num1 / num2 / 100
     */
    public static BigDecimal percent(BigDecimal num1, BigDecimal num2) {
        BigDecimal temp = divide(num1, num2);
        return percent(temp, 4);
    }

    /**
     * 返回百分比
     *
     * @return num1 / 100
     */
    public static BigDecimal percent(BigDecimal num1, int scale) {
        return divide(num1, HUNDRED, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 是否为0
     */
    public static boolean isZero(BigDecimal num) {
        return compareTo(ZERO, num) == 0;
    }

    /**
     * different
     */
    public static int compareTo(BigDecimal num1, BigDecimal num2) {
        return valueOf(num1).compareTo(valueOf(num2));
    }

    /**
     * different
     */
    public static int compareToScale4(BigDecimal num1, BigDecimal num2) {
        return valueOfScale4(num1).compareTo(valueOfScale4(num2));
    }

    public static BigDecimal setScale(BigDecimal num, int scale) {
        return num.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 是否出尽100
     */
    public static boolean isRemainderHundred(BigDecimal num) {
        return compareTo(ZERO, num.remainder(HUNDRED)) == 0;
    }

    /**
     * 返回qty根据unit的取整
     *
     * @param qty
     * @param unit
     * @return (qty/unit精度为0)*unit
     */
    public static BigDecimal rounding(BigDecimal qty, BigDecimal unit) {
        BigDecimal tradeUnitQty = divide(qty, unit, 0, BigDecimal.ROUND_DOWN);
        return multiply(tradeUnitQty, unit);
    }


    public static BigDecimal minusToZero(BigDecimal num1, BigDecimal num2) {
        BigDecimal result = minus(num1, num2);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            result = ZERO;
        }
        return result;
    }

    public static BigDecimal divideRoundUp(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return ZERO;
        }
        if (ZERO.compareTo(num2) == 0) {
            return ZERO;
        }
        return num1.divide(num2, 0, BigDecimal.ROUND_UP);
    }

    public static BigDecimal divideRoundDown(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return ZERO;
        }
        if (ZERO.compareTo(num2) == 0) {
            return ZERO;
        }
        return num1.divide(num2, 0, BigDecimal.ROUND_DOWN);
    }

    /**
     * 使用java正则表达式去掉多余的0
     *
     * @param s
     * @return
     */
    public static BigDecimal subZeroAndDot(BigDecimal s) {
        String str = s.toString();
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");//去掉多余的0
        }
        return new BigDecimal(str);
    }

}
