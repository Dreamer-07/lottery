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
        ACTIVITY_STOCK_IS_EMPTY("0003", "库存已空"),
        REPLACE_TAKE("0004", "不允许重复参与活动"),
        DRAW_FAIL("0005", "未中奖");

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
        PHYSICAL_GOODS(4, "实物奖品"),
        ;
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

    /**
     * id 生成类型
     */
    public enum Ids {
        /**
         * 雪花算法
         */
        SNOW_FLAKE,
        /**
         * 日期算法
         */
        SHORT_CODE,
        /**
         * 随机算法
         */
        RANDOM_NUMERIC;
    }

    /**
     * 活动状态
     */
    public enum ActivityState {

        /**
         * 1：编辑
         */
        EDIT(1, "编辑"),
        /**
         * 2：提审
         */
        ARRAIGNMENT(2, "提审"),
        /**
         * 3：撤审
         */
        REVOKE(3, "撤审"),
        /**
         * 4：通过
         */
        PASS(4, "通过"),
        /**
         * 5：运行(活动中)
         */
        DOING(5, "运行(活动中)"),
        /**
         * 6：拒绝
         */
        REFUSE(6, "拒绝"),
        /**
         * 7：关闭
         */
        CLOSE(7, "关闭"),
        /**
         * 8：开启
         */
        OPEN(8, "开启");;

        private Integer code;

        private String info;

        ActivityState(Integer code, String info) {
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
     * 活动单使用状态 0未使用、1已使用
     */
    public enum TaskState {
        NO_USED(0, "未使用"),

        USED(1, "已使用");

        private Integer code;

        private String info;

        TaskState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public static class Cache {
        /**
         * 抽奖策略领域 - 抽奖奖品 - 库存锁
         */
        public static String STRATEGY_DRAW_STOCK_LOCK_KEY = "strategy:draw:stock_lock:";

        /**
         * 活动领域 - 名额预留库存
         */
        public static final String ACTIVITY_STOCK_COUNT_KEY = "strategy:stock_count:";

        /**
         * 活动领域 - 用户参与 - 已领取的活动次数
         */
        public static String ACTIVITY_PARTAKE_USER_TAKE_COUNT_KEY = "activity:partake:user_take_count:";
    }
}
