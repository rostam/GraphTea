// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.undo;

/**
 * @author Rouzbeh Ebrahimi
 */
public interface Undoable {
    public void undo(UndoableActionOccuredData uaod);

    public void redo(UndoableActionOccuredData uaod);
}