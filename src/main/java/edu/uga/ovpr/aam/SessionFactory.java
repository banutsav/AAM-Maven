package edu.uga.ovpr.aam;

import edu.uga.ovpr.OVPRSessionFactory;

/**
 *
 * @author Glenn Owens
 */
public class SessionFactory implements OVPRSessionFactory<Session> {

    public Session createSession() {
        return new Session();
    }
}