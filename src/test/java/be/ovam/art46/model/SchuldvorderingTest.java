package be.ovam.art46.model;

import be.ovam.pad.model.Schuldvordering;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Koen on 20/06/2014.
 */
public class SchuldvorderingTest{

    @Test
    public void isAangepast(){
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setHerziening_bedrag(null);
        schuldvordering.setHerziening_correct_bedrag(null);
        schuldvordering.setVordering_bedrag(null);
        schuldvordering.setVordering_correct_bedrag(null);
        Assert.assertFalse(schuldvordering.isAangepast());
    }

    @Test
    public void isAangepast1(){
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setHerziening_bedrag(15d);
        schuldvordering.setHerziening_correct_bedrag(null);
        schuldvordering.setVordering_bedrag(null);
        schuldvordering.setVordering_correct_bedrag(null);
        Assert.assertTrue(schuldvordering.isAangepast());
    }

    @Test
    public void isAangepast2(){
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setHerziening_bedrag(null);
        schuldvordering.setHerziening_correct_bedrag(15d);
        schuldvordering.setVordering_bedrag(null);
        schuldvordering.setVordering_correct_bedrag(null);
        Assert.assertTrue(schuldvordering.isAangepast());
    }

    @Test
    public void isAangepast3(){
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setHerziening_bedrag(15d);
        schuldvordering.setHerziening_correct_bedrag(15d);
        schuldvordering.setVordering_bedrag(null);
        schuldvordering.setVordering_correct_bedrag(null);
        Assert.assertFalse(schuldvordering.isAangepast());
    }
    @Test
    public void isAangepast4(){
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setHerziening_bedrag(15d);
        schuldvordering.setHerziening_correct_bedrag(15d);
        schuldvordering.setVordering_bedrag(15d);
        schuldvordering.setVordering_correct_bedrag(null);
        Assert.assertTrue(schuldvordering.isAangepast());
    }
    @Test
    public void isAangepast5(){
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setHerziening_bedrag(15d);
        schuldvordering.setHerziening_correct_bedrag(15d);
        schuldvordering.setVordering_bedrag(null);
        schuldvordering.setVordering_correct_bedrag(15d);
        Assert.assertTrue(schuldvordering.isAangepast());
    }
    @Test
    public void isAangepast6(){
        Schuldvordering schuldvordering = new Schuldvordering();
        schuldvordering.setHerziening_bedrag(15d);
        schuldvordering.setHerziening_correct_bedrag(15d);
        schuldvordering.setVordering_bedrag(15d);
        schuldvordering.setVordering_correct_bedrag(15d);
        Assert.assertFalse(schuldvordering.isAangepast());
    }
}
