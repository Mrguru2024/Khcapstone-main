package com.keycodehelp.keycodehelp_backend;

import com.keycodehelp.KeycodehelpBackendApplication;
import com.keycodehelp.services.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = KeycodehelpBackendApplication.class)
@ActiveProfiles("test")
public class KeycodehelpBackendApplicationTests {

    @MockBean
    private LoginService loginService;

    @Test
    public void contextLoads() {
        // Test that the application context loads without issues.
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
