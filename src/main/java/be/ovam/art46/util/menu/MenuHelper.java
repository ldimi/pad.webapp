package be.ovam.art46.util.menu;

import be.ovam.art46.util.menu.Menu.MenuItem;


public enum MenuHelper {
    INSTANCE;
    
    private static final Menu menu;
    
    static {
        menu = new Menu();
        menu.add(new MenuItem("m_taken", "Taken", "./s/takenlijst", null)
                .add(new MenuItem("lijst", "Takenlijst", "./s/takenlijst", null))
                .add(new MenuItem("scanSchuldvordering", "Scan schuldvord.", "./s/tescannenschuldvorderingen", "secretariaat"))
                .add(new MenuItem("printSchuldvordering", "Print schuldvord.", "./s/teprintenschuldvorderingen", "secretariaat"))
                .add(new MenuItem("printBrief", "Print brieven", "./s/taken/brief/printen", "secretariaat"))
                .add(new MenuItem("keurSchuldvordering", "Keur schuldvord.", "./s/tekeurenschuldvorderingen", "ondertekenaar")))
        
			.add(new MenuItem("m_dossier", "Dossierbeheer", "./dossierlijst.do", null)
				.add(new MenuItem("dossiers", "Mijn dossiers", "./dossierlijst.do", null))
				.add(new MenuItem("zoeken", "Zoek dossier", "/pad/s/dossier/zoek", null))
				.add(new MenuItem("zoekenKadaster", "Zoek kadaster", "./kadasterzoek.do", null)))
								
            .add(new MenuItem("m_planning", "Planning", "./s/planning/individueel/bodemEnAfval", null)
                .add(new MenuItem("individueel", "planning IB", "./s/planning/individueel/bodemEnAfval", null))
                .add(new MenuItem("jaar", "planning JB", "./s/planning/jaar/overzichtbudgetten", null))
                .add(new MenuItem("vek", "VEK", "./s/VEKoverzichtslijst/", null)))

            // BOS 
            
            // Technische planning
                
			.add(new MenuItem("m_briefwisseling", "Briefwisseling", "/pad/briefzoek.do", "adminArt46,adminIVS")
                .add(new MenuItem("zoeken", "Zoek brief", "/pad/briefzoek.do", null))
                .add(new MenuItem("nieuw", "Nieuwe brief", "./briefnieuw.do?brief_id=0", null))
                .add(new MenuItem("details", "Brief details", "./briefdetailsview.do", null))
                .add(new MenuItem("leeg1", "----------------------", "javascript:void(0);", null))
                .add(new MenuItem("zoekAdres", "Zoek adres", "./s/adres/zoek", null))
                .add(new MenuItem("detailAdres", "Adres details", "/pad/adresnieuw.do", null))
                .add(new MenuItem("leeg2", "----------------------", "javascript:void(0);", null))
                .add(new MenuItem("qrbrievenzonderscan", "Te scannen inkomende brieven", "./s/beheer/qrbrievenzonderscan", null))
                .add(new MenuItem("gegenereerdeBrieven", "Te scannen uitgaande brieven", "./s/beheer/gegenereerdeBrieven", null)))

			.add(new MenuItem("m_overzichtslijsten", "Overzichtslijsten", "./actiejdzoekresult.do", null)
                .add(new MenuItem("actiesJD", "Acties JD", "./actiejdzoekresult.do", null))
                .add(new MenuItem("schuldvordering", "Schuldvordering", "./lijstschuldvorderingselect.do", null))
                .add(new MenuItem("realisatie", "Realisatiegraad", "./actiezoekresult.do", null))
                .add(new MenuItem("archief", "Archief", "./archiefzoekresult.do", null))
                .add(new MenuItem("verbintenissen", "Verbintenissen", "./dossiersfzselect.do", null))
                .add(new MenuItem("standVanZaken", "Stand van Zaken", "./dossierbestekprojectzoekresult.do", null))
                .add(new MenuItem("deelopdrachten", "Deelopdrachten", "./lijstnietafgeslotendeelopdrachten.do", null))
                .add(new MenuItem("queries", "Queries", "./querylijst.do", null))
                .add(new MenuItem("indicatoren", "Indicatoren", "./lijstindicatoren.do", null)))

			.add(new MenuItem("m_toepassingsbeheer", "Toepassingsbeheer", "./adrestypelijst.do", "adminArt46")
                .add(new MenuItem("adrestypes", "Adrestype", "./adrestypelijst.do", null))
                .add(new MenuItem("programmatypes", "Programmatype", "./s/beheer/programmatypelijst", null))
                .add(new MenuItem("dossierhouders", "Dossierhouder", "./s/beheer/dossierhouders", null))
                .add(new MenuItem("budgetaire-artikels", "Budgetaire Artikels", "./budgetairartikellijst.do", null))
                .add(new MenuItem("actietypes", "Actietype", "./actietypelijst.do", null))
                .add(new MenuItem("planningfasen", "Planning fase", "./s/beheer/planningfaselijst", null))
                .add(new MenuItem("budgetcodes", "Budget Code", "./s/beheer/budgetcodelijst", null))
                .add(new MenuItem("jaarbudget", "Jaarbudget", "./s/beheer/jaarbudget", null))
                .add(new MenuItem("mijlpalen", "Mijlpaal", "./s/beheer/mijlpalen", null))
                .add(new MenuItem("meetstaat.templates", "Meetstaat template", "./s/beheer/meetstaat/templates/", null))
                .add(new MenuItem("meetstaat.eenheid", "Meetstaat eenheid", "./s/beheer/meetstaat/eenheden/", null))
                .add(new MenuItem("briefTypesVos", "BriefType VOS", "./s/beheer/briefTypeVosLijst", null))
                .add(new MenuItem("briefAard", "BriefAard", "./s/beheer/briefAardLijst", null))
                .add(new MenuItem("nieuwDossier", "Nieuw dossier", "./startnieuwdossier.do", null))
                .add(new MenuItem("generateBarcode", "Genereer barcode", "./s/beheer/generatebarcode", null))
                .add(new MenuItem("dossierRechtsgronden", "Rechtsgrond", "./s/beheer/dossierRechtsgrondlijst", null))
                .add(new MenuItem("doelgroepen", "Doelgroep", "./s/beheer/doelgroepen", null))
                .add(new MenuItem("verontreinigActiviteitType", "Verontrein. activ.", "./s/beheer/verontreinigActiviteitType", null))
                .add(new MenuItem("instrumentType", "Instrumenttype", "./s/beheer/instrumentType", null))
                .add(new MenuItem("screeningMedium", "Screen Medium", "./s/beheer/screeningMedium", null))
                .add(new MenuItem("screeningStofgroepCode", "Screen Stofgroep", "./s/beheer/screeningStofgroepCode", null))
                .add(new MenuItem("screeningPrioriteit", "Screen Prioriteit", "./s/beheer/screeningPrioriteit", null)))
   				
    		.add(new MenuItem("m_artikel46", "Artikel 46", "/pad/lijstindex.do", null)
                .add(new MenuItem("opname", "Opname", "/pad/lijstopnameselect.do", null))
                .add(new MenuItem("voorGoedkeuring", "Voor goedkeuring", "/pad/lijstvoorgoedkselect.do", null))
                .add(new MenuItem("naGoedkeuring", "Na goedkeuring", "/pad/lijstnagoedkselect.do", null))
                .add(new MenuItem("naPublicatie", "Na publicatie", "/pad/lijstnapubselect.do", null))
                .add(new MenuItem("overzichtPublicatie", "Overzicht publicatie", "/pad/lijsthistoriek.do", null))
                .add(new MenuItem("lijsten", "Lijsten", "/pad/lijstenoverzicht.do", null))
                .add(new MenuItem("afgeslotenDossiers", "Afgesloten dossiers", "/pad/lijstafgeslotendossiers.do", null)))
    				
            .add(new MenuItem("m_naarSluis", ">> Sluis", "./s/sluis", null, "_blank"))
			.add(new MenuItem("m_wiki", ">> Wiki", "http://alfresco.ovam.be/share/page/site/ivs/wiki-page?title=PAD", null, "_blank"))
			.add(new MenuItem("m_afmelden", "Afmelden", "/pad/s/logout", null))
			.add(new MenuItem("m_test", "Test", "/pad/s/test", "testIVS")
				.add(new MenuItem("testrunner", "Run tests", "/pad/s/testrunner", "testIVS")))
				
                
//			.add(new MenuItem("m_adressenBeheer", "Adressen beheer", "/pad/s/zoeken/beheer", "testIVS")
//                .add(new MenuItem("zoeken", "zoeken", "/pad/s/zoeken/beheer", "testIVS"))
//                .add(new MenuItem("corrigeeronvolledigeadressen", "Corrigeer adressen", "/pad/s/adres/corrigeeradressen", "testIVS"))
//                .add(new MenuItem("consolideerdubbeleadressen", "Consolideer adressen", "/pad/s/adres/consolideeradressen", "testIVS")))
            
//				.add(new MenuItem("m_adressencontrole", "Adressen controle", "/pad/s/adressencontrole", "testIVS"))
//				.add(new MenuItem("nieuwadres", "Nieuw Adres", "/pad/s/nieuw_adres", "testIVS"))
//				.add(new MenuItem("nieuwrechtspersoonformulier", "Nieuwe Rechtspersoon", "/pad/s/nieuwerechtspersoon", "testIVS"))
//				.add(new MenuItem("nieuwcontactpersoonformulier", "Nieuwe contactpersoon", "/pad/s/nieuwecontactpersoon", "testIVS"))
				/*onderstaande worden tijdelijk afgezet: vereist wat meer aanpassing van de paginas of alles omzetten naar 1 pagina...*/
//				.add(new MenuItem("rechtspersoon_adresLink", "Link rechtspersonen aan Adres", "/pad/s/rechtspersoonadresLink", "testIVS"))
//				.add(new MenuItem("adres_rechtspersoonLink", "link adressen aan Rechtspersoon", "/pad/s/adresrechtspersoonLink", "testIVS"))
//				.add(new MenuItem("adrespersoonlink", "Adressen en personen linken", "/pad/s/adrespersoonlink", "testIVS"))
			
			;
    }

    
    public String render(String selectedId) {
        return menu.render(selectedId);
    }
    
}