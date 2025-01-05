package army;

import java.util.*;

// Classe abstraite représentant une unité
abstract class Unite {
    String nom;
    int cout;

    public Unite(String nom, int cout) {
        this.nom = nom;
        this.cout = cout;
    }

    public abstract String getDetails();
}

// Classe pour les unités d'infanterie
class Infanterie extends Unite {
    String type; // Soldat, Lourd, Spécial, Chef
    int nombre;
    private static final String[] TYPES_VALIDES = {"Soldat", "Lourd", "Spécial", "Chef"};

    public Infanterie(String nom, int cout, String type, int nombre) {
        super(nom, cout);
        if (!isTypeValide(type)) {
            throw new IllegalArgumentException("Type d'infanterie invalide. Types possibles: Soldat, Lourd, Spécial, Chef.");
        }
        this.type = type;
        this.nombre = nombre;
    }

    private boolean isTypeValide(String type) {
        for (String t : TYPES_VALIDES) {
            if (t.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDetails() {
        return "Infanterie : " + type + " - " + nom + " (" + nombre + " x " + cout + " pts, total: " + (nombre * cout) + " pts)";
    }
}

// Classe pour les véhicules
class Vehicule extends Unite {
    String role; // Transport ou Attaque
    int capacite; // Capacité de transport (0 si rôle = Attaque)
    int nombre;
    private static final String[] ROLES_VALIDES = {"Transport", "Attaque"};

    public Vehicule(String nom, int cout, String role, int capacite, int nombre) {
        super(nom, cout);
        if (!isRoleValide(role)) {
            throw new IllegalArgumentException("Rôle de véhicule invalide. Rôles possibles: Transport, Attaque.");
        }
        this.role = role;
        this.capacite = role.equalsIgnoreCase("Transport") ? capacite : 0;
        this.nombre = nombre;
    }

    private boolean isRoleValide(String role) {
        for (String r : ROLES_VALIDES) {
            if (r.equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDetails() {
        if (role.equalsIgnoreCase("Transport")) {
            return "Véhicule : " + role + " - " + nom + " (" + nombre + " x " + cout + " pts, capacité: " + capacite + ", total: " + (nombre * cout) + " pts)";
        } else {
            return "Véhicule : " + role + " - " + nom + " (" + nombre + " x " + cout + " pts, total: " + (nombre * cout) + " pts)";
        }
    }
}

// Classe représentant un groupe d'unités
class Groupe {
    String nom;
    List<Unite> unites;

    public Groupe(String nom) {
        this.nom = nom;
        this.unites = new ArrayList<>();
    }

    public void ajouterUnite(Unite unite) {
        unites.add(unite);
    }

    public void supprimerUnite(String nomUnite) {
        unites.removeIf(unite -> unite.nom.equals(nomUnite));
    }

    public int calculerPointsTotal() {
        return unites.stream().mapToInt(unite -> {
            if (unite instanceof Infanterie) {
                return ((Infanterie) unite).nombre * unite.cout;
            } else if (unite instanceof Vehicule) {
                return ((Vehicule) unite).nombre * unite.cout;
            }
            return unite.cout;
        }).sum();
    }

    public void afficherGroupe() {
        System.out.println("  Groupe: " + nom);
        for (Unite unite : unites) {
            System.out.println("    " + unite.getDetails());
        }
    }
}

// Classe représentant une armée
class Armee {
    String nom;
    String faction;
    private int pointsMax;
    List<Groupe> groupes;
    private static final String[] FACTIONS_VALIDES = {"Space Marines", "Orks", "Eldars", "Tau", "Necrons"};

    public Armee(String nom, String faction, int pointsMax) {
        if (!isFactionValide(faction)) {
            throw new IllegalArgumentException("Faction invalide : \"" + faction + "\". Factions possibles : Space Marines, Orks, Eldars, Tau, Necrons.");
        }
        this.nom = nom;
        this.faction = faction;
        this.pointsMax = pointsMax;
        this.groupes = new ArrayList<>();
    }

    private boolean isFactionValide(String faction) {
        String cleanedFaction = faction.trim().toLowerCase();
        for (String f : FACTIONS_VALIDES) {
            if (f.toLowerCase().equals(cleanedFaction)) {
                return true;
            }
        }
        return false;
    }

    public int getPointsMax() {
        return pointsMax;
    }

    public void ajouterGroupe(Groupe groupe) {
        groupes.add(groupe);
    }

    public void supprimerGroupe(String nomGroupe) {
        groupes.removeIf(groupe -> groupe.nom.equals(nomGroupe));
    }

    public int calculerPointsTotal() {
        return groupes.stream()
                .mapToInt(Groupe::calculerPointsTotal)
                .sum();
    }

    public void afficherArmee() {
        System.out.println("Armée: " + nom + " | Faction: " + faction + " | Points Max: " + pointsMax);
        for (Groupe groupe : groupes) {
            groupe.afficherGroupe();
        }
        System.out.println("Points Totals: " + calculerPointsTotal());
    }
}

// Classe principale pour l'interaction avec l'utilisateur
public class ArmyPlanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Création de l'armée
        System.out.println("Factions disponibles: Space Marines, Orks, Eldars, Tau, Necrons");
        System.out.print("Nom de l'armée: ");
        String nomArmee = scanner.nextLine();
        System.out.print("Faction: ");
        String faction = scanner.nextLine();
        System.out.print("Points maximum: ");
        int pointsMax = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Armee armee = new Armee(nomArmee, faction, pointsMax);

        // Boucle principale
        while (true) {
            System.out.println("\n1. Ajouter Groupe");
            System.out.println("2. Supprimer Groupe");
            System.out.println("3. Ajouter Unité");
            System.out.println("4. Supprimer Unité");
            System.out.println("5. Afficher Armée");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option: ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    System.out.print("Nom du groupe: ");
                    String nomGroupe = scanner.nextLine();
                    armee.ajouterGroupe(new Groupe(nomGroupe));
                    break;
                case 2:
                    System.out.print("Nom du groupe à supprimer: ");
                    String nomGroupeSuppr = scanner.nextLine();
                    armee.supprimerGroupe(nomGroupeSuppr);
                    break;
                case 3:
                    System.out.print("Nom du groupe pour l'unité: ");
                    String nomGroupeUnite = scanner.nextLine();
                    Groupe groupe = armee.groupes.stream()
                            .filter(g -> g.nom.equals(nomGroupeUnite))
                            .findFirst()
                            .orElse(null);
                    if (groupe != null) {
                        System.out.println("1. Ajouter Infanterie");
                        System.out.println("2. Ajouter Véhicule");
                        int typeUnite = scanner.nextInt();
                        scanner.nextLine(); // Consommer la nouvelle ligne

                        switch (typeUnite) {
                            case 1:
                                System.out.println("Types d'infanterie disponibles: Soldat, Lourd, Spécial, Chef");
                                System.out.print("Nom de l'infanterie: ");
                                String nomInfanterie = scanner.nextLine();
                                System.out.print("Coût de l'infanterie: ");
                                int coutInfanterie = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Type (Soldat/Lourd/Spécial/Chef): ");
                                String typeInfanterie = scanner.nextLine();
                                System.out.print("Nombre d'unités: ");
                                int nombreInfanterie = scanner.nextInt();
                                scanner.nextLine();
                                if (armee.calculerPointsTotal() + (coutInfanterie * nombreInfanterie) <= armee.getPointsMax()) {
                                    try {
                                        groupe.ajouterUnite(new Infanterie(nomInfanterie, coutInfanterie, typeInfanterie, nombreInfanterie));
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } else {
                                    System.out.println("Impossible d'ajouter l'unité : dépassement des points maximum.");
                                }
                                break;
                            case 2:
                                System.out.println("Rôles de véhicule disponibles: Transport, Attaque");
                                System.out.print("Nom du véhicule: ");
                                String nomVehicule = scanner.nextLine();
                                System.out.print("Coût du véhicule: ");
                                int coutVehicule = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Rôle (Transport/Attaque): ");
                                String roleVehicule = scanner.nextLine();
                                int capacite = 0;
                                if (roleVehicule.equalsIgnoreCase("Transport")) {
                                    System.out.print("Capacité de transport: ");
                                    capacite = scanner.nextInt();
                                    scanner.nextLine();
                                }
                                System.out.print("Nombre de véhicules: ");
                                int nombreVehicules = scanner.nextInt();
                                scanner.nextLine();
                                if (armee.calculerPointsTotal() + (coutVehicule * nombreVehicules) <= armee.getPointsMax()) {
                                    try {
                                        groupe.ajouterUnite(new Vehicule(nomVehicule, coutVehicule, roleVehicule, capacite, nombreVehicules));
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } else {
                                    System.out.println("Impossible d'ajouter le véhicule : dépassement des points maximum.");
                                }
                                break;
                            default:
                                System.out.println("Type d'unité invalide.");
                        }
                    } else {
                        System.out.println("Groupe non trouvé.");
                    }
                    break;
                case 4:
                    System.out.print("Nom du groupe de l'unité: ");
                    String nomGroupeUniteSuppr = scanner.nextLine();
                    Groupe groupeSuppr = armee.groupes.stream()
                            .filter(g -> g.nom.equals(nomGroupeUniteSuppr))
                            .findFirst()
                            .orElse(null);
                    if (groupeSuppr != null) {
                        System.out.print("Nom de l'unité à supprimer: ");
                        String nomUniteSuppr = scanner.nextLine();
                        groupeSuppr.supprimerUnite(nomUniteSuppr);
                    } else {
                        System.out.println("Groupe non trouvé.");
                    }
                    break;
                case 5:
                    armee.afficherArmee();
                    break;
                case 6:
                    System.out.println("Au revoir !");
                    scanner.close();
                    return;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }
}
