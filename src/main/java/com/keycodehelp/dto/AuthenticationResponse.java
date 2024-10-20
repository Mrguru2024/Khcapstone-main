package com.keycodehelp.dto;

import java.util.List;

public record AuthenticationResponse(String username, List<String> roles) {

}
