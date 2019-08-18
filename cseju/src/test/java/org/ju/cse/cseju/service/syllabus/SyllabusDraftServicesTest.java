package org.ju.cse.cseju.service.syllabus;

import org.ju.cse.cseju.model.syllabus.SyllabusDraft;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class SyllabusDraftServicesTest {

    private SyllabusDraft syllabusDraft;
    private SyllabusDraftServices syllabusDraftServices;



    @Before
    public void setUp() throws Exception {
        syllabusDraft = new SyllabusDraft();
        syllabusDraftServices = new SyllabusDraftServices();

        /*syllabusDraft.setSyllabusId(1);
        syllabusDraft.setType("undergrad");
        syllabusDraft.setEffectiveYearFrom("2018");
        syllabusDraft.setEffectiveYearTo("2022");
        syllabusDraft.setSession();
        syllabusDraft.setName();*/
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createNewSyllabus() {
        //syllabusDraftServices.createNewSyllabus(syllabusDraft);
    }
}