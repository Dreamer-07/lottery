package pers.prover07.lottery.domain.award.service.goods.impl;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.award.model.req.GoodsReq;
import pers.prover07.lottery.domain.award.service.goods.BaseDistributionBase;

/**
 * 优惠券奖品
 *
 * @author Prover07
 * @date 2023/9/1 15:49
 */
@Component
public class CouponGoods extends BaseDistributionBase {
    @Override
    public boolean doDistribution(GoodsReq goodsReq) {
        return true;
    }

}
