package com.mx.util;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/8/22.
 */
public class OperationUtil {

    private enum TYPE { PLUS, MINUS, MULTIPLY, DIVIDE  }


    public static void main(String[] args) {
        BigDecimal b100 = new BigDecimal(100);
        BigDecimal b50 = new BigDecimal(50);
        BigDecimal b30 = new BigDecimal(30);
        BigDecimal b20 = new BigDecimal(20);
        BigDecimal b10 = new BigDecimal(10);


        BigDecimal b1002 = new BigDecimal("100.02");
        BigDecimal b502 = new BigDecimal("50.26");
        BigDecimal b302 = new BigDecimal("30.78");
        BigDecimal b202 = new BigDecimal("20.25");

        //加法
        System.out.println("加法:" + b100 + " + " + b202 + " + " + b10 + " = " + plus(b100, b202, b10));
        //减法
        System.out.println("减法:" + b100 + " - " + b302 + " - " + b20 + " = " + minus(b100, b302, b20));
        //乘法
        System.out.println("乘法:" + b50 + " * " + b20 + " * " + b302 + " = " + multiply(b50, b20, b302));
        //除法，保留一位小数
        System.out.println("除法，保留一位小数:" + b30 + " / " + b1002 + " = " + divide(b1002, 1, b30));
        //折旧率，保留一位小数
        System.out.println("折旧率，保留一位小数:" + b30 + " / " + b502 + " = " + depreciation(b502, 1, b30) + "%");
        //有毛利率的乘法
        System.out.println("有毛利率的乘法:" + b302 + " * " + b202  + " * " + b10 + " = " + multiplyByRatio(b302, b202, b10));
        //毛利率=1-成本价格/销售价格
        System.out.println("毛利率=1-成本价格/销售价格:1 - " + b302 + " / " + b502 + " = " + getProfitRatio(b302, b502) + "%");
        //加权毛利率=(1-成本价格/销售价格）*销售比例%
        System.out.println("加权毛利率=(1-成本价格/销售价格）*销售比例%:(1 - " + b302 + " / " + b502 + ") * " + b30 + "% = " + getProfitRatio(b302, b502, b30) + "%");
        //总净利润=总毛利润+其他收入-固定支出-所得税-其他支出
        System.out.println("总净利润=总毛利润+其他收入-固定支出-所得税-其他支出:" + b1002 + " + " + b502 + " - " + b302 + " - " + b202 + " - " + b50 + ") * " + b30 + "% = " + getTotalNetProfit(b1002, b502, b302, b202, b50));
        //销货比=（短期负债+长期负债）/营业收入*100%
        System.out.println("销货比=（短期负债+长期负债）/营业收入*100%: (" + b202 + " + " + b502 + ") / " + b1002 + " = " + getSalesRatio(b202, b502, b1002) + "%");
        //速动比=（流动资产-存货）/流动负债*100%
        System.out.println("速动比=（流动资产-存货）/流动负债*100%: (" + b502 + " - " + b202 + ") / " + b10 + " = " + getSalesRatio(b202, b502, b1002) + "%");
        //月平均月可支=(家庭收入+其他收入-固定支出-个人所得税-其他支出)/月份数
        System.out.println("月平均月可支=(家庭收入+其他收入-固定支出-个人所得税-其他支出)/月份数: (" + b502 + " + " + b1002 + " - " + b50 + " - " + b20 + " - " + b10 + ") / " + 12 + " = " + getMonthlyAverageAvailable(b502, b1002, b50, b20, b10, 12));
        //应有权益=初始权益+期间利润+期间注资+升值-期间提取-折旧/贬值
        System.out.println("应有权益=初始权益+期间利润+期间注资+升值-期间提取-折旧/贬值: " + b502 + " + " + b1002 + " + " + b50 + " + " + b20 + " - " + b100  + " - " + b50 + " = " + getDeservedRight(b502, b1002, b50, b20, b100, b50));
        //偏差率=（总销售额-检验销售额）/总销售额*100%
        System.out.println("偏差率=（总销售额-检验销售额）/总销售额*100%: (" + b502 + " - " + b20 + ") / " + b502 + " = " + getDeviationRatio(b502, b20) + "%");
        //应有权益 = 初始权益+期间收入+升值-期间提取-折旧/贬值
        System.out.println("应有权益 = 初始权益+期间收入+升值-期间提取-折旧/贬值: " + b1002 + " + " + b502 + " + " + b302 + "  " + b202 + " - " + b50 + ") * " + b30 + "% = " + getDeservedRight(b1002, b502, b302, b202, b50));
    }





