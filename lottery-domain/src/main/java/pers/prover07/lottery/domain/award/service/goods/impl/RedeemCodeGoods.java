package pers.prover07.lottery.domain.award.service.goods.impl;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.award.model.req.GoodsReq;
import pers.prover07.lottery.domain.award.service.goods.BaseDistributionBase;

/**
 * 兑换码类商品
 *
 * @author Prover07
 * @date 2023/9/4 14:34
 */
@Component
public class RedeemCodeGoods extends BaseDistributionBase {
    @Override
    protected boolean doDistribution(GoodsReq goodsReq) {
        return true;
    }
}
