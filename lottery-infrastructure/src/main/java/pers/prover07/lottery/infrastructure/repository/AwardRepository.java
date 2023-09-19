package pers.prover07.lottery.infrastructure.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.award.model.vo.DrawOrderVo;
import pers.prover07.lottery.domain.award.repository.IAwardRepository;
import pers.prover07.lottery.infrastructure.dao.IUserStrategyExportDao;
import pers.prover07.lottery.infrastructure.po.UserStrategyExport;

/**
 * 奖品表仓储服务
 *
 * @author Prover07
 * @date 2023/9/4 14:40
 */
@Component
@Slf4j
public class AwardRepository implements IAwardRepository {

    @Autowired
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public void updateUserStrategyGrantState(String uId, Long orderId, String awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setGrantState(grantState);

        userStrategyExportDao.update(
                userStrategyExport,
                Wrappers.<UserStrategyExport>lambdaQuery()
                        .eq(UserStrategyExport::getUId, uId)
                        .eq(UserStrategyExport::getOrderId, orderId)
                        .eq(UserStrategyExport::getAwardId, awardId)
        );

    }

}
