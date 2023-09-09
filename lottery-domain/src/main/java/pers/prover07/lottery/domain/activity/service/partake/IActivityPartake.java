package pers.prover07.lottery.domain.activity.service.partake;

import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.res.PartakeRes;

/**
 * 活动参与接口
 *
 * @author Prover07
 * @date 2023/9/4 17:33
 */
public interface IActivityPartake {


    /**
     * 参与活动
     * @param req
     * @return
     */
    PartakeRes doPartake(PartakeReq req);

}
