package com.senne.domain;

public enum AccountStatus {
    PENDING_VERIFICATION,    // Account is created but not yet verified
    ACITVE,                  // Account is active and in good standing
    SUSPENDED,               // Account in temporarily suspended, possibly due to violations
    DEACTIVATED,             // Account is deacitvated, user may have chosen to deactivate it
    BANNED,                  // Account is permanently banned, due to severe violations
    CLOSED                   // Account is permanently closed, possibly at user request
}
