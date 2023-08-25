package pers.prover07.lottery.rpc.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.rpc.dto.ActivityDto;

import java.io.Serializable;

/**
 * 活动信息响应体
 *
 * @author Prover07
 * @date 2023/8/25 15:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRes implements Serializable {

    private Result result;
    private ActivityDto activity;

}