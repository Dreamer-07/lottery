package pers.prover07.lottery.domain.award.service.factory;

import org.springframework.stereotype.Service;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.award.model.vo.DrawOrderVo;
import pers.prover07.lottery.domain.award.service.goods.IDistributionGoods;

/**
 * 奖品分发工厂
 *
 * @author Prover07
 * @date 2023/9/4 14:11
 */
@Service
public class DistributionGoodsFactory extends GoodsConfig {

    public IDistributionGoods getDistributionGoods(Integer awardType) {
        return goodsMap.get(awardType);
    }

}
