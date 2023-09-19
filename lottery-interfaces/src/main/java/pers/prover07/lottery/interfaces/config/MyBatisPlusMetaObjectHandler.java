package pers.prover07.lottery.interfaces.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 元数据注入(create_time，update_time)
 *
 * @author Prover07
 * @date 2023/9/19 11:36
 */
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动填入创建时间
        if (metaObject.hasGetter(CREATE_TIME)) {
            this.fillStrategy(metaObject, CREATE_TIME, new Date());
        }

        // 自动填入更新时间
        if (metaObject.hasGetter(UPDATE_TIME)) {
            this.fillStrategy(metaObject, UPDATE_TIME, new Date());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填入更新时间
        if (metaObject.hasGetter(UPDATE_TIME)) {
            this.fillStrategy(metaObject, UPDATE_TIME, new Date());
        }
    }
}
