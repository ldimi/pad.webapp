package be.ovam.art46.service.meetstaat.impl;

import be.ovam.pad.model.GenericRegel;
import be.ovam.pad.model.OfferteRegel;
import be.ovam.pad.util.MeetstaatUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Koen Corstjens on 5-9-13.
 */
public abstract class MeetstaatBasicService<T extends GenericRegel> {
    public static final String POSTNR_TOTAAL = ".t";
    public static final String POSTNR_TOTAAL_BTW = ".tbtw";
    private Logger log = Logger.getLogger(MeetstaatBasicService.class);

    public List<String> validate(List<T> regels) {
        List<String> result = new ArrayList<String>();
        for (T regel : regels) {
            String error = regel.validateRegel();
            if (error != null) {
                result.add(error);
            }
        }
        return result;
    }

    protected ArrayList<T> getMeetstaatRegelsFromCsv(Reader reader, Long id, List<String> fouten) throws IOException {
        if (fouten == null) {
            fouten = new ArrayList<String>();
        }
        ArrayList<T> regels = new ArrayList<T>();
        ICsvListReader listReader = null;
        HashMap<String, String> postNummers = new HashMap<String, String>();
        try {
            listReader = new CsvListReader(reader, CsvPreference.EXCEL_PREFERENCE);
            listReader.getHeader(true); // skip the header (can't be used with CsvListReader)
            while ((listReader.read()) != null) {
                T regel = getRegelFrom(listReader, id, fouten);
                if (regel != null && IsCoreectPostNr(regel.getPostnr(), fouten, postNummers)) {
                    regels.add(regel);
                }
            }
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (listReader != null) {
                listReader.close();
            }
        }
        return regels;
    }

    protected abstract T getRegelFrom(ICsvListReader listReader, Long id, List<String> fouten);

    private void addTotaalRegel(HashMap<String, T> totaalRegels, T regel, BigDecimal bedrag, BigDecimal bedragIncBtw) {
        String key = MeetstaatUtil.getParent(StringUtils.replace(regel.getPostnr(), POSTNR_TOTAAL, StringUtils.EMPTY));
        T totaalRegel = getParent(key, totaalRegels);
        totaalRegel.addTotaal(bedrag);
        totaalRegel.addBtwTotaal(bedragIncBtw);
        totaalRegel.setChilds(totaalRegel.getChilds() + 1);
        if (totaalRegel.getLevel() > 0) {
            addTotaalRegel(totaalRegels, totaalRegel, bedrag, bedragIncBtw);
        }
    }

    private T getParent(String parentPostnr, HashMap<String, T> totaalRegels) {
        parentPostnr = parentPostnr + POSTNR_TOTAAL;
        if (totaalRegels.containsKey(parentPostnr)) {
            return totaalRegels.get(parentPostnr);
        } else {
            T totaalRegel = getNewInstance();
            totaalRegel.setPostnr(parentPostnr);
            if (StringUtils.isEmpty(MeetstaatUtil.getParent(parentPostnr))) {
                totaalRegel.setTaak("Totaal (excl BTW)");
            } else {
                totaalRegel.setTaak("Totaal " + MeetstaatUtil.getParent(parentPostnr));
            }
            totaalRegels.put(parentPostnr, totaalRegel);
            return totaalRegel;
        }
    }

    protected List<T> bereken(List<T> regels) {
        return bereken(regels, new ArrayList<String>());
    }

