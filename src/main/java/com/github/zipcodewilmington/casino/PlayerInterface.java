package com.github.zipcodewilmington.casino;

/**
 * Contract for all players in the Casino.
 * All players must hold a CasinoAccount reference and know how to play their game.
 * Methods marked `default` are provided for free — override them if you need custom behaviour.
 */
public interface PlayerInterface {

    // ── Required (must implement) ─────────────────────────────────────

    /** @return the CasinoAccount used to log into the Casino system. */
    CasinoAccount getArcadeAccount();

    /**
     * Defines how this player plays their game.
     * @param <SomeReturnType> any return type you need
     */
    <SomeReturnType> SomeReturnType play();

    // ── Provided for free (override if needed) ────────────────────────

    /**
     * Alias for getArcadeAccount() — matches the UML name.
     * Override if your player stores the account under a different field.
     */
    default CasinoAccount fetchCasinoAccount() {
        return getArcadeAccount();
    }

    /** @return the player's current balance, or 0.0 if no account is set. */
    default double getBalance() {
        CasinoAccount account = getArcadeAccount();
        return account != null ? account.getBalance() : 0.0;
    }
}
