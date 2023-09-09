package pers.prover07.lottery.domain.support.ids.policy;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.support.ids.IIdGenerator;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;

/**
 * 短码生成策略，仅支持很小的调用量，用于生成活动配置类编号，保证全局唯一
 *
 * @author Prover07
 * @date 2023/9/5 14:17
 */
@Component
public class ShortCode implements IIdGenerator {

    @Override
    public long nextId() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // 打乱排序：2008年为准 + 小时 + 周期 + 日 + 三位随机数
        String idStr = String.valueOf(year - 2008) +
                hour +
                String.format("%02d", week) +
                day +
                String.format("%03d", new Random().nextInt(1000));

        return Long.parseLong(idStr);
    }
}
