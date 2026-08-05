// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor;

import java.awt.*;

/**
 * Author: Azin Azadi
 * Email :
 */
public interface GBasicCellEditor<t> {
    void setEditingFinishedListener(EditingFinishedListener listener);

    Component getEditorComponent(t value);

    void cancelEditing();

    Object getEditorValue();
}
