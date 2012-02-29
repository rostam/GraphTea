// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor;

import java.awt.*;

/**
 * Author: Azin Azadi
 * Email :
 */
public interface GBasicCellEditor<t> {
    public void setEditingFinishedListener(EditingFinishedListener listener);

    public Component getEditorComponent(t value);

    public void cancelEditing();

    public Object getEditorValue();
}
