package fit.hcp.imoocsecskill.enums;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @Date: 2022/01/27/11:24 AM
 */
class SeckillStateEnumTest {
    @Test
    public void test(){
        System.out.println(SeckillStateEnum.END);
        System.out.println(SeckillStateEnum.valueOf("END"));
        System.out.println(Arrays.toString(SeckillStateEnum.values()));
        System.out.println(SeckillStateEnum.valueOf("END"));
        System.out.println(SeckillStateEnum.stateOf(1));
        System.out.println(SeckillStateEnum.stateOf(10));
    }

}