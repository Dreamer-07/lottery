package pers.prover07.lottery.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.prover07.lottery.infrastructure.po.UserStrategyExport;

@Mapper
public interface IUserStrategyExportDao extends BaseMapper<UserStrategyExport> {
}