    /**
     * 不定个数的求和，总数
     * 适用于【各种金额总额加和】
     * @param values
     * @return
     */
    public static BigDecimal plus (BigDecimal ...values) {
        return operation(null, TYPE.PLUS, 0, values);
    }

    /**
     * 不定个数的求和，总数
     * 适用于【各种金额总额加和】
     * @param values
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal plus (int scale,BigDecimal ...values) {
        return operation(null, TYPE.PLUS, scale, values);
    }

    /**
     * 不定个数的求积，乘积
     * 适用于【金额=现值*数量】
     * @param values
     * @return
     */
    public static BigDecimal multiply (BigDecimal ...values) {
        return operation(null, TYPE.MULTIPLY, 0, values);
    }

    /**
     * 不定个数的求积，乘积
     * 适用于【金额=现值*数量】
     * @param values
     * @param scale 保留小数位
     * @return
     */
    public static BigDecimal multiply (int scale,BigDecimal ...values) {
        return operation(null, TYPE.MULTIPLY, scale, values);
    }

    /**
     * 不定个数的差值
     * 适用于【现值=原值-折旧金额?，等】
     * @param first 被减数
     * @param values 减数
     * @return
     */
    public static BigDecimal minus (BigDecimal first, BigDecimal ...values) {
    	//防止减数都是null,返回0的情况
		for (int i = 0; i <values.length; i++) {
			if (values[i] != null) {
				return operation(first, TYPE.MINUS, -1, values);
			}
		}
		return first;
	}
    /**
     * 不定个数的差值
     * 适用于【现值=原值-折旧金额?，等】
     * @param first 被减数
     * @param scale 保留小数
     * @param values 减数
     * @return
     */
    public static BigDecimal minus (BigDecimal first, int scale,BigDecimal ...values) {
        return operation(first, TYPE.MINUS, scale, values);
    }

    /**
     * 保留固定小数的除法
     * 适用于【月均=总金额/月份】
     * @param first 除数（分母）
     * @param scale 保留几位小数【正整数，如果传入负数，做0处理】
     * @param second 被除数
     * @return
     */
    public static BigDecimal divide (BigDecimal first, int scale, BigDecimal second) {
//        if (second == null)
//            return null;
        return operation(first, TYPE.DIVIDE, scale, second);
    }

    /**
     * 有率的除法
     * 适用于【360/周转率】
     * @return
     */
    public static BigDecimal divideByRatio (BigDecimal first, int scale, BigDecimal second) {
        BigDecimal minus = multiply(new BigDecimal("100"), second);
        return divide(first, scale, minus);
    }

    /**
     * 有毛利率的乘法（如果任何一个为空，返回0）
     * 适用于【总毛利润=总销售额*毛利率（四舍五入，不保留小数）】
     * @param values
     * @return
     */
    public static BigDecimal multiplyByRatio (BigDecimal ...values) {
        for (BigDecimal v: values) {
            if (v == null)
                return new BigDecimal("0");
        }
        BigDecimal minus = multiply(values);
        return divide(new BigDecimal(100), 2, minus);
    }

    /**
     * 保留固定小数的折旧率
     * 适用于【折旧率=折旧金额/原值*100%】
     * @param first 除数（分母）
     * @param scale 保留几位小数【正整数，如果传入负数，做0处理】
     * @param second 被除数
     * @return 运算过程乘以100，返回值需要自行加%
     */
    public static BigDecimal depreciation (BigDecimal first, int scale, BigDecimal second) {
//        if (first == null && second == null) {
//            return null;
//        }
//        if (second == null)
//            return new BigDecimal("0");
        BigDecimal minus = multiply(second, new BigDecimal(100));
        return divide(first, scale, minus);
    }

