package com.github.zipcodewilmington.casino;

/**
 * Contract for all games in the Casino.
 * Methods marked `default` are optional overrides — implement them in your game class as needed.
 */
public interface GameInterface extends Runnable {

    // ── Required (must implement) ─────────────────────────────────────

    /** Add a player to the game. */
    void add(PlayerInterface player);

    /** Remove a player from the game. */
    void remove(PlayerInterface player);

    /** Main game loop — drives a full session. */
    void run();

    // ── Optional hooks (override in your game class as needed) ────────

    /** Load or reset any game state before a session starts. */
    default void fetch() {}

    /** Called at the start of each round. */
    default void start() {}

    /** Called at the end of each round. */
    default void end() {}

    /** Register or initialise any sub-games or game variants. */
    default void loadGames() {}

    /**
     * Factory method — create and return a player for this game.
     * Override to return your concrete player type (e.g. new BlackjackPlayer(account)).
     */
    default PlayerInterface createPlayer(CasinoAccount account) {
        return null;
    }

    /** Remove and clean up a player — delegates to remove() by default. */
    default void deletePlayer(PlayerInterface player) {
        remove(player);
    }
}