    protected List<T> bereken(List<T> regels, List<String> fouten) {
        if (fouten == null) {
            fouten = new ArrayList<String>();
        }
        sort(regels);
        HashMap<String, T> totaalMeetstaten = new HashMap<String, T>();
        HashMap<String, T> mapRegels = new HashMap<String, T>();
        int hoogsteRootPostnr = 0;
        for (T regel : regels) {
            if ((!regel.isExtraRegel()) && regel.isTotaal()) {
                regel.setRegelTotaal(new BigDecimal(0));
                totaalMeetstaten.put(regel.getPostnr(), regel);
                int rootPostnr = MeetstaatUtil.getRootPostnr(regel.getPostnr());
                if (rootPostnr > hoogsteRootPostnr) {
                    hoogsteRootPostnr = rootPostnr;
                }
            }
        }

        BigDecimal totaalExtraRegels = new BigDecimal(0);
        BigDecimal totaalBtwExtraRegels = new BigDecimal(0);
        int aantalExtraRegels = 0;
        hoogsteRootPostnr++;
        for (T regel : regels) {
            if (regel.isExtraRegel()) {
                OfferteRegel offerteRegel = ((OfferteRegel) regel);
                if (StringUtils.isNotEmpty(offerteRegel.getType())) {
                    aantalExtraRegels++;
                    offerteRegel.setExtraRegelPostNr(StringUtils.EMPTY + hoogsteRootPostnr + "." + aantalExtraRegels);
                    if (offerteRegel.getRegelTotaal() != null) {
                        totaalExtraRegels = totaalExtraRegels.add(offerteRegel.getRegelTotaal());
                        totaalBtwExtraRegels = totaalBtwExtraRegels.add(offerteRegel.getRegelTotaalInclBTW());
                    }
                    mapRegels.put(offerteRegel.getExtraRegelPostNr(), regel);
                }

            }
            if ((!regel.isExtraRegel()) && !regel.isTotaal()) {
                if (mapRegels.containsKey(regel.getPostnr())) {
                    fouten.add("Postnr: " + regel.getPostnr() + " kan niet toegevoegd worden omdat dit Postnr meer dan een keer voor komt!\n");
                } else {
                    mapRegels.put(regel.getPostnr(), regel);
                    addTotaalRegel(totaalMeetstaten, regel, regel.getRegelTotaal(), regel.getRegelTotaalInclBTW());
                }
            }
        }

        T superTotaalRegel = totaalMeetstaten.get(".t");
        if (superTotaalRegel != null) {
            superTotaalRegel.addTotaal(totaalExtraRegels);
            T totaalBTW = getNewInstance();
            totaalBTW.setPostnr(POSTNR_TOTAAL_BTW);
            totaalBTW.setTaak("Totaal (incl BTW)");
            totaalBTW.addTotaal(superTotaalRegel.getRegelTotaalInclBTW());
            totaalBTW.addTotaal(totaalBtwExtraRegels);
            totaalBTW.setChilds(2);
            totaalMeetstaten.put(totaalBTW.getPostnr(), totaalBTW);
        }
        if (aantalExtraRegels > 0) {
            OfferteRegel titleOfferteRegel = new OfferteRegel();
            titleOfferteRegel.setExtraRegelPostNr(StringUtils.EMPTY + hoogsteRootPostnr);
            titleOfferteRegel.setExtraRegelTaak("Extra Posten");
            titleOfferteRegel.setChilds(aantalExtraRegels);
            mapRegels.put(titleOfferteRegel.getExtraRegelPostNr(), (T) titleOfferteRegel);
            OfferteRegel totaalExtraOfferteRegel = new OfferteRegel();
            totaalExtraOfferteRegel.setExtraRegelPostNr(StringUtils.EMPTY + hoogsteRootPostnr + ".t");
            totaalExtraOfferteRegel.setExtraRegelTaak("Totaal " + hoogsteRootPostnr);
            totaalExtraOfferteRegel.setRegelTotaal(totaalExtraRegels);
            totaalExtraOfferteRegel.setRegelTotaalInclBTW(totaalBtwExtraRegels);
            totaalExtraOfferteRegel.setChilds(aantalExtraRegels);
            totaalMeetstaten.put(totaalExtraOfferteRegel.getExtraRegelPostNr(), (T) totaalExtraOfferteRegel);
        }

        for (T totaalRegel : totaalMeetstaten.values()) {
            T parentRegel = mapRegels.get(MeetstaatUtil.getParent(totaalRegel.getPostnr()));
            if (parentRegel != null && !parentRegel.isExtraRegel()) {
                if (parentRegel.getAantal() != null) {
                    fouten.add("Postnr: " + parentRegel.getPostnr() + " Is een titel regel en mag geen hoeveelheden bevatten, hoeveelheid: " + parentRegel.getAantal() + " wordt  verwijderd !\n");
                }
                if (StringUtils.isNotEmpty(parentRegel.getEenheid())) {
                    fouten.add("Postnr: " + parentRegel.getPostnr() + " Is een titel regel en mag geen eenheid bevatten!, eenheid: " + parentRegel.getEenheid() + " wordt  verwijderd !\n");
                }
                if (parentRegel.getEenheidsprijs() != null) {
                    fouten.add("Postnr: " + parentRegel.getPostnr() + " Is een titel regel en mag geen eenheidsprijs bevatten!, eenheidsprijs: " + parentRegel.getEenheidsprijs() + " wordt  verwijderd !\n");
                }
                parentRegel.setAantal(null);
                parentRegel.setEenheid(null);
                parentRegel.setType(null);
                parentRegel.setEenheidsprijs(null);
                parentRegel.setRegelTotaal(null);
                parentRegel.setChilds(totaalRegel.getChilds());
            }
            if (totaalRegel.getChilds() > 0 && totaalRegel.getRegelTotaal() != null && !new BigDecimal("0").equals(totaalRegel.getRegelTotaal())) {
                mapRegels.put(totaalRegel.getPostnr(), totaalRegel);
            }
        }
        regels.clear();
        regels.addAll(mapRegels.values());
        sort(regels);
        return regels;
    }

    public abstract void sort(List<T> regels);

    public abstract T getNewInstance();

    protected boolean IsCoreectPostNr(String postnr, List<String> fouten, HashMap<String, String> postNummers) {
        if (StringUtils.isNotEmpty(postnr) && postNummers.containsValue(postnr)) {
            fouten.add("Postnr: " + postnr + " kan niet toegevoegd worden omdat dit Postnr meer dan een keer voor komt!\n");
            return Boolean.FALSE;
        } else {
            postNummers.put(postnr, postnr);
            return Boolean.TRUE;
        }
    }

}