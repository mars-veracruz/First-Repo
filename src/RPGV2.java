import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
//Learning how to code
public class RPGV2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();

        String[][] choices = {
                // {parentID, ID, choiceTxt, resultTxt, moveToID }

                {"-1", "0", "", "Du stehst in einer Bar."},


                {"0", "1", "Geh in die Wildnis auf der Suche nach Abenteuern", "Du bist in der Wildnis angekommen. Du triffst auf ein Monster."},

                {"0", "2", "Trink ein Getränk und raste dich aus", "Du lässt dir ein Getränk schmecken", "0"},

                {"0", "3", "Beeindrucke die Prinzessin mit deinen Schätzen", "Du gibst alles!", "0"},


                {"1", "4", "Kämpf gegen das Monster", "", "6"},

                {"1", "5", "Lauf vor dem Monster davon", "Du läufst wie ein Feigling zurück zur Bar.", "0"},


                {"4", "6", "", ""},

                {"6", "7", "Versuche das Monster mit deinem Schwert zu erwischen", "Du wirfst dich in die Schlacht!", "6"},

                {"6", "8", "Es ist Zeit zu gehen", "Du versuchst zu flüchten, doch das Monster verfolgt dich.", "0"},


                {"-1", "9", "", "Deine Zeit ist leider vorüber. Der Tod erwartet dich schon mit freundlichem Grinsen"}, // TOD

                {"-1", "10", "", "Du bist der größte Held aller Zeiten! Hurra!"}, // SIEG

        };

        int input = 0;  //Catches the user input.
        int currentChoice = 0; //Stores the current choices the user made.
        ArrayList<Integer> viableChoices = new ArrayList<>(); //Stores the only choices the user has.
        ArrayList<String> temp = new ArrayList<>(); //Used to check the length of the single arrays inside de 2D array.
        // If the length is less than 5, then no check for 5th element in that array will be performed.
        boolean game = true;  //Used to keep the game running in loop.
        boolean rightInput = false; //Used to keep asking the user for a valid input.
        boolean victory = false;
        int lifeMax = 10;
        int pLife = lifeMax;
        int mLife = r.nextInt(3, 21);
        int pAttack = 0;
        int mAttack = 0;
        int pGold = 0;
        int mGold = r.nextInt(1, mLife * 101);

        System.out.println("""
                ----------------
                *****RPG V2*****
                ----------------
                  Willkommen!""");
        System.out.println();
        do {
            viableChoices.clear();
            temp.clear();
            //Operation fills the list with only available choices
            for (int i = currentChoice; i < choices.length; i++) {
                if (Integer.parseInt(choices[i][0]) == currentChoice) {
                    viableChoices.add(i);
                }
            }
            do {
                if (currentChoice == 6 && mLife < 1) {
                    mLife = r.nextInt(3, 21);
                    mGold = r.nextInt(1, mLife * 101);
                    System.out.println(String.format("Das Monster hat insgesamt %d Leben, und du noch %d", mLife, pLife));
                } else if (currentChoice == 6) {
                    System.out.println(String.format("Das Monster hat insgesamt %d Leben, und du noch %d", mLife, pLife));
                } else {
                    System.out.println(String.format("%s", choices[currentChoice][3]));
                }
                System.out.println("----------------------------");
                for (int viableChoice : viableChoices) {
                    System.out.println(String.format("%s.) %s", choices[viableChoice][1], choices[viableChoice][2]));
                }

                System.out.println("----------------------------");
                System.out.println("Was willst du als nächstes tun? (0 um das Spiel zu beenden)");
                input = sc.nextInt();
                if (input == 0) {
                    System.out.println("Das Spiel wird beendet!");
                    rightInput = true;
                    game = false;
                } else if (viableChoices.contains(input)) {
                    currentChoice = input;
                    rightInput = true;
                } else {
                    System.out.println("Ungültige Eingabe!");
                    System.out.println();
                    rightInput = false;
                }
            } while (!rightInput);
            if (game) {
                if (currentChoice == 2) {
                    if (pGold <= 100) {
                        System.out.println("Du hast nicht genug Gold!");
                        System.out.println("Gold: " + pGold);
                        currentChoice = 0;

                    } else {
                        pGold -= 100;
                        pLife += 3;
                        if (pLife > lifeMax) {
                            pLife = lifeMax;
                        }
                        System.out.printf("Dein Lebensdurst kehrt zurück! +3 Leben, jetzt hast du wieder %d Leben!", pLife);
                        System.out.println();
                    }
                } else if (currentChoice == 3) {
                    System.out.println(choices[currentChoice][3]);
                    if (pGold >= 1000 && lifeMax >= 15) {
                        System.out.println("Die Prinzessin ist total beeindruckt von deinen Schätzen und deiner Lebenserfahrung und heiratet dich auf der Stelle!");
                        currentChoice = 10;
                        System.out.println(choices[currentChoice][3]);
                        System.out.println();
                        System.out.println("""
                                            Credits: Marcelo Souza
                                              Coders.Bay Studios
                                            ------THANK YOU!------""");
                        game = false;
                    } else {
                        System.out.println("Die Prinzessin belächelt dich nur verächtlich. So einen armen Schlucker soll sie heiraten? Und so wenig Lebenserfahrung! Tz tz!");
                        System.out.println();
                        System.out.printf("Da fehlen dir noch sicher %d Gold dafür, Junge! Und %d Jahre Erfahrung!", (1000 - pGold), (15 - lifeMax));
                        System.out.println();
                    }
                } else if (currentChoice == 7) {
                    System.out.println(choices[currentChoice][3]);
                    pAttack = r.nextInt(1, pLife + 1);
                    mLife = mLife - pAttack;
                    if (mLife < 1) {
                        lifeMax++;
                        System.out.println(String.format("Du erwischt das Monster mit %d Schaden. Es hat noch %d Leben.", pAttack, mLife));
                        System.out.println("Du hast das Monster besiegt!");
                        System.out.println("Du gewinnst an Erfahrung und hast jetzt mehr maximale Lebenspunkte");
                        System.out.printf("Du findest in der Nähe des Monsters %d Gold - das du dir natürlich schnappst!", mGold);
                        pGold = mGold;
                        currentChoice = 1;
                        victory = true;
                    } else {
                        System.out.println(String.format("Du erwischt das Monster mit %d Schaden. Es hat noch %d Leben.", pAttack, mLife));
                    }
                    System.out.println();
                    if (!victory) {
                        mAttack = r.nextInt(1, mLife + 1);
                        pLife = pLife - mAttack;
                        if (pLife < 1) {
                            System.out.println(String.format("Das Monster hat dich für %d Schaden erwischt. Du hast noch %d Leben.", mAttack, pLife));
                            currentChoice = 9;
                            System.out.println();
                            System.out.println(String.format("%s", choices[currentChoice][3]));
                            System.out.println();
                            System.out.println("""
                                    Credits: Marcelo Souza
                                      Coders.Bay Studios
                                    -------GAME OVER------""");
                            game = false;
                        } else {
                            System.out.println(String.format("Das Monster hat dich für %d Schaden erwischt. Du hast noch %d Leben.", mAttack, pLife));
                        }
                    } else {
                        victory = false;
                    }
                } else if (currentChoice == 8) {
                    System.out.println(choices[currentChoice][3]);
                    mAttack = r.nextInt(1, mLife + 1);
                    pLife = pLife - mAttack;
                    if (pLife < 1) {
                        System.out.println(String.format("Das Monster hat dich für %d Schaden erwischt. Du hast noch %d Leben.", mAttack, pLife));
                        currentChoice = 9;
                        System.out.println();
                        System.out.println(String.format("%s", choices[currentChoice][3]));
                        System.out.println();
                        System.out.println("""
                                    Credits: Marcelo Souza
                                      Coders.Bay Studios
                                    -------GAME OVER------""");

                        game = false;
                    } else {
                        System.out.println(String.format("Das Monster hat dich für %d Schaden erwischt. Du hast noch %d Leben.", mAttack, pLife));
                        System.out.println();
                    }
                }
                for (int i = 0; i < choices[currentChoice].length; i++) {
                    temp.add(choices[currentChoice][i]);
                }
                if (temp.size() == 5) {
                    currentChoice = Integer.parseInt(choices[currentChoice][4]);
                }
                System.out.println();
            }
        } while (game);
    }
}