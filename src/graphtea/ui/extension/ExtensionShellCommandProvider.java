// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.extension;

import graphtea.platform.extension.Extension;

import java.util.HashMap;
import java.util.Vector;
/**
 * a class for holding added commands
 */
public class ExtensionShellCommandProvider {
    public AbstractExtensionAction ths;
    public Extension trgClass;
    public String abrv;
    public String command;
    public String desc;
    public String help;
    public static HashMap<String, ExtensionShellCommandProvider> commandsDict = new HashMap<>();
    public static Vector<ExtensionShellCommandProvider> commands = new Vector<>();
    public String name;

    public ExtensionShellCommandProvider(AbstractExtensionAction ths, Extension trgClass, String name, String abrv, String command, String desc, String help) {
        this.ths = ths;
        this.trgClass = trgClass;
        this.name = name;
        this.abrv = abrv;
        this.command = command;
        this.desc = desc;
        this.help = help;
    }

    /**
     *
     * @param ths The corresponding extension action
     * @param trg The corresponding extension
     * @param name The name of command
     * @param abrv The abbreviated form
     * @param command The command
     * @param desc The description
     * @param help The help
     */
    public static void addCommand(AbstractExtensionAction ths, Extension trg, String name, String abrv, String command, String desc, String help) {
        ExtensionShellCommandProvider c = new ExtensionShellCommandProvider(ths, trg, name, abrv, command, desc, help);
        commands.add(c);
        commandsDict.put(name, c);
    }
}
