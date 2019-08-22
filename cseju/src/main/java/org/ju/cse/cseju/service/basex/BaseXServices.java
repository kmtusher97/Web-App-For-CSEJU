package org.ju.cse.cseju.service.basex;

import lombok.NoArgsConstructor;
import org.basex.api.client.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author Kamrul Hasan
 */
@NoArgsConstructor
public class BaseXServices {
    private ClientSession session;

    @Autowired
    public BaseXServices(ClientSession session) {
        this.session = session;
    }

    /**
     * performs read query
     *
     * @param xQuery
     * @return
     */
    public String executeReadQuery(String xQuery) {
        /**
         * Start server on default port 1984
         */
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
    public void executeWriteQuery(String xQuery) {
        try {
            session.execute("XQUERY " + xQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
