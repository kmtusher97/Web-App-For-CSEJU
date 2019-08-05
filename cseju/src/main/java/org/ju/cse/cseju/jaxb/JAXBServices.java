package org.ju.cse.cseju.jaxb;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Kamrul Hasan
 */
@Service
public class JAXBServices {

    private final int CUT_INDEX = 55;

    /**
     * returns the xml string of an object
     * without initial xml doc tag
     *
     * @param object
     * @param <Obj>
     * @return
     */
    public <Obj> String objectToXmlString(Obj object, boolean operationType) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((Class<Obj>) object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(object, stringWriter);

            if(!operationType) {
                return (stringWriter.toString()).substring(CUT_INDEX);
            }
            else {
                return stringWriter.toString();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * returns the object form xml string
     * @param xmlObject
     * @param object
     * @param <Obj>
     * @return
     */
    public <Obj> Object xmlStringToObject(String xmlObject, Obj object) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((Class<Obj>) object.getClass());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader stringReader = new StringReader(xmlObject);
            return (Obj) unmarshaller.unmarshal(stringReader);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
