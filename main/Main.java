package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fileio.FileSystem;
import heroes.Hero;
import heroes.Knight;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.Wizard;

/**
 * Clasa ce contine implementarea metodei main.
 *
 * @author TEACA BOGDAN
 */
public final class Main {
    public static void main(final String[] args) {
        try {
            // Initializam lista de eroi

            List<Hero> heroes = new ArrayList<Hero>();

            /* Folosim API-ul pus la dispozitie pe pagina cu enuntul temei;
             * args[0] este numele fisierului de intrare;
             * args[1] este numele fisierului de iesire;
             */

            FileSystem fileSystem = new FileSystem(args[0], args[1]);

            // Citim dimensiunile terenului

            int n = fileSystem.nextInt();
            int m = fileSystem.nextInt();

            // Cream o matrice ce contine tipurile de land din joc

            LandType[][] map = new LandType[n][m];

            for (int i = 0; i < n; i++) {
                String landLine = fileSystem.nextWord();

                for (int j = 0; j < m; j++) {
                    char landChar = landLine.charAt(j);

                    switch (landChar) {
                    case 'W':
                        map[i][j] = LandType.WOODS;
                        break;
                    case 'L':
                        map[i][j] = LandType.LAND;
                        break;
                    case 'V':
                        map[i][j] = LandType.VOLCANIC;
                        break;
                    case 'D':
                        map[i][j] = LandType.DESERT;
                        break;
                    default:
                        break;
                    }
                }
            }

            // Punem in eroii in lista si ii plasam in locatiile initiale pe harta

            int p = fileSystem.nextInt();

            for (int i = 0; i < p; i++) {
                String heroType = fileSystem.nextWord();

                int row = fileSystem.nextInt();
                int col = fileSystem.nextInt();

                switch (heroType) {
                case "R":
                    heroes.add(new Rogue(row, col, map[row][col]));
                    break;
                case "W":
                    heroes.add(new Wizard(row, col, map[row][col]));
                    break;
                case "P":
                    heroes.add(new Pyromancer(row, col, map[row][col]));
                    break;
                case "K":
                    heroes.add(new Knight(row, col, map[row][col]));
                    break;
                default:
                    break;
                }
            }

            /* Mutam eroii pe harta conform datelor din fisier, avand in vedere ca damage-ul de
             * la effectele over-time trebuie aplicat inainte de vreo lupta, deoarece daca un
             * erou moare din cauza unui damage over-time, oponentul nu castiga experience, iar
             * lupta nu mai are loc.
             */

            int r = fileSystem.nextInt();

            for (int i = 0; i < r; i++) {
                String moveDirection = fileSystem.nextWord();

                for (Hero hero : heroes) {
                    hero.applyEffectsOverTime();
                }

                for (int j = 0; j < p; j++) {
                    heroes.get(j).move(map, moveDirection.charAt(j));
                }

                for (int j = 0; j < p; j++) {
                    for (int k = 0; k < p; k++) {
                        if (j < k) {
                            Hero hero1 = heroes.get(j);
                            Hero hero2 = heroes.get(k);

                            if (!hero1.getIsDead() && !hero2.getIsDead()) {
                                int row1 = hero1.getRow();
                                int row2 = hero2.getRow();
                                int col1 = hero1.getColumn();
                                int col2 = hero2.getColumn();

                                /* Daca eroii sunt in viata si se afla pe acelasi patratel,
                                atunci se vor lupta */

                                if (row1 == row2 && col1 == col2) {
                                    hero1.getPrimarySpell().castSpell(hero2, map[row1][col1]);
                                    hero1.getSecondarySpell().castSpell(hero2, map[row1][col1]);

                                    hero2.getPrimarySpell().castSpell(hero1, map[row1][col1]);
                                    hero2.getSecondarySpell().castSpell(hero1, map[row1][col1]);

                                    hero1.increaseCurrentAttack();
                                    hero2.increaseCurrentAttack();

                                    if (hero1.getIsDead() && !hero2.getIsDead()) {
                                        hero2.gainExperience(hero1);
                                    }

                                    if (hero2.getIsDead() && !hero1.getIsDead()) {
                                        hero1.gainExperience(hero2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Scriem in fisierul de output proprietatile finale ale eroilor

            for (int i = 0; i < p; i++) {
                fileSystem.writeWord(heroes.get(i).printStatus());
                fileSystem.writeNewLine();
            }

            // Inchidem fisierul de input si cel de output

            fileSystem.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Main() { }
}
