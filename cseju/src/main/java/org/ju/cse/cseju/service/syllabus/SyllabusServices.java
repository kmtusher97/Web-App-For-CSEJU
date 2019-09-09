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

    /**
     * @param syllabusName
     */
    public void addYear(String syllabusName) {
        String[] yearIds = (baseXRepository.read(
                "for $nd in //syllabus[@name=\"" + syllabusName + "\"]/syllabusData/year return data($nd/[@id])"
        )).split("\\s+");

        Integer mexYear = 1;
        for (String yearId : yearIds) {
            if (yearId == "") break;
            Integer tmp = Integer.parseInt(yearId);
            if (tmp == mexYear) {
                mexYear++;
                continue;
            }
            break;
        }

        if (mexYear == 1) {
            baseXRepository.write(
                    "insert node <year id=\"" + mexYear + "\"/> as first into //syllabus[@name=\"" +
                            syllabusName + "\"]/syllabusData"
            );
        } else {
            baseXRepository.write(
                    "insert node <year id=\"" + mexYear + "\"/> after //syllabus[@name=\"" +
                            syllabusName + "\"]//year[@id=\"" + (mexYear - 1) + "\"]"
            );
        }
    }

    /**
     * @param syllabusName
     * @param yearId
     */
    public void deleteYearById(String syllabusName, Integer yearId) {
        baseXRepository.write(
                "delete node //syllabus[@name=\"" + syllabusName + "\"]//year[@id=\"" + yearId + "\"] "
        );
    }

    /**
     * @param syllabusName
     * @param yearId
     */
    public void addSemesterIntoYear(String syllabusName, Integer yearId) {
        String[] semesterIds = (baseXRepository.read(
                "for $nd in //syllabus[@name=\"" + syllabusName + "\"]/syllabusData/year[@id=\"" + yearId +
                        "\"]//semester return data($nd/[@id])"
        )).split("\\s+");

        Integer mexSemester = 1;
        for (String semesterId : semesterIds) {
            if (semesterId == "") break;
            Integer tmp = Integer.parseInt(semesterId);
            if (tmp == mexSemester) {
                mexSemester++;
                continue;
            }
            break;
        }

        if (mexSemester == 1) {
            baseXRepository.write(
                    "insert node <semester id=\"1\"/> as first into //syllabus[@name=\"" + syllabusName +
                            "\"]//year[@id=\"" + yearId + "\"]"
            );
        } else {
            baseXRepository.write(
                    "insert node <semester id=\"" + mexSemester + "\"/> after //syllabus[@name=\"" +
                            syllabusName + "\"]//year[@id=\"" + yearId + "\"]//semester[@id=\"" + (mexSemester - 1) + "\"]"
            );
        }
    }

}
