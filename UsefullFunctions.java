import my.Component;
import my.Resource;
import my.build.*;
import my.fuel.Fuel;
import my.fuel.ResourceFuel;
import my.recipe.Recipe;
import my.recipe.RecipeOut;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class UsefullFunctions {

    private static final String FILENAME = "src\\data.xml";

    private static final ArrayList<Component> components = new ArrayList<Component>();

    private static final ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    private static final ArrayList<Resource> ressources = new ArrayList<Resource>();

    private static final ArrayList<Building> buildings = new ArrayList<Building>();

    private static final ArrayList<Component> composantsNonBatiment = new ArrayList<Component>();

    private static final ArrayList<Fuel> fuels = new ArrayList<Fuel>();

    private static final ArrayList<ResourceFuel> resourceFuels = new ArrayList<ResourceFuel>();

    private UsefullFunctions(){};

    public static void xmlComponentReader() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            //On parcourt tous les composants du fichier
            NodeList list = doc.getElementsByTagName("items");

            //Pour chaque composant...
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    //On regarde le nom et la categorie du composant
                    Element element = (Element) node;
                    //Quand le tag lu est unique pour l'objet, on peut faire ainsi
                    String category = element.getElementsByTagName("category").item(0).getTextContent();
                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    NodeList fuel = element.getElementsByTagName("fuel");
                    Element fuelEl = (Element) fuel.item(0);

                    // ici pour la category components et fuel
                    if(category.equals("components")) {
                        if(fuel.getLength() != 0) {
                            //Permet de savoir si c'est un fuel et de l'integrer au tableau sous fuel transtyper en component
                            components.add((Component) new Fuel(id, name,
                                    fuelEl.getElementsByTagName("category").item(0).getTextContent(),
                                    Double.parseDouble(fuelEl.getElementsByTagName("value").item(0).getTextContent()))
                            );
                        } else {
                            components.add(new Component(id, name));
                        }
                    }

                    //ici pour la category building et les autres classes qui heritent de building
                    if(category.equals("buildings")) {
                        Element factory = (Element) element.getElementsByTagName("factory").item(0);
                        Element mining = (Element) element.getElementsByTagName("mining").item(0);

                        //pour les ectarcteurs
                        if(!Objects.isNull(factory) && !Objects.isNull(mining)) {
                            //pour ajouter les extracteurs
                            components.add((Component) new Extractor(id, name,
                                    Double.parseDouble(mining.getElementsByTagName("speed").item(0).getTextContent()),
                                    factory.getElementsByTagName("type").item(0).getTextContent(),
                                    Double.parseDouble(factory.getElementsByTagName("usage").item(0).getTextContent()),
                                    Double.parseDouble(factory.getElementsByTagName("drain").item(0).getTextContent()))
                            );
                        }
                        if(Objects.isNull(factory) && !Objects.isNull(mining)) {
                            //pour ajouter les extracteurs qui ne consomment pas l'electricité
                            components.add((Component) new ExtractorWithoutElec(id, name,
                                    Double.parseDouble(mining.getElementsByTagName("speed").item(0).getTextContent()))
                            );
                        }
                        //pour les usines et les centrals
                        if(!Objects.isNull(factory) && Objects.isNull(mining)) {

                            Node centralCategory = factory.getElementsByTagName("category").item(0);
                            Node centralValue = factory.getElementsByTagName("value").item(0);
                            String type = factory.getElementsByTagName("type").item(0).getTextContent();
                            Node usage = factory.getElementsByTagName("usage").item(0);
                            Node drain =  factory.getElementsByTagName("drain").item(0);
                            //centralCarburant
                            if(type.equals("burner")) {
                                if(Objects.isNull(factory.getElementsByTagName("speed").item(0))) { // la valeur de speed est par defaut
                                    components.add((Component) new CentralCarburant(id, name,
                                            type,
                                            Double.parseDouble(centralValue.getTextContent()),
                                            centralCategory.getTextContent(),
                                            1
                                    ));
                                } else { //la valeur de speed
                                    components.add((Component) new CentralCarburant(id, name,
                                            type,
                                            Double.parseDouble(centralValue.getTextContent()),
                                            centralCategory.getTextContent(),
                                            Double.parseDouble(factory.getElementsByTagName("speed").item(0).getTextContent())
                                    ));
                                }
                            }
                            //centralRenouvelable
                            if(type.equals("electric-production")) {
                                components.add((Component) new CentralRenouvelable(id, name,
                                        type,
                                        Double.parseDouble(centralValue.getTextContent())
                                ));
                            }

                            if(!Objects.isNull(usage) && !Objects.isNull(drain)) {
                                Node speed = factory.getElementsByTagName("speed").item(0);
                                if(Objects.isNull(speed)) {
                                    components.add((Component) new Factory(id, name,
                                            1,
                                            type,
                                            Double.parseDouble(usage.getTextContent()),
                                            Double.parseDouble(drain.getTextContent())
                                    ));
                                } else {
                                    components.add((Component) new Factory(id, name,
                                            Double.parseDouble(speed.getTextContent()),
                                            type,
                                            Double.parseDouble(usage.getTextContent()),
                                            Double.parseDouble(drain.getTextContent())
                                    ));
                                }

                            }

                        } else { //pour un batiment simple
                            components.add((Component) new Building(id, name));
                        }
                    }

                    // ici pour la category resource et resourceFuel
                    if(category.equals("resource")) {
                        //Si l'objet est une ressource, on regarde qui permet de miner cette ressource
                        //Quand l'objet peut contenir plusieurs fois le meme tag, on fait ainsi
                        NodeList l = element.getElementsByTagName("minedby");
                        String minedBys[] =new String[l.getLength()];

                        for (int i = 0; i < l.getLength(); i++) {
                            minedBys[i] = l.item(i).getTextContent();
                        }

                        if(fuel.getLength() != 0) {
                            components.add((Component) new ResourceFuel(id, name, minedBys,
                                    fuelEl.getElementsByTagName("category").item(0).getTextContent(),
                                    Double.parseDouble(fuelEl.getElementsByTagName("value").item(0).getTextContent())));
                        } else {
                            //C'est un resource simple
                            components.add((Component) new Resource(id, name, minedBys[0]));
                        }
                    }

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public static void xmlRecipeReader() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            //On parcourt tous les composants du fichier
            NodeList list = doc.getElementsByTagName("recipes");

            //Pour chaque composant...
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    //On regarde le nom et la categorie du composant
                    Element element = (Element) node;
                    //Quand le tag lu est unique pour l'objet, on peut faire ainsi
                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String producers = element.getElementsByTagName("producers").item(0).getTextContent();

                    Double time = Double.parseDouble(element.getElementsByTagName("time").item(0).getTextContent());
                    Element input = (Element) element.getElementsByTagName("in").item(0);

                    NodeList liste_ingredient = input.getElementsByTagName("*");
                    String ingredient[] = new String[liste_ingredient.getLength()];
                    double quantity[] = new double[liste_ingredient.getLength()];

                    for(int i=0; i<liste_ingredient.getLength(); i++) {
                        /*On utilise la variable e pour recuperer le nom du tag (qui est le nom de l'item),
                        et la variable input pour recuperer la quantite une fois qu'on connait le nom du tag
                         */
                        Element e = (Element)liste_ingredient.item(i);
                        String id_ingred = e.getNodeName();
                        ingredient[i] = id_ingred;
                        quantity[i] = (Double.parseDouble(input.getElementsByTagName(id_ingred).item(0).getTextContent()));
                    }

                    Element output = (Element) element.getElementsByTagName("out").item(0);

                    if(output != null) {
                        NodeList liste_out = output.getElementsByTagName("*");
                        String out[] = new String[liste_out.getLength()];
                        double outQuantity[] = new double[liste_out.getLength()];

                        for(int i=0; i<liste_out.getLength(); i++) {
                            /*On utilise la variable e pour recuperer le nom du tag (qui est le nom de l'item),
                            et la variable input pour recuperer la quantite une fois qu'on connait le nom du tag
                             */
                            Element e = (Element)liste_out.item(i);
                            String id_out = e.getNodeName();
                            out[i] = id_out;
                            outQuantity[i] = Double.parseDouble(output.getElementsByTagName(id_out).item(0).getTextContent());
                        }

                        recipes.add( (Recipe) new RecipeOut( id, name, ingredient, quantity, time.doubleValue(), producers, out, outQuantity));
                    } else {
                        recipes.add( new Recipe( id, name, ingredient, quantity, time.doubleValue(), producers));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void distributionCategories(){
        for(Component c : components){
            if( c instanceof Building) {
                buildings.add((Building) c);
                continue;
            }
            if( c instanceof Resource) ressources.add((Resource)c);
            if( c instanceof Fuel) fuels.add((Fuel)c);
            if( c instanceof ResourceFuel) resourceFuels.add((ResourceFuel)c);
            composantsNonBatiment.add(c);
        }
    }

    public static Component getComponentById(String id){
        try {
            for( Component c : composantsNonBatiment ) {
                if(c.getId().equals(id) ) {
                    return c;
                }
            }
            throw new Exception("il y'a pas un element composant avec cet id");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Resource getResourceById(String id){
        try {
            for( Resource r : ressources ) {
                if(r.getId().equals(id) ) {
                    return r;
                }
            }
            throw new Exception("il y'a pas un element Ressource avec cet id");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Building getBuildingById(String id){
        try {
            for( Building b : buildings ) {
                if(b.getId().equals(id) ) {
                    return b;
                }
            }
            throw new Exception("il y'a pas un element batiment avec cet id");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


    public static Building getFactoryById(String id){
        try {
            for( Building b : buildings) {
                if((b instanceof Factory) && b.getId().equals(id) ) {
                    return b;
                }
            }
            throw new Exception("il y'a pas un element factory avec cet id");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Building getExtractorById(String id) {
        try {
            for( Building b : buildings){
                if((b instanceof Extractor || b instanceof ExtractorWithoutElec) && b.getId().equals(id) ) {
                    return b;
                }
            }
            throw new Exception("il y'a pas un element extractor or extractorWithoutElec avec cet id");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Building getCentralCarburantById(String id){
        try {
            for( Building b : buildings) {
                if((b instanceof CentralCarburant ) && b.getId().equals(id) ) {
                    return b;
                }
            }
            throw new Exception("il y'a pas un element CentralCarburant avec cet id");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Recipe getRecipeById(String id) {//affiche les carburant qu'on peut associer a ce centralCarburant
        try {
            for( Recipe recipe : recipes) {
                if(recipe.getId().equals(id)) return recipe;
            }
            throw new Exception("il y'a pas une recette avec cet id");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static void affichageComposantsNonBatiment() {
        System.out.println("-------------ComponentsWithoutBuildings");

        //cette partie permet de trier le tableau
        Collections.sort(composantsNonBatiment , new Comparator<Component>() {
            public int compare(Component c1, Component c2){
                return c1.getId().compareTo(c2.getId());
            }
        });
        //fin
        for(Component c : composantsNonBatiment) {
            System.out.println(c.getId());
        }
    }
    public static void affichageBatiments() {
        System.out.println("-------------Buildings");

        //cette partie permet de trier le tableau
        Collections.sort(buildings , new Comparator<Building>() {
            public int compare(Building b1, Building b2){
                return b1.getId().compareTo(b2.getId());
            }
        });
        //fin
        for(Building b : buildings) {
            System.out.println(b);
        }
    }

    public static void affihcageRecettes() {
        System.out.println("-------------recipes");
        Collections.sort(recipes , new Comparator<Recipe>() {
            public int compare(Recipe r1, Recipe r2){
                return r1.getId().compareTo(r2.getId());
            }
        });

        for(Recipe r : recipes) {
            System.out.println(r.getId());
        }
    }

    public static void usineRecettes(Building f) {// ce code permet d'afficher les recettes que le batiment peut produire
        System.out.println("---------------"+f.getName());
        for(Recipe r : recipes) {
            if(r.getProducers().equals(f.getId())) {
                System.out.println(r);
            }
        }
    }

    public static void extracteurRessources(Building e) {// affiche les resource extraitent par l'extracteur
        System.out.println("---------------Extracteur:"+e.getName()+" ressources qu'il peut extraire:");
        for(Resource r : ressources) {
            if(r.getMinedBy().contains(e.getId())) {
                System.out.println(r);
            }
        }
    }

    public static void centralCarburantFuel(Building c) {//affiche les carburant qu'on peut associer a ce centralCarburant
        System.out.println("---------------"+c.getName());
        for(Component f : composantsNonBatiment) {
            if( f instanceof ResourceFuel && ((ResourceFuel) f).getCategory().equals(((CentralCarburant) c).getCategory()) ) {
                System.out.println(f);
            }

            if( f instanceof Fuel && ((Fuel) f).getCategory().equals(((CentralCarburant) c).getCategory()) ) {
                System.out.println(f);
            }
        }
    }


    //permet de savoir les ressources necessaires pour creer un composant
    public static ArrayList<String> howToCreateComponent(Component c){
        ArrayList<String> resultat = new ArrayList<String>();
        ArrayList<String> aEnlever = new ArrayList<String>();
        ArrayList<String> alt = new ArrayList<String>();

        for (Recipe r : recipes) {
            if (r instanceof Recipe && r.getId().equals(c.getId())) {
                resultat.addAll(r.getIngredient());
                break;
            }

            if (r instanceof RecipeOut && ((RecipeOut) r).getOut().contains(c.getId())) {
                resultat.addAll(r.getIngredient());
                break;
            }
        }

        for (String id : resultat) {
            for (Component cp : composantsNonBatiment) {
                if ( !(cp instanceof Resource) && id.equals(cp.getId()) ) {
                    alt.addAll(howToCreateComponent(cp));
                    aEnlever.add(id);
                }
            }
        }

        for (String al : alt) {
            if(!resultat.contains(al)) {
                resultat.add(al);
            }
        }

        resultat.removeAll(aEnlever);

        return resultat;
    }

    public static ArrayList<String> getRecetteResources(Recipe r){
        ArrayList<String> resultat1 = new ArrayList<String>();
        ArrayList<String> resultat2 = new ArrayList<String>();
        ArrayList<String> alt = new ArrayList<String>();
        for(String rs : r.getIngredient()) {
            for(Component c : components) {
                if(rs.equals(c.getId()) && !(c instanceof Resource)) {
                    resultat1.addAll(howToCreateComponent(c));
                }
                if(rs.equals(c.getId()) && (c instanceof Resource)) {
                    resultat1.add(rs);
                }
            }
        }
        for(String id : resultat1){
            if(!alt.contains(id)) {
                alt.add(id);
                resultat2.add(id);
            }
        }

        return resultat2;
    }
    public static double consommationTotaleRecette(Recipe r){
        ArrayList<String> ressources = getRecetteResources(r);
        double consommation = 0;
        for(String id : ressources) {
            consommation +=((Factory)getFactoryById(getResourceById(id).getMinedBy().get(0))).getUsage();
        }
        return consommation;
    }


}