    /********************************************************上面通用基本运算，下面自定义固定运算*******************************************************************/

    /**
     * 损益情况（经营类）基本信息 【毛利率】
     * 适用于【显示，毛利率=1-成本价格/销售价格】
     * @param salePrice
     * @param costPrice
     * @return
     */
    public static BigDecimal getProfitRatio(BigDecimal salePrice, BigDecimal costPrice){
        BigDecimal divide = depreciation(salePrice, 2, costPrice);
        return minus(new BigDecimal(100), divide);
    }

    /**
     * 损益情况（经营类）基本信息 【加权毛利率】
     * 适用于【显示，加权毛利率=(1-成本价格/销售价格）*销售比例%】
     * @param salePrice
     * @param costPrice
     * @param saleRatio
     * @return
     */
    public static BigDecimal getProfitRatio(BigDecimal salePrice, BigDecimal costPrice, BigDecimal saleRatio){
        BigDecimal ratio = getProfitRatio(salePrice, costPrice);
        return multiplyByRatio(ratio, saleRatio);
    }

    /**
     * 财务分析（经营类）,损益分析,总净利润
     * 使用于【总净利润=总毛利润+其他收入-固定支出-所得税-其他支出】
     * @param crossProfit
     * @param otherIncomeAmount
     * @param fixedCostDefrayAmount
     * @param texDefrayAmount
     * @param otherDefrayAmount
     * @return
     */
    public static BigDecimal getTotalNetProfit(BigDecimal crossProfit, BigDecimal otherIncomeAmount, BigDecimal fixedCostDefrayAmount, BigDecimal texDefrayAmount, BigDecimal otherDefrayAmount){
        BigDecimal minus = plus(crossProfit,otherIncomeAmount);
        return minus(minus, fixedCostDefrayAmount, texDefrayAmount, otherDefrayAmount);
    }

    /**
     * 财务分析（经营类）,偿还能力分析,销货比
     * 适用于【销货比=（短期负债+长期负债）/营业收入*100%】
     * @param debtsShortAmount
     * @param debtsLongAmount
     * @param businessIncomeAmount
     * @return
     */
    public static BigDecimal getSalesRatio(BigDecimal debtsShortAmount, BigDecimal debtsLongAmount, BigDecimal businessIncomeAmount){
        BigDecimal div = plus(debtsShortAmount, debtsLongAmount);
        return depreciation(businessIncomeAmount, 1 , div);
    }


    /**
     * 财务分析（经营类）,偿还能力分析,速动比
     * 适用于【速动比=（流动资产-存货）/流动负债*100%】
     * @param flow
     * @param assetsStockAmount
     * @param flowLiabilities
     * @return
     */
    public static BigDecimal getQuickRatio(BigDecimal flow, BigDecimal assetsStockAmount, BigDecimal flowLiabilities) {
        BigDecimal div = minus(flow, assetsStockAmount);
        return depreciation(flowLiabilities, 1 , div);
    }

    /**
     * 财务分析（消费类）,损益分析,月平均月可支
     * 适用于【月平均月可支=(家庭收入+其他收入-固定支出-个人所得税-其他支出)/月份数】
     * @param householdIncome
     * @param otherIncomeAmount
     * @param fixedCostDefrayAmount
     * @param texDefrayAmount
     * @param otherDefrayAmount
     * @param months
     * @return
     */
    public static BigDecimal getMonthlyAverageAvailable(BigDecimal householdIncome, BigDecimal otherIncomeAmount, BigDecimal fixedCostDefrayAmount,
                                                        BigDecimal texDefrayAmount, BigDecimal otherDefrayAmount, int months){
        BigDecimal div = getTotalNetProfit(householdIncome, otherIncomeAmount, fixedCostDefrayAmount, texDefrayAmount, otherDefrayAmount);
        return divide(new BigDecimal(months), 0, div);
    }



    /**
     * 交叉检验（经营类），权益检验，应有权益【显示，应有权益=初始权益+期间利润+期间注资+升值-期间提取-折旧/贬值】
     * @param initRight
     * @param periodProfit
     * @param periodInjection
     * @param appreciation
     * @param periodExtract
     * @param depreciation
     * @return
     */
    public static BigDecimal getDeservedRight(BigDecimal initRight, BigDecimal periodProfit, BigDecimal periodInjection, BigDecimal appreciation, BigDecimal periodExtract, BigDecimal depreciation){
        BigDecimal minus = plus(initRight, periodProfit, periodInjection, appreciation);
        return minus(minus, periodExtract, depreciation);
    }

    /**
     * 偏差率
     * 适用于【偏差率=（毛利率-检验净利率）/毛利率*100%，偏差率=（总销售额-检验销售额）/总销售额*100%】
     * @param minusTotal 【毛利率，总销售额】
     * @param minus 【检验净利率，检验销售额】
     * @return
     */
    public static BigDecimal getDeviationRatio(BigDecimal minusTotal, BigDecimal minus){
        BigDecimal divide = minus(minusTotal, minus);
        return depreciation(minusTotal, 1, divide);
    }

    /**
     * 交叉检验（消费类），权益检验，应有权益【显示，初始权益+期间收入+升值-期间提取-折旧/贬值】
     * @param initRight
     * @param periodInjection ? 期间收入? 期间注资
     * @param appreciation
     * @param periodExtract
     * @param depreciation
     * @return
     */
    public static BigDecimal getDeservedRight(BigDecimal initRight, BigDecimal periodInjection, BigDecimal appreciation, BigDecimal periodExtract, BigDecimal depreciation){
        BigDecimal minus = plus(initRight, periodInjection, appreciation);
        return minus(minus, periodExtract, depreciation);
    }


    /**
     * 通用计算方式，私有
     * @param first 【被减数，被除数】
     * @param type 【计算方式，加减乘除】
     * @param scale 【除法，要保留的小数点个数】
     * @param values 【通用不固定参数，加数，减数，除数，乘数】
     * @return
     */
    private static BigDecimal operation(BigDecimal first, TYPE type, int scale, BigDecimal ...values) {
        boolean isNull2 = true;
        for (BigDecimal value : values) {
            if (value != null) {
                isNull2 = false;
                break;
            }
        }
        if (isNull2) return null;
        BigDecimal resultB = new BigDecimal(0);
        switch (type) {
            case PLUS:      //加
                if (values == null)
                    return null;
                if (values.length == 1)
                    return values[0];
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == null)
                        continue;
                    resultB = resultB.add(values[i]);
                }
                if (scale > -1)
                    resultB = resultB.divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP);
                return resultB;
            case MINUS:     //减
                if (first == null && values == null)
                    return null;
                first = first == null ? resultB : first;
                if (values == null)
                    return first;
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == null)
                        continue;
                    if (resultB.floatValue() == 0)
                        resultB = first.subtract(values[i]);
                    else
                        resultB = resultB.subtract(values[i]);
                }
                if (scale > -1)
                    resultB = resultB.divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP);
                return resultB;
            case MULTIPLY:      //乘
                if (values == null)
                    return null;
                if (values.length == 1)
                    return values[0];
                resultB = new BigDecimal(1);
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == null)
                        continue;
                    resultB = resultB.multiply(values[i]);
                }
                if (scale > -1)
                    resultB = resultB.divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP);
                return resultB;
            case DIVIDE:        //除
                if (first ==null || first.floatValue() == 0 || values == null || values[0] == null)
                    return null;
                if (scale < 0 )
                    scale = 0;
                resultB = values[0].divide(first, scale, BigDecimal.ROUND_HALF_UP);
                return resultB;
            default:
                return null;
        }
    }
}
