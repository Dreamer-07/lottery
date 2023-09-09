package pers.prover07.lottery.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.prover07.lottery.infrastructure.po.UserTakeActivity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/11:31
 * @description:
 */
@Mapper
public interface IUserTakeActivityDao extends BaseMapper<UserTakeActivity> {
}
