package org.ju.cse.cseju.repository.basex;

import lombok.NoArgsConstructor;
import org.basex.api.client.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author tshr
 */
@NoArgsConstructor
public class BaseXRepository {

    private ClientSession session;

    @Autowired
    public BaseXRepository(ClientSession session) {
        this.session = session;
    }

    /**
     * performs read query
     *
     * @param xQuery
     * @return XML String
     */
    public String read(String xQuery) {
        /**Start server on default port 1984**/
        String result = null;
        try {
            result = session.execute("XQUERY " + xQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * performs write query
     *
     * @param xQuery
     */
    public void write(String xQuery) {
        try {
            session.execute("XQUERY " + xQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param parent
     * @param parentAttributeName
     * @param parentAttributeValue
     * @param nodeName
     * @param nodeAttributeName
     * @param nodeAttributeValue
     * @return XML String getByParentAnId
     */
    public String getByParentAndId(
            String parent,
            String parentAttributeName,
            String parentAttributeValue,
            String nodeName,
            String nodeAttributeName,
            String nodeAttributeValue
    ) {

        return read(
                "//" + parent + "[@" + parentAttributeName + "=\"" +
                        parentAttributeValue + "\"]/" + nodeName + "[@" +
                        nodeAttributeName + "=\"" + nodeAttributeValue + "\"]"
        );
    }

    /**
     * insert a node by parents attribute
     *
     * @param parent
     * @param attributeName
     * @param attributeValue
     * @param node
     */
    public void insert(
            String parent,
            String attributeName,
            String attributeValue,
            String node
    ) {
        write(
                "insert node " + node + " into //" + parent
                        + "[@" + attributeName + "=\"" + attributeValue + "\"]"
        );
    }


    /**
     * delete node by attribute value
     *
     * @param nodeName
     * @param attributeName
     * @param attributeValue
     */
    public void delete(
            String nodeName,
            String attributeName,
            String attributeValue
    ) {
        write(
                "delete nodes //" + nodeName +
                        "[@" + attributeName + "=\"" + attributeValue + "\"]"
        );
    }

    /**
     * delete by parent
     *
     * @param parent
     * @param parentAttributeName
     * @param parentAttributeValue
     * @param nodeName
     * @param attributeName
     * @param attributeValue
     */
    public void delete(
            String parent,
            String parentAttributeName,
            String parentAttributeValue,
            String nodeName,
            String attributeName,
            String attributeValue
    ) {

        write(
                "delete nodes //" + parent + "[@" +
                        parentAttributeName + "=\"" + parentAttributeValue + "\"]/" +
                        nodeName + "[@" + attributeName + "=\"" + attributeValue + "\"]"
        );
    }


    /**
     * update by parent id
     *
     * @param parent
     * @param parentAttributeName
     * @param parentAttributeValue
     * @param node
     * @param nodeName
     * @param attributeName
     * @param attributeValue
     */
    public void saveOrUpdate(
            String parent,
            String parentAttributeName,
            String parentAttributeValue,
            String node,
            String nodeName,
            String attributeName,
            String attributeValue
    ) {

        delete(
                parent,
                parentAttributeName,
                parentAttributeValue,
                nodeName,
                attributeName,
                attributeValue
        );
        insert(
                parent,
                parentAttributeName,
                parentAttributeValue,
                node
        );
    }

    /**
     * @param parentNode
     * @param parentNodeAttributeName
     * @param parentNodeAttributeValue
     * @param nodeName
     * @return XMLString
     */
    public String getAllAsXMLString(
            String parentNode,
            String parentNodeAttributeName,
            String parentNodeAttributeValue,
            String nodeName
    ) {

        return read(
                "for $nd in //" + parentNode + "[@" +
                        parentNodeAttributeName + "=\"" + parentNodeAttributeValue +
                        "\"]  return $nd//" + nodeName
        );
    }

    /**
     * @param parentNode
     * @param parentNodeAttributeName
     * @param parentNodeAttributeValue
     * @param nodeName
     * @return
     */
    public String getAllAsData(
            String parentNode,
            String parentNodeAttributeName,
            String parentNodeAttributeValue,
            String nodeName
    ) {
        return read(
                "for $nd in //" + parentNode + "[@" +
                        parentNodeAttributeName + "=\"" + parentNodeAttributeValue +
                        "\"]  return data($nd//" + nodeName + ")"
        );
    }
}
