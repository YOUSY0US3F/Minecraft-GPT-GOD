package net.bigyous.gptgodmc.loggables;

public interface Loggable {
    /**
     * Get the string representation of this log that can be fed to GPT
     */
    public String getLog();

    /**
     * Attempt to combine two loggables.
     * E.g. two item pickup events can be combined.
     * Returns false if they were not combined, true if they were.
     * The loggable on which combine was called, e.g. loggable1.combine(...),
     * will then take into account both logs.
     */
    public boolean combine(Loggable l);
}
