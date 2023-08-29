package pers.prover07.lottery.domain.strategy.service.draw;

import pers.prover07.lottery.domain.strategy.model.req.DrawReq;
import pers.prover07.lottery.domain.strategy.model.res.DrawRes;

public interface IDrawExec {

    DrawRes doDrawExec(DrawReq drawReq);

}
