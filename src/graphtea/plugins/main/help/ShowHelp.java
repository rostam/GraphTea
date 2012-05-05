package graphtea.plugins.main.help;

import graphtea.ui.extension.UIActionExtension;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.StaticUtils;

import javax.swing.*;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * @author Azin azadi
 */
public class ShowHelp implements UIActionExtension {
    public void actionPerformed(BlackBoard blackBoard) {
        JFrame dialog = new JFrame();
        QuestionAskUI qui=new QuestionAskUI(blackBoard);
        dialog.setTitle("Help");
        dialog.setContentPane(qui.getRootPanel());
        try {
            qui.getContent().setPage(new URL("http://www.google.com"));
        } catch (MalformedURLException e) {
            StaticUtils.addExceptionLog(e);
        }
        dialog.setSize(700,600);
        dialog.setVisible(true);
    }
}
