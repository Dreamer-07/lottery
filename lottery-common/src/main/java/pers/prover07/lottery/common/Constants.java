package pers.prover07.lottery.common;

/**
 * 系统中常用状态信息定义
 *
 * @author Prover07
 * @date 2023/8/25 15:30
 */
public class Constants {

    /**
     * 响应状态
     */
    public enum ResponseCode {
        SUCCESS("0000", "成功"),
        UNKNOWN_ERROR("0001", "未知错误"),
        ILLEGAL_PARAMETER("0002", "非法参数"),
        INDEX_DUP("0003", "主键冲突");

        private String code;
        private String info;

        ResponseCode(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 抽奖模式
     * 场景：两种抽奖算法描述，场景A20%、B30%、C50%
     * 算法1 - 单项概率：如果A奖品抽空后，B和C保持目前中奖概率，用户抽奖扔有20%中为A，因A库存抽空则结果展示为未中奖。为了运营成本，通常这种情况的使用的比较多
     * 算法2 - 总体概率：如果A奖品抽空后，B和C奖品的概率按照 3:5 均分，相当于B奖品中奖概率由 0.3 升为 0.375
     */
    public enum DrawStrategyMode {

        SINGLE(1, "单项概率"),


        ENTIRETY(2, "总体概率");

        private Integer code;

        private String info;

        DrawStrategyMode(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 中奖的状态
     */
    public enum DrawState {
        FAIL(0, "未中奖"),

        SUCCESS(1, "已中奖"),

        COVER(2, "大保底啦");

        private Integer code;

        private String info;

        DrawState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 发奖状态
     */
    public enum GrantType {
        INIT(0, "等待发奖"),
        COMPLETE(1, "发奖成功"),
        FAIL(2, "发奖失败");

        private Integer code;

        private String info;

        GrantType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 奖品状态
     */
    public enum AwardType {
        COUPON_GOODS(3, "优惠券"),
        REDEEM_CODE_GOODS(2, "兑换码"),
        DESC_GOODS(1, "文字描述"),
        PHYSICAL_GOODS(4, "实物奖品"),;
        private Integer code;

        private String info;

        AwardType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    public static class Cache {
        /**
         * 活动策略领域 - 抽奖奖品 - 库存锁
         */
        public static String STRATEGY_DRAW_STOCK_LOCK_KEY = "strategy:draw:stock_lock:";
    }
}
