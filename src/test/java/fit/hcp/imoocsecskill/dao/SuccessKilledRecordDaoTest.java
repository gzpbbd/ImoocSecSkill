package fit.hcp.imoocsecskill.dao;

import fit.hcp.imoocsecskill.bean.SuccessKilledRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SuccessKilledRecordDaoTest {

    @Autowired
    SuccessKilledRecordDao successKilledRecordDao;

    @Autowired
    ApplicationContext applicationContext;


    @Test
    void insertRecord() {
        int i = successKilledRecordDao.insertRecord(1001, "18374715257");
        System.out.println(i);
    }

    @Test
    void queryByIdWithProduct() {
        SuccessKilledRecord record = successKilledRecordDao.queryByIdWithProduct(1001, "18374715257");
        System.out.println(record);
    }
}