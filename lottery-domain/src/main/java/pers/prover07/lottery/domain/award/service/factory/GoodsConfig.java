package pers.prover07.lottery.domain.award.service.factory;

import org.springframework.context.annotation.Configuration;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.award.service.goods.IDistributionGoods;
import pers.prover07.lottery.domain.award.service.goods.impl.CouponGoods;
import pers.prover07.lottery.domain.award.service.goods.impl.DescGoods;
import pers.prover07.lottery.domain.award.service.goods.impl.PhysicalGoods;
import pers.prover07.lottery.domain.award.service.goods.impl.RedeemCodeGoods;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 各类奖品发奖配置
 *
 * @author Prover07
 * @date 2023/9/4 13:50
 */
@Configuration
public class GoodsConfig {

    protected static Map<Integer, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();

    @Resource
    private CouponGoods couponGoods;

    @Resource
    private DescGoods descGoods;

    @Resource
    private PhysicalGoods physicalGoods;

    @Resource
    private RedeemCodeGoods redeemCodeGoods;

    @PostConstruct
    public void init() {
        goodsMap.put(Constants.AwardType.DESC_GOODS.getCode(), descGoods);
        goodsMap.put(Constants.AwardType.COUPON_GOODS.getCode(), couponGoods);
        goodsMap.put(Constants.AwardType.PHYSICAL_GOODS.getCode(), physicalGoods);
        goodsMap.put(Constants.AwardType.REDEEM_CODE_GOODS.getCode(), redeemCodeGoods);
    }

}
