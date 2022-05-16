import my.Component;
import my.Resource;
import my.ConsumptionException;
import my.incompatibleCategotyException;
import my.build.*;

import java.util.ArrayList;
import java.util.Scanner;


public class MainJeu {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        UsefullFunctions.xmlComponentReader();
        UsefullFunctions.xmlRecipeReader();
        UsefullFunctions.distributionCategories();

        int option1 = 1;
        int option2;
        String id;

        while( option1 != 0) {
            System.out.println("0: Exit\n1: composants non batiment\n2: batiments\n3: recettes");
            option1 = scanner.nextInt();
            if(option1 == 1) {
                System.out.println("1: affichage de tous les composants non batimen\n2: affichage detaille pour une composante\n3: affichage les ressources necessaires pour avoir un composant");
                option2 = scanner.nextInt();
                switch(option2) {
                    case 1:
                        UsefullFunctions.affichageComposantsNonBatiment();
                        break;
                    case 2:
                        scanner.nextLine();
                        System.out.println("donner l'id du composant non batiment");
                        id = scanner.nextLine();
                        System.out.println(UsefullFunctions.getComponentById(id).toString());
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("donner l'id du composant non batiment");
                        id = scanner.nextLine();
                        System.out.println("les resources necessaires pour creer " + id);
                        for (String r : UsefullFunctions.howToCreateComponent(UsefullFunctions.getComponentById(id))) {
                            System.out.println(r);
                        }
                        break;
                    default:
                        continue;
                }
            }
            if(option1 == 2) {
                System.out.println("1: affichage de tous les batimens\n2: affichage des recettes qu'un usine peut produire\n3: les resources qu'un extractor peut extraire\n4: affichage des carburant qu'on peut associer a un centralCarburant\n5: affichage detaille d'un batiment");
                option2 = scanner.nextInt();
                switch(option2) {
                    case 1:
                        UsefullFunctions.affichageBatiments();
                        break;
                    case 2:
                        scanner.nextLine();
                        System.out.println("donner l'id d'un usine");
                        id = scanner.nextLine();
                        UsefullFunctions.usineRecettes(UsefullFunctions.getFactoryById(id));
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("donner l'id d'un extracteur");
                        id = scanner.nextLine();
                        UsefullFunctions.extracteurRessources(UsefullFunctions.getExtractorById(id));
                        break;
                    case 4:
                        scanner.nextLine();
                        System.out.println("donner l'id d'un centralCarburant");
                        id = scanner.nextLine();
                        UsefullFunctions.centralCarburantFuel(UsefullFunctions.getCentralCarburantById(id));
                        break;
                    case 5:
                        scanner.nextLine();
                        System.out.println("donner l'id du composant non batiment");
                        id = scanner.nextLine();
                        System.out.println(UsefullFunctions.getBuildingById(id).toString());
                        break;
                    default:
                        continue;
                }
            }
            if(option1 == 3) {
                System.out.println("1: affichage des recettes\n2: les ressources necessaires pour une recette\n3: affichage detaille d'une recette\n4: la consommation electrique totale requise par une recette");
                option2 = scanner.nextInt();

                switch (option2){
                    case 1:
                        UsefullFunctions.affihcageRecettes();
                        break;
                    case 2:
                        scanner.nextLine();
                        System.out.println("donner l'id d'une recette");
                        id = scanner.nextLine();
                        System.out.println("les resources necessaire pour la recette *** "+id+" ***");
                        for(String res : UsefullFunctions.getRecetteResources(UsefullFunctions.getRecipeById(id))){
                            System.out.println("-> "+res);
                        };
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("donner l'id d'une recette");
                        id = scanner.nextLine();
                        System.out.println(UsefullFunctions.getRecipeById(id).toString());
                        break;
                    case 4:
                        scanner.nextLine();
                        System.out.println("donner l'id d'une recette");
                        id = scanner.nextLine();
                        System.out.print("la consommation est :");
                        System.out.println( UsefullFunctions.consommationTotaleRecette(UsefullFunctions.getRecipeById(id)) );
                        break;
                    default:
                        continue;
                }
            }
        }

        return;
    }




}

