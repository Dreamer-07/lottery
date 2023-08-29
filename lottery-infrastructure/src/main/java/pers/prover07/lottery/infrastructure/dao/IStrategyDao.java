package pers.prover07.lottery.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.prover07.lottery.infrastructure.po.Strategy;

@Mapper
public interface IStrategyDao extends BaseMapper<Strategy> {
}