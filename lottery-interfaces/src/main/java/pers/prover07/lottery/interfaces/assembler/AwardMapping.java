package pers.prover07.lottery.interfaces.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;
import pers.prover07.lottery.rpc.dto.AwardDTO;

/**
 * 中奖奖品信息映射
 *
 * @author Prover07
 * @date 2023/9/19 11:31
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AwardMapping extends IMapping<DrawAwardVO, AwardDTO> {

    @Mapping(target = "userId", source = "UId")
    @Override
    AwardDTO sourceToTarget(DrawAwardVO source);

    @Override
    DrawAwardVO targetToSource(AwardDTO source);

}
