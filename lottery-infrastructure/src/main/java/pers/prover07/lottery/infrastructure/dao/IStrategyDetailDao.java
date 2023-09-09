package pers.prover07.lottery.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.prover07.lottery.infrastructure.po.StrategyDetail;

import java.util.List;

@Mapper
public interface IStrategyDetailDao extends BaseMapper<StrategyDetail> {

    void insertList(List<StrategyDetail> strategyDetailList);

    int deductStock(@Param("strategyId") Long strategyId, @Param("awardId") String awardId);
}