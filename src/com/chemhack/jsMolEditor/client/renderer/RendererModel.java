package com.chemhack.jsMolEditor.client.renderer;

import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.model.Bond;
import com.google.gwt.user.client.Cookies;

import java.util.Set;
import java.util.HashSet;
import java.util.Date;


public class RendererModel {

    private int atomRadius = 8;

    private String backColor = "white";

    private double bondDistance = 2.0;

    private double bondWidth = 2.0;

    private double defaultBondLength=0.5;

    private String externalHighlightColor = "orange";

    private String foreColor = "black";

    private String hoverOverColor = "lightGray";


    /**
     * Determines whether structures should be drawn as Kekule structures, thus
     * giving each carbon element explicitly, instead of not displaying the
     * element symbol. Example C-C-C instead of /\.
     */
    private boolean kekuleStructure = false;

    private double highlightRadiusModel = 0.7;

    private String mappingColor = "gray";

    private double margin = 0.05;

    private String selectedAtomBackColor = "rgb(55,168,213)";

    private boolean showAromaticity = false;

    private boolean showAromaticityInCDKStyle = false;

    private boolean showAtomAtomMapping = true;

    private boolean showAtomTypeNames = false;

    /**
     * Determines whether methyl carbons' symbols should be drawn explicit for
     * methyl carbons. Example C/\C instead of /\.
     */
    private boolean showEndCarbons = false;

    /**
     * Determines whether explicit hydrogens should be drawn.
     */
    private boolean showExplicitHydrogens = true;

    /**
     * Determines whether implicit hydrogens should be drawn.
     */
    private boolean showImplicitHydrogens = true;

    private boolean willDrawNumbers = false;

    private double zoomFactor = 0.8;

    private Set<Atom> highlightedAtoms = new HashSet<Atom>();


    private Set<Bond> highlightedBonds = new HashSet<Bond>();


    public int getAtomRadius() {
        return atomRadius;
    }

    public void setAtomRadius(int atomRadius) {
        this.atomRadius = atomRadius;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public double getBondDistance() {
        return bondDistance;
    }

    public void setBondDistance(double bondDistance) {
        this.bondDistance = bondDistance;
    }

    public double getBondWidth() {
        return bondWidth;
    }

    public void setBondWidth(double bondWidth) {
        this.bondWidth = bondWidth;
    }


    public String getExternalHighlightColor() {
        return externalHighlightColor;
    }

    public void setExternalHighlightColor(String externalHighlightColor) {
        this.externalHighlightColor = externalHighlightColor;
    }

    public String getForeColor() {
        return foreColor;
    }

    public void setForeColor(String foreColor) {
        this.foreColor = foreColor;
    }

    public String getHoverOverColor() {
        return hoverOverColor;
    }

    public void setHoverOverColor(String hoverOverColor) {
        this.hoverOverColor = hoverOverColor;
    }

    public boolean isKekuleStructure() {
        return kekuleStructure;
    }

    public void setKekuleStructure(boolean kekuleStructure) {
        this.kekuleStructure = kekuleStructure;
    }

    public double getHighlightRadiusModel() {
        return highlightRadiusModel;
    }

    public void setHighlightRadiusModel(double highlightRadiusModel) {
        this.highlightRadiusModel = highlightRadiusModel;
    }

    public String getMappingColor() {
        return mappingColor;
    }

    public void setMappingColor(String mappingColor) {
        this.mappingColor = mappingColor;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public String getSelectedAtomBackColor() {
        return selectedAtomBackColor;
    }

    public void setSelectedAtomBackColor(String selectedAtomBackColor) {
        this.selectedAtomBackColor = selectedAtomBackColor;
    }

    public boolean isShowAromaticity() {
        return showAromaticity;
    }

    public void setShowAromaticity(boolean showAromaticity) {
        this.showAromaticity = showAromaticity;
    }

    public boolean isShowAromaticityInCDKStyle() {
        return showAromaticityInCDKStyle;
    }

    public void setShowAromaticityInCDKStyle(boolean showAromaticityInCDKStyle) {
        this.showAromaticityInCDKStyle = showAromaticityInCDKStyle;
    }

    public boolean isShowAtomAtomMapping() {
        return showAtomAtomMapping;
    }

    public void setShowAtomAtomMapping(boolean showAtomAtomMapping) {
        this.showAtomAtomMapping = showAtomAtomMapping;
    }

    public boolean isShowAtomTypeNames() {
        return showAtomTypeNames;
    }

    public void setShowAtomTypeNames(boolean showAtomTypeNames) {
        this.showAtomTypeNames = showAtomTypeNames;
    }

    public boolean isShowEndCarbons() {
        return showEndCarbons;
    }

    public void setShowEndCarbons(boolean showEndCarbons) {
        this.showEndCarbons = showEndCarbons;
    }

    public boolean isShowExplicitHydrogens() {
        return showExplicitHydrogens;
    }

    public void setShowExplicitHydrogens(boolean showExplicitHydrogens) {
        this.showExplicitHydrogens = showExplicitHydrogens;
    }

    public boolean isShowImplicitHydrogens() {
        return showImplicitHydrogens;
    }

    public void setShowImplicitHydrogens(boolean showImplicitHydrogens) {
        this.showImplicitHydrogens = showImplicitHydrogens;
    }

    public boolean isWillDrawNumbers() {
        return willDrawNumbers;
    }

    public void setWillDrawNumbers(boolean willDrawNumbers) {
        this.willDrawNumbers = willDrawNumbers;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public Set<Atom> getHighlightedAtoms() {
        return highlightedAtoms;
    }

    
    public Set<Bond> getHighlightedBonds() {
        return highlightedBonds;
    }



    public void writeToCookies() {
        Date expires = new Date((new Date()).getTime() + 31536000000l);    //expires in one year

//        Cookies.setCookie("");
    }

    public void readFromCookies() {

    }

    public double getDefaultBondLength() {
        return defaultBondLength;
    }

    public void setDefaultBondLength(double defaultBondLength) {
        this.defaultBondLength = defaultBondLength;
    }
}

