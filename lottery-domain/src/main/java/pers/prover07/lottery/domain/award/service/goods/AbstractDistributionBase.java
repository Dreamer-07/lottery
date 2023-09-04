package pers.prover07.lottery.domain.award.service.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.award.model.req.GoodsReq;
import pers.prover07.lottery.domain.award.model.res.DistributionRes;
import pers.prover07.lottery.domain.award.repository.IAwardRepository;

import javax.annotation.Resource;

/**
 * 奖品发货基础流程
 *
 * @author Prover07
 * @date 2023/9/1 14:56
 */
public abstract class AbstractDistributionBase implements IDistributionGoods {

    @Resource
    protected IAwardRepository awardRepository;

    protected Logger logger = LoggerFactory.getLogger(AbstractDistributionBase.class);


    @Override
    public DistributionRes distribution(GoodsReq goodsReq) {
        logger.info("award distribution goods: uId - {}, awardId - {}, awardName - {}", goodsReq.getUId(), goodsReq.getAwardId(), goodsReq.getAwardName());

        boolean status = doDistribution(goodsReq);

        return buildDistributionRes(goodsReq.getUId(), status);
    }


    /**
     * 具体奖品发放逻辑函数
     *
     * @param goodsReq
     * @return
     */
    protected abstract boolean doDistribution(GoodsReq goodsReq);

    /**
     * 构建奖品发放的响应信息体
     *
     * @param uId
     * @param status
     * @return
     */
    protected DistributionRes buildDistributionRes(String uId, boolean status) {
        Constants.GrantType grantType = status ? Constants.GrantType.COMPLETE : Constants.GrantType.FAIL;
        return new DistributionRes(uId, grantType.getCode(), grantType.getInfo(), buildStatementId());
    }

    /**
     * 构建结算单ID(如：发券后有券码、发货后有单号等，用于存根查询)
     * @return
     */
    protected String buildStatementId() {
        return "";
    }

}
