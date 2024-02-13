// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.core;

public class AEvent{
    public String  category    = "default";
    public String  action      = "default";
    public String  label       = "default";
    public int     value       = 0;

    public AEvent category(String c){
    	this.category = c;
    	return this;
    }

    public AEvent action(String action){
    	this.action = action;
    	return this;
    }

    public AEvent label(String label){
    	this.label = label;
    	return this;
    }

    public AEvent value(int value){
    	this.value = value;
    	return this;
    }

    public String toString(){
        return "AEvent: "+ category + ", " + action + ", " + label + ", " + value;
    }
 }
