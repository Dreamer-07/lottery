package pers.prover07.lottery.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.prover07.lottery.infrastructure.po.Activity;

@Mapper
public interface IActivityDao extends BaseMapper<Activity> {


    Activity queryByActivityId(Long activityId);

    int deductStock(Long activityId);

    int alterStatus(@Param("activityId") Long activityId, @Param("currentState") Integer currentState, @Param("afterState") Integer afterState);
}