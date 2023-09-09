package pers.prover07.lottery.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应对象
 *
 * @author Prover07
 * @date 2023/8/25 15:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = 3254749114670886042L;

    private String code;

    private String info;

    public static Result buildResult(Constants.ResponseCode responseStatus) {
        return new Result(responseStatus.getCode(), responseStatus.getInfo());
    }

    public static Result buildResult(String code, String info) {
        return new Result(code, info);
    }


    public static Result buildSuccessResult() {
        return new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
    }

    public static Result buildSuccessResult(String info) {
        return new Result(Constants.ResponseCode.SUCCESS.getCode(), info);
    }

    public static Result buildErrorResult() {
        return new Result(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), Constants.ResponseCode.UNKNOWN_ERROR.getInfo());
    }

    public static Result buildErrorResult(String info) {
        return new Result(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), info);
    }

}
