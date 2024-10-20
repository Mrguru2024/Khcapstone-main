package listener;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.DisposableBean;

@Component
public class SessionCleanupOnShutdown implements DisposableBean {


    private final SessionRegistry sessionRegistry;

    public SessionCleanupOnShutdown(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void destroy() throws Exception {
        // Invalidate all active sessions before shutdown
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            sessionRegistry.getAllSessions(principal, false)
                    .forEach(SessionInformation::expireNow);
        }
    }
}
