package pers.prover07.lottery.domain.support.ids;

/**
 * id 生成接口
 * @author Prover07
 * @date 2023/9/5 14:14
 */
public interface IIdGenerator {

    /**
     * 获取ID，目前有三种实现方式
     *  1. 雪花算法，用于生成单号
     *  2. 日期算法，用于生成活动编号类，特性是生成数字串较短，但指定时间内不能生成太多
     *  3. 随机算法，用于生成策略ID
     *
     * ！如果要补充新的算法，除了补充说明外请修改 {@link IdContext} 中 map 容器的大小，避免没有添加成功
     * @return
     */
    long nextId();

}
