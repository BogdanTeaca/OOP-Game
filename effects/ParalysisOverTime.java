package effects;

/**
 * Clasa pentru efectul over-time al spell-ului Paralysis.
 *
 * @author TEACA BOGDAN
 */
public class ParalysisOverTime extends EffectOverTime {
    private final int damageBase = 40;
    private final int damagePerLevel = 10;

    private static final int DURATION_DEFAULT = 3;
    private static final int DURATION_WOODS = 6;

    /**
     * Constructor ce initializeaza proprietatile efectului over-time al spell-ului Paralysis.
     */
    public ParalysisOverTime(final int level, final float victimModif, final float landModifier) {
        this.damageOverTime = Math.round(victimModif * landModifier
                * (damageBase + level * damagePerLevel));

        this.canMove = false;

        if (landModifier == 1.0f) {
            this.durationRounds = DURATION_DEFAULT;
        } else {
            this.durationRounds = DURATION_WOODS;
        }
    }
}
