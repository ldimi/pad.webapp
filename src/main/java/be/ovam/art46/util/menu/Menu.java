package be.ovam.art46.util.menu;

import be.ovam.art46.util.Application;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private List<MenuItem> menuList = new ArrayList<MenuItem>();
    
    public Menu add(MenuItem menuitem) {
        menuList.add(menuitem);
        return this;
    }
    
    public String render(String selectedId) {
        
        StringBuilder sb = new StringBuilder();
        for (MenuItem menuItem : this.menuList) {
            renderMenuItem(sb, menuItem, selectedId);
        }
        return sb.toString();
    }
    
    
    private void renderMenuItem( StringBuilder sb, MenuItem menuItem, String selectedId) {
        
        if (!userIsAuthorised(menuItem.getRoles())) {
            // menu punt moet niet gerendered worden
            return;
        }
        
        boolean selected = selectedId.startsWith(menuItem.getId());
        

        sb.append("<h3 id=\"")
            .append(menuItem.getId())
            .append("\" class=\"menuItem")
            .append(selected ? " selected" : "")
            .append("\" >");
        sb.append("<a href=\"")
            .append(menuItem.getLink())
            .append("\" target=\"")
            .append(menuItem.getTarget())
            .append("\" >")
            .append(menuItem.getLabel())
            .append("</a>");
        sb.append("</h3>");
        
        sb.append("<div class=\"submenu")
            .append(selected ? " selected" : "")
            .append("\" >");
        sb.append("<ul>");
        
        for (MenuItem subMenuItem : menuItem.getSubMenuList()) {
            renderSubMenuItem(sb, menuItem, subMenuItem, selectedId);
        }
        
        sb.append("<ul>");
        sb.append("</div>");
        
    }

    private void renderSubMenuItem( StringBuilder sb, MenuItem menuItem, MenuItem subMenuItem, String selectedId) {
        
        if (!userIsAuthorised(subMenuItem.getRoles())) {
            // menu punt moet niet gerendered worden
            return;
        }
        
        String fullId = menuItem.getId() + "." + subMenuItem.getId();
        boolean subItemselected = selectedId.equals(fullId);
        
        sb.append("<li id=\"")
            .append(fullId)
            .append("\" class=\"menuItem")
            .append(subItemselected ? " selected" : "")
            .append("\" >");
        sb.append("<a href=\"")
            .append(subMenuItem.getLink())
            .append("\" target=\"")
            .append(subMenuItem.getTarget())
            .append("\" >")
            .append(subMenuItem.getLabel())
            .append("</a>");
        sb.append("</li>");
    }
    
    
    
    private boolean userIsAuthorised (String roles) {
        if (roles == null || roles.length() == 0) {
            return true;
        }
        
        String[] rolesArr = roles.split(",");
        for (String role : rolesArr) {
            if (Application.INSTANCE.isUserInRole(role.trim())) {
                return true;
            }
        }
        return false;
    }

    
    
    
    
    
    
    
    
    public static class MenuItem {
        
        private String id;
        private String label;
        private String link;
        private String roles;
        private String target;
    
        private List<MenuItem> subMenuList = new ArrayList<MenuItem>();
        
    
        public MenuItem(String id, String label, String link, String roles, String target) {
            super();
            this.id = id;
            this.label = label;
            this.link = link;
            this.roles = roles;
            this.target = target;
        }
    
        public MenuItem(String id, String label, String link, String roles) {
            this(id, label, link, roles, "_self");
        }
    
        public MenuItem add(MenuItem menuitem) {
            subMenuList.add(menuitem);
            return this;
        }
        
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
        public String getLink() {
            return link;
        }
        public void setLink(String link) {
            this.link = link;
        }
        public String getRoles() {
            return roles;
        }
        public void setRoles(String roles) {
            this.roles = roles;
        }
    
        public List<MenuItem> getSubMenuList() {
            return subMenuList;
        }
        public void setSubMenuList(List<MenuItem> subMenuList) {
            this.subMenuList = subMenuList;
        }
    
        public String getTarget() {
            return target;
        }
    
        public void setTarget(String target) {
            this.target = target;
        }
        
    }
    
}

