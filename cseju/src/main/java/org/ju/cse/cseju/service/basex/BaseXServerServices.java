package org.ju.cse.cseju.service.basex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.basex.api.client.ClientSession;
import org.basex.core.cmd.CreateDB;

import java.io.IOException;

/**
 * @author Kamrul Hasan
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseXServerServices {

    private final int SERVER_PORT = 1984;
    private final String SERVER_HOST = "localhost";
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin";
    private final String STORAGE_LOCATION = "src/main/resources/xml/syllabus/";
    private final String XML_EXTENSION = ".xml";

    private org.basex.BaseXServer baseXServer;

    private ClientSession session;

    /**
     * Starts database
     *
     * @param databaseName
     */
    public void startService(String databaseName) {
        try {
            baseXServer = new org.basex.BaseXServer();
            try {
                session = new ClientSession(
                        SERVER_HOST,
                        SERVER_PORT,
                        ADMIN_USERNAME,
                        ADMIN_PASSWORD
                );
                session.execute(new CreateDB(
                        databaseName,
                        STORAGE_LOCATION + databaseName + XML_EXTENSION
                ));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
