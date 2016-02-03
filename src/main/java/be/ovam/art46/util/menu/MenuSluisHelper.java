package be.ovam.art46.util.menu;

import be.ovam.art46.util.menu.Menu.MenuItem;


public enum MenuSluisHelper {
    INSTANCE;
    
    private static Menu menu;
    
    static {
        menu = new Menu();
        menu.add(new MenuItem("m_sluis_overdracht", "Overdrachten", "./s/sluis/overdracht/beheer", null)
                .add(new MenuItem("registratie", "registratie", "./s/sluis/registratie", null))
                .add(new MenuItem("instroom", "instroom", "./s/sluis/instroom", null))
                .add(new MenuItem("screening", "screening", "./s/sluis/screening", null))
                .add(new MenuItem("bibliotheek", "bibliotheek", "./s/sluis/bibliotheek", null))
                .add(new MenuItem("uitstroom", "uitstroom", "./s/sluis/uitstroom", null))
                .add(new MenuItem("overzicht", "overzicht", "./s/sluis/overdracht/beheer", null))
                .add(new MenuItem("historiek", "historiek", "./s/sluis/overdracht/historiek", null)))
            .add(new MenuItem("m_sluis_pad", " >> Pad", "./indexview.do", null, "_blank")
        );
    }

    
    public String render(String selectedId) {
        return menu.render(selectedId);
    }
    
}