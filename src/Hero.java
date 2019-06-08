import java.util.Random;

abstract class Hero {

    protected int health;
    protected String name;
    protected int damage;
    protected int addHeal;
    protected int maxHealth;

    public Hero(int health, int maxHealth, String name, int damage, int addHeal) {
        this.health = health;
        this.name = name;
        this.damage = damage;
        this.addHeal = addHeal;
        this.maxHealth = maxHealth;
    }

    // метод для удара
    abstract void hit(Hero hero);

    // метод для лечения
    abstract void healing(Hero hero);

    // метод для нанесения урона
    void causeDamage(int damage) {
        if (health <= 0) {
            System.out.println("Герой уже мертвый!");
        } else if (health > 0 && damage >= health) {
            health = 0;
        } else if (health > 0 && damage < health) {
            health -= damage;
        }

    }

    public int getHealth() {
        return health;
    }

    void addHealth(int health) {
        this.health += health;
    }

    void info() {

        System.out.println(name + " " + (health <= 0 ? "Герой мертвый" : health) + " " + damage);
    }
}

class Warrior extends Hero {

    public Warrior(int health, int maxHealth, String type, int damage, int addHeal) {
        super(health, maxHealth, type, damage, addHeal);

    }

    @Override
    void hit(Hero hero) {
        if (hero != this) {
            if (health <= 0) {
                System.out.println("Герой погиб и бить не может!");
            } else {
                hero.causeDamage(damage);
                System.out.println(this.name + " нанес урон " + hero.name + " на " + this.damage);
                System.out.println(hero.name + " осталось " + hero.health);
            }

        }
    }

    @Override
    void healing(Hero hero) {
        System.out.println("Войны не умеют лечить!");
    }
}

class Assasin extends Hero {
    // критический урон
    int cricitalHit;
    Random random = new Random();

    public Assasin(int health, int maxHealth, String type, int damage, int addHeal) {
        super(health, maxHealth, type, damage, addHeal);
        this.cricitalHit = random.nextInt(20);
    }

    @Override
    void hit(Hero hero) {
        if (hero != this) {
            if (health <= 0) {
                System.out.println("Герой погиб и бить не может!");
            } else {
                hero.causeDamage(damage + cricitalHit);
                System.out.println(this.name + " нанес урон " + hero.name + " на " + (this.damage + this.cricitalHit));
                System.out.println(hero.name + " осталось " + hero.health);
            }

        }
    }

    @Override
    void healing(Hero hero) {
        System.out.println("Убийцы не умеют лечить!");
    }
}

class Doctor extends Hero {

    public Doctor(int health, int maxHealth, String type, int damage, int addHeal) {
        super(health, maxHealth, type, damage, addHeal);
    }

    @Override
    void hit(Hero hero) {
        System.out.println("Доктор не может бить!");

    }


    @Override
    void healing(Hero hero) {
        if (this.health > 0) {
            hero.addHealth(addHeal);
            System.out.println(this.name + " похилил " + hero.name + " на " + this.addHeal);
            System.out.println(hero.name + " осталось " + hero.health);
        } else {
            System.out.println(this.name + " помер");
        }

    }
}


class Game {

    public boolean checkHealt(Hero[] team) {
        int teamHealh = 0;
        for (Hero h : team) {
            if (h.health <= 0) {
                teamHealh += 0;
            } else {
                teamHealh += h.health;
            }
        }
        if (teamHealh > 0) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {

        Random randomStep = new Random();
        // Random randomHealing = new Random();
        Random randomPlayer = new Random();
        //int round = 3;

        Hero[] team1 = new Hero[]{new Warrior(250, 250, "Варианн", 50, 0)
                , new Assasin(150, 150, "Шандрисса", 40, 0)
                , new Doctor(120, 120, "Бенедикт", 0, 20)};

        Game g1 = new Game();

        Hero[] team2 = new Hero[]{new Warrior(230, 230, "Саурфанг", 45, 0)
                , new Assasin(160, 160, "Гаронна", 55, 0)
                , new Doctor(110, 110, "Бвон`самди", 0, 30)};


        // 3 раунда
        int whoWillTurn = randomPlayer.nextInt(2);

        while (g1.checkHealt(team1) && g1.checkHealt(team2)) {
            for (int i = 0; i < team1.length; i++) {
                if (randomStep.nextInt(2) == 0) {
//                    int whoWillTurn = randomPlayer.nextInt(2);
                    // если доктор то только хилим
                    if (team1[i] instanceof Doctor && team1[i].health > 0) {
                        for (Hero h : team1) {
                            if (h.health < h.maxHealth) {
                                if ((h.health + (team1[i].addHeal)) <= h.maxHealth) {
                                    team1[i].healing(h);
                                    break;
                                } else if ((h.health + (team1[i].addHeal)) > h.maxHealth) {
                                    h.health = h.maxHealth;
                                    break;
                                }
                            }
                        }
                    } else {
                        int whoWasHitting = randomPlayer.nextInt(team2.length);
                        if (team1[i].health > 0) {
                            if (team2[whoWasHitting].health > 0) {
                                team1[i].hit(team2[whoWasHitting]);
                                if (team1[i].damage >= team2[whoWasHitting].health) {
                                    team2[whoWasHitting].health = 0;
                                }
                            } else {
                                team1[i].hit(team2[new Random().nextInt(team2.length)]);
                                if (team1[i].damage >= team2[whoWasHitting].health) {
                                    team2[whoWasHitting].health = 0;
                                }
                            }
                        }

                    }

                } else {
                    if (team2[i] instanceof Doctor && team2[i].health > 0) {
                        for (Hero h : team2) {
                            if (h.health < h.maxHealth) {
                                if ((h.health + (team2[i].addHeal)) <= h.maxHealth) {
                                    team2[i].healing(h);
                                    break;
                                } else if ((h.health + (team2[i].addHeal)) > h.maxHealth) {
                                    h.health = h.maxHealth;
                                    break;
                                }
                            }
                        }
                    } else {
                        int whoWasHitting = randomPlayer.nextInt(team2.length);
                        if (team2[i].health > 0) {
                            if (team1[whoWasHitting].health > 0) {
                                team2[i].hit(team1[whoWasHitting]);
                                if (team2[i].damage >= team1[whoWasHitting].health) {
                                    team1[whoWasHitting].health = 0;
                                }
                            } else {
                                team2[i].hit(team1[new Random().nextInt(team2.length)]);
                                if (team2[i].damage >= team1[whoWasHitting].health) {
                                    team1[whoWasHitting].health = 0;
                                }
                            }
                        } else {
                            team2[new Random().nextInt(team2.length)].hit(team1[whoWasHitting]);
                        }

                    }

                }
            }
        }
        System.out.println("---------------");

        for (Hero t1 : team1) {
            t1.info();
        }

        for (Hero t2 : team2) {
            t2.info();
        }
        if (!g1.checkHealt(team1)) {
            System.out.println("Победила команда 2");
        } else if (!g1.checkHealt(team2)) {
            System.out.println("Победила команда 1");
        }
    }


}