package org.ju.cse.cseju.service.syllabus;


import org.ju.cse.cseju.repository.basex.BaseXRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tshr
 */
@Service
public class SyllabusServices {

    @Autowired
    private BaseXRepository baseXRepository;

    /**
     * @param syllabusName
     * @return full syllabus as XML string
     */
    public String getSyllabus(String syllabusName) {
        return baseXRepository.read(
                "//syllabus[@name=\"" + syllabusName + "\"]/syllabusData"
        );
    }

    public void addYear(String syllabusName) {
        String[] yearIds = (baseXRepository.read(
                "for $nd in //syllabus[@name=\"" + syllabusName + "\"]/syllabusData/year return data($nd/[@id])"
        )).split("\\s+");

        Integer mexYear = 1;
        for (String yearId : yearIds) {
            if (yearId == "") break;
            Integer tmp = Integer.parseInt(yearId);
            if (tmp == mexYear ) {
                mexYear++;
                continue;
            }
            break;
        }

        baseXRepository.write(
                "insert node <year id=\"" + mexYear + "\"/> into //syllabus[@name=\"" +
                        syllabusName + "\"]/syllabusData"
        );
    }
}
