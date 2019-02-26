package effects;

/**
 * Clasa abstracta ce va fi mostenita de toate efectele over-time ale spell-urilor.
 *
 * @author TEACA BOGDAN
 */
public abstract class EffectOverTime {
    /**
     * Numarul de runde pana cand efectul dispare.
     */
    protected int durationRounds;

    /**
     * Cantitatea de damage suferita de erou cat timp efectul tine.
     */
    protected int damageOverTime;

    /**
     * Descrie daca eroul se poate misca atata timp cat efectul tine.
     */
    protected boolean canMove;

    public EffectOverTime() { }

    /**
     * Metoda descreste durata efectului. Atunci cand durata efectului ajunge la 0, se considera
     * ca efectul a disparut, iar eroul nu mai este afectat de acesta.
     */
    public void decreaseDuration() {
        this.durationRounds--;
    }

    /**
     * @return damageOverTime
     */
    public int getDamageOverTime() {
        return damageOverTime;
    }

    /**
     * @return canMove
     */
    public boolean getCanMove() {
        return canMove;
    }

    /**
     * @return getDurationRounds
     */
    public int getDurationRounds() {
        return durationRounds;
    }
}
