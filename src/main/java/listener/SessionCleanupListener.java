package listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class SessionCleanupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Iterate over all registered sessions and expire them
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            sessionRegistry.getAllSessions(principal, false)
                    .forEach(SessionInformation::expireNow);
        }
    }
}
