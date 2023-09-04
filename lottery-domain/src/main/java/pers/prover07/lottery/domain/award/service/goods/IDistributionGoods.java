package pers.prover07.lottery.domain.award.service.goods;

import pers.prover07.lottery.domain.award.model.req.GoodsReq;
import pers.prover07.lottery.domain.award.model.res.DistributionRes;

/**
 * 定义商品发奖的接口
 *
 * @author Prover07
 * @date 2023/9/1 14:40
 */
public interface IDistributionGoods {

    /**
     * 获取中奖商品
     *
     * @param goodsReq
     * @return
     */
    DistributionRes distribution(GoodsReq goodsReq);

}
