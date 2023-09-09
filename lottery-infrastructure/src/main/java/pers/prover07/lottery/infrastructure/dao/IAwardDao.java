package pers.prover07.lottery.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.prover07.lottery.infrastructure.po.Award;

import java.util.List;

@Mapper
public interface IAwardDao extends BaseMapper<Award> {

    void insertList(List<Award> awardList);
}