package effects;

/**
 * Clasa pentru efectul over-time al spell-ului Ignite.
 *
 * @author TEACA BOGDAN
 */
public class IgniteOverTime extends EffectOverTime {
    private final int damageBase = 50;
    private final int damagePerLevel = 30;

    /**
     * Constructor ce initializeaza proprietatile efectului over-time al spell-ului Ignite.
     */
    public IgniteOverTime(final int level, final float victimModifier, final float landModifier) {
        this.damageOverTime = Math.round(victimModifier * landModifier
                * (damageBase + level * damagePerLevel));

        this.canMove = true;
        this.durationRounds = 2;
    }
}
