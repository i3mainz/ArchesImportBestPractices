
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CSV to Arches Converter
 *
 * @author florian.thiery
 */
public class ArchesConverter_file {

    private static String csvFile = "in.csv";
    private static String cvsSplitBy = ";";
    private static String outputpath = "out.csv";
    private static int uuid = 10000;
    private static int iterator = 100;
    private static String creator = "unknown";
    private static List<String> list = new ArrayList<String>();

    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to the archesConverter");
        System.out.println("------------------------------");

        try {

            Scanner scanIn = new Scanner(System.in);
            System.out.println("Filepath: ");
            csvFile = scanIn.nextLine();
            System.out.println("Deliminiter: ");
            cvsSplitBy = scanIn.nextLine();
            System.out.println("Outputfile: ");
            outputpath = scanIn.nextLine();
            System.out.println("Creator: ");
            creator = scanIn.nextLine();
            System.out.println("Start UID (5 digits): ");
            String tmp = scanIn.nextLine();
            uuid = Integer.parseInt(tmp);
            System.out.println("Start internal iterator (3 digits): ");
            tmp = scanIn.nextLine();
            iterator = Integer.parseInt(tmp);
            scanIn.close();

            ArchesConverter_file.readCSVfile();
            ArchesConverter_file.writeToArchesFile();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    public static void readCSVfile() throws IOException {

        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(
                    new InputStreamReader(
                    new FileInputStream(csvFile), "UTF-8"));

            int i = 0;
            while ((line = br.readLine()) != null) {

                if (i != 0) {

                    String[] csv = line.split(cvsSplitBy);

                    int uuid2 = uuid + i;
                    int iterator2 = iterator + i;

                    //Write IDs as external refs
                    String id = csv[0];
                    if (!"".equals(id)) {
                        list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|EXTERNAL XREF.E42|" + id + "|MONUMENT:" + iterator2);
                        list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|EXTERNAL XREF TYPE.E55|EXTERNAL_XREF_TYPE_1000|MONUMENT:" + iterator2); //TODO: insert ID
                    }

                    String id_f = csv[1];
                    if (!"".equals(id_f)) {
                        list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|EXTERNAL XREF.E42|" + id_f + "|MONUMENT:" + iterator2);
                        list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|EXTERNAL XREF TYPE.E55|EXTERNAL_XREF_TYPE_1001|MONUMENT:" + iterator2); //TODO: insert ID
                    }

                    String fdstnr_amt = csv[3];
                    if (!"".equals(fdstnr_amt)) {
                        list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|EXTERNAL XREF.E42|" + fdstnr_amt + "|MONUMENT:" + iterator2);
                        list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|EXTERNAL XREF TYPE.E55|EXTERNAL_XREF_TYPE_2000|MONUMENT:" + iterator2); //TODO: insert ID
                    }

                    //Fundstelle
                    String fundstelle = csv[2];
                    fundstelle = fundstelle.replace("ä", "ae");
                    fundstelle = fundstelle.replace("ö", "oe");
                    fundstelle = fundstelle.replace("ü", "ue");
                    fundstelle = fundstelle.replace("ß", "ss");
                    fundstelle = fundstelle.replace("Ä", "Ae");
                    fundstelle = fundstelle.replace("Ö", "Oe");
                    fundstelle = fundstelle.replace("Ü", "Ue");

                    //RESOURCEID|RESOURCETYPE|ATTRIBUTENAME|ATTRIBUTEVALUE|GROUPID
                    String address = uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|ADDRESS_TOWN/CITY.E45|" + fundstelle.toUpperCase() + "|ADDRESS-0";
                    list.add(address);
                    String compiler = uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|COMPILER.E82|" + creator.toUpperCase() + "|COMPILER.E82-0";
                    list.add(compiler);
                    String name1 = uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|NAME TYPE.E55|NAME_TYPE_1|NAME.E41-" + iterator2;
                    list.add(name1);
                    String name2 = uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|NAME.E41|" + fundstelle + "|NAME.E41-" + iterator2;
                    list.add(name2);

                    //Koordinaten
                    String lat = "0";
                    String lon = "0";

                    lat = csv[8];
                    lon = csv[9];

                    lat = lat.replace(",", ".");
                    lon = lon.replace(",", ".");

                    String point = uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|SPATIAL COORDINATES_GEOMETRY.E47|POINT (" + lat + " " + lon + ")|SPATIAL COORDINATES_GEOMETRY.E47-0";
                    list.add(point);

                    //Flurname
//                    String flurname = csv[4];
//                    if (!"".equals(flurname)) {
//                        String cadastral_ref = uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|PLACE_CADASTRAL REFERENCE.E53|" + flurname + "|PLACE_CADASTRAL REFERENCE.E53-0";
//                        list.add(cadastral_ref);
//                    }

                    //Koordinatenqualität
                    int koord_qual = Integer.parseInt(csv[10]);
                    String quality = "EMPTY";
                    switch (koord_qual) {
                        case 0:
                            break;
                        case 1:
                            quality = "GEOMETRY_QUALIFIER_4";   //Literaturangabe
                            break;
                        case 2:
                            quality = "GEOMETRY_QUALIFIER_1000";    //interpoliert
                            break;
                        case 3:
                            quality = "GEOMETRY_QUALIFIER_6002";    //etwas-Angabe
                            break;
                        case 4:
                            quality = "GEOMETRY_QUALIFIER_5000";    //Flurname
                            break;
                        case 5:
                            quality = "GEOMETRY_QUALIFIER_5001";    //Ortsteil
                            break;
                        default:
                            quality = "EMPTY";
                            break;
                    }
                    String geometry_qualifier = uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|GEOMETRY QUALIFIER.E55|" + quality + "|SPATIAL COORDINATES_GEOMETRY.E47-0";
                    list.add(geometry_qualifier);

                    //Untersuchungsniveau
                    int u_niveau = Integer.parseInt(csv[11]);
                    String niveau = "EMPTY";
                    switch (u_niveau) {
                        case 1:
                            niveau = "INVESTIGATION_TYPE_1_1"; //wissenschaftliche Grabung"; 
                            break;
                        case 2:
                            niveau = "INVESTIGATION_TYPE_1_2"; //baubegleitande Grabung";
                            break;
                        case 3:
                            niveau = "INVESTIGATION_TYPE_1"; //Grabung (unbestimmt)";
                            break;
                        case 4:
                            niveau = "INVESTIGATION_TYPE_1_3"; //Notbergung/Sondage";
                            break;
                        case 5:
                            niveau = "INVESTIGATION_TYPE_4_1"; //Laienfund mit Bodeneingriff";
                            break;
                        case 6:
                            niveau = "INVESTIGATION_TYPE_2_1_3"; //Lesefund (systematische Begehung)";
                            break;
                        case 7:
                            niveau = "INVESTIGATION_TYPE_2_1_1"; //Lesefund (Begehung + Bodeneingriff)";
                            break;
                        case 8:
                            niveau = "INVESTIGATION_TYPE_4"; //Zufallsfund";
                            break;
                        case 9:
                            niveau = "INVESTIGATION_TYPE_2_1"; //Lesefund (unbestimmt)";
                            break;
                        default:
                            niveau = "EMPTY";
                            break;
                    }
                    String level = uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|INVESTIGATION TYPE.E55|" + niveau + "|INVESTIGATION TYPE.E55-0";  //INVESTIGATION TYPE (within SITE resource type?)
                    list.add(level);

                    //Befunde
                    int grube = Integer.parseInt(csv[12]);
                    int graben = Integer.parseInt(csv[13]);
                    int palisade = Integer.parseInt(csv[14]);
                    int pfosten = Integer.parseInt(csv[15]);
                    int wall = Integer.parseInt(csv[16]);
                    int schicht = Integer.parseInt(csv[17]);

                    //Funde
                    int keramik = Integer.parseInt(csv[23]);
                    int silex = Integer.parseInt(csv[24]);
                    int felssteingeraete = Integer.parseInt(csv[25]);
                    int huettenlehm = Integer.parseInt(csv[26]);
                    int tierknochen = Integer.parseInt(csv[27]);
                    int menschenknochen = Integer.parseInt(csv[28]);

                    int[] components = {grube, graben, palisade, pfosten, wall, schicht, keramik, silex, felssteingeraete, huettenlehm, tierknochen, menschenknochen};
                    int m = 0;
                    for (int k = 0; k < components.length; k++) {
                        //check if component occurs
                        if (components[k] != 0) {   //or 2, neglect quantity or 3 capture uncertainty

                            String comp_id = "EMPTY";
                            switch (k) {
                                case 0:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_3300"; //"grube";  
                                    break;
                                case 1:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_310012"; //graben";
                                    break;
                                case 2:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_310010"; //palisade";
                                    break;
                                case 3:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_3400"; //pfosten";
                                    break;
                                case 4:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_310011"; //wall";
                                    break;
                                case 5:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_6"; //schicht";
                                    break;
                                case 6:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_1"; //keramik";
                                    break;
                                case 7:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_2200"; //silex";
                                    break;
                                case 8:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_2100"; //felssteingeraete";
                                    break;
                                case 9:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_320010"; //"huettenlehm";
                                    break;
                                case 10:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_5400"; //tierknochen";
                                    break;
                                case 11:
                                    comp_id = "ARCHAEOLOGICAL_COMPONENT_5100"; //menschenknochen";
                                    break;
                                default:
                                    comp_id = "EMPTY";
                                    break;

                            }

                            //assign certainty
                            String certainty = "";
                            if (components[k] == 1) {
                                certainty = "COMPONENT_CERTAINTY_1";   //sicher
                                list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|ARCHAEOLOGICAL COMPONENT TYPE.E55|ARCHAEOLOGICAL_COMPONENT_" + comp_id + "|COMPONENT.E18-" + m);
                                list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|COMPONENT CERTAINTY TYPE.E55|" + certainty + "|COMPONENT.E18-" + m);
                            } else if (components[k] == 2) { //assign quantity (mehrere)
                                String quantity = "mehrere";
                                String measure_type = "COMPONENT_MEASUREMENT_TYPE_2"; //Anzahl (beschreibend)
                                String unit = "UNIT_OF_COMPONENT_MEASUREMENT_TYPE_1";          //Stück                              
                                list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|ARCHAEOLOGICAL COMPONENT TYPE.E55|ARCHAEOLOGICAL_COMPONENT_" + comp_id + "|COMPONENT.E18-" + m);
                                list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|COMPONENT MEASUREMENT TYPE.E55|" + measure_type + "|COMPONENT.E18-" + m);
                                list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|UNIT OF COMPONENT MEASUREMENT.E58|" + unit + "|COMPONENT.E18-" + m);
                                list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|DESCRIPTION OF COMPONENT MEASUREMENT.E62|" + quantity + "|COMPONENT.E18-" + m); //descriptive value
                            } else if (components[k] == 3 || components[k] == 9) { //assign uncertainty
                                certainty = "COMPONENT_CERTAINTY_2"; //unsicher
                                list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|ARCHAEOLOGICAL COMPONENT TYPE.E55|ARCHAEOLOGICAL_COMPONENT_" + comp_id + "|COMPONENT.E18-" + m);
                                list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|COMPONENT CERTAINTY TYPE.E55|" + certainty + "|COMPONENT.E18-" + m);
                            }

                            m++;
                        }
                    }

                    //DONE: replace MK = 0 with MK = 2
                    //Periods
                    int mk = Integer.parseInt(csv[31]);
                    int mk_alt = Integer.parseInt(csv[33]);
                    int mk_mittel = Integer.parseInt(csv[34]);
                    int mk_jung = Integer.parseInt(csv[35]);
                    int mk1 = Integer.parseInt(csv[36]);
                    int mk2 = Integer.parseInt(csv[37]);
                    int mk3 = Integer.parseInt(csv[38]);
                    int mk3a = Integer.parseInt(csv[39]);
                    int mk3b = Integer.parseInt(csv[40]);
                    int mk4 = Integer.parseInt(csv[41]);
                    int mk5 = Integer.parseInt(csv[42]);

                    //Ansprache Fundplatz
                    String ansprache = csv[20];
                    String ansprache_id = "EMPTY";
                    if (ansprache.contains("Einzel")) {
                        ansprache_id = "SITE_7";
                    } else if (ansprache.contains("Fundführende Schicht")) {
                        ansprache_id = "SITE_6000";
                    } else if (ansprache.contains("Fundansammlung indifferent")) {
                        ansprache_id = "SITE_3001";
                    } else if (ansprache.contains("PGIS")) {
                        ansprache_id = "SITE_5000";
                    } else if (ansprache.contains("Erdwerk")) {
                        ansprache_id = "SITE_20001";
                    } else if (ansprache.contains("Erdwerk mit weiteren")) {
                        ansprache_id = "SITE_200011";
                    } else if (ansprache.contains("Gruben")) {
                        ansprache_id = "SITE_2002";
                    }

                    //- FROM/TO DATE??? -> global für Zeit definieren
                    int[] periods = {mk, mk_alt, mk_mittel, mk_jung, mk1, mk2, mk3, mk3a, mk3b, mk4, mk5};
                    int n = 0;
                    for (int k = 0; k < periods.length; k++) {

                        if (periods[k] != 0) {      //or 2, capture uncertainty

                            String period_id = "EMPTY";
                            switch (k) {
                                case 0:
                                    period_id = "PERIOD_1310213";
                                    break;
                                case 1:
                                    period_id = "PERIOD_131021310";
                                    break;
                                case 2:
                                    period_id = "PERIOD_131021311";
                                    break;
                                case 3:
                                    period_id = "PERIOD_131021312";
                                    break;
                                case 4:
                                    period_id = "PERIOD_13102131010";
                                    break;
                                case 5:
                                    period_id = "PERIOD_13102131011";
                                    break;
                                case 6:
                                    period_id = "PERIOD_13102131110";
                                    break;
                                case 7:
                                    period_id = "PERIOD_1310213111100";
                                    break;
                                case 8:
                                    period_id = "PERIOD_1310213111101";
                                    break;
                                case 9:
                                    period_id = "PERIOD_13102131210";
                                    break;
                                case 10:
                                    period_id = "PERIOD_13102131211";
                                    break;
                                default:
                                    period_id = "EMPTY";
                                    break;

                            }

                            //assign certainty
                            String phase_certainty = "";
                            if (periods[k] == 1) {
                                phase_certainty = "PHASE_TYPE_ASSIGNMENT_CERTAINTY_1";   //sicher
                            } else if (periods[k] == 2) {
                                phase_certainty = "PHASE_TYPE_ASSIGNMENT_CERTAINTY_2";   //unsicher
                            }
                            list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|CULTURAL PERIOD.E55|" + period_id + "|PHASE TYPE ASSIGNMENT.E17-" + n);
                            list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|PHASE TYPE ASSIGNMENT CERTAINTY TYPE.E55|" + phase_certainty + "|PHASE TYPE ASSIGNMENT.E17-" + n);
                            list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|ARCHAEOLOGICAL HERITAGE (SITE) TYPE.E55|" + ansprache_id + "|PHASE TYPE ASSIGNMENT.E17-" + n);

                            n++;
                        }
                    }

                    //Datierungsgrundlage
                    int dat_grundlage = Integer.parseInt(csv[43]);
                    String grundlage = "EMPTY";
                    switch (dat_grundlage) {
                        case 1:
                            grundlage = "DATING_2000"; //14C";
                            break;
                        case 2:
                            grundlage = "DATING_10001"; //Keramik";
                            break;
                        case 3:
                            grundlage = "DATING_10002"; //Steingerät";
                            break;
                        default:
                            grundlage = "EMPTY";
                            break;
                    }
                    list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|SCIENTIFIC DATING METHOD.E55|" + grundlage + "|SCIENTIFIC DATING EVENT.E5-0");

                    //Sekundäre Lage, Weiterführende Interpretation, Datierendes Material -> Description
                    int sekund_lage = Integer.parseInt(csv[19]);
                    String lage = "EMPTY";
                    switch (sekund_lage) {
                        case 0:
                            lage = "";
                            break;
                        case 1:
                            lage = "Sekundäre Lage: ja; ";
                            break;
                        case 2:
                            lage = "Sekundäre Lage: vielleicht; ";
                            break;
                        default:
                            lage = "EMPTY";
                            break;
                    }
                    String weit_interpret = "";
                    if (!"".equals(csv[21])) {
                        weit_interpret = "Weiterführende Interpretation: " + csv[21] + "; ";
                    } else {
                        weit_interpret = "";
                    }
                    String dat_mat = "";
                    if (!"".equals(csv[44])) {
                        dat_mat = "Datierendes Material: " + csv[21] + "; ";
                    } else {
                        dat_mat = "";
                    }
                    String beschreibung = lage + weit_interpret + dat_mat;
                    list.add(uuid2 + "|ARCHAEOLOGICAL HERITAGE (SITE).E27|SUMMARY.E62|" + beschreibung + "|SUMMARY.E62-0");

                    i++;

                } else {
                    i++;
                }

            }

            System.out.println("reading " + csvFile + " done...");
            System.out.println((i - 1) + " entities written...");
            System.out.println(((i - 1) * 7) + " lines written...");

        } catch (FileNotFoundException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println(e.toString());
                }
            }
        }
    }

    public static void writeToArchesFile() {

        try {

            File file = new File(outputpath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i) + "\r\n");
            }

            bw.close();

            System.out.println("writing " + outputpath + " done...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
