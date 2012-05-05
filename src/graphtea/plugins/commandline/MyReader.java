// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Mohammad Ali Rostami
 */
public class MyReader extends Reader {
    private BufferedReader br;

    public MyReader(Reader r) {
        br = new BufferedReader(r);
    }


    public int read(char cbuf[], int off, int len) throws IOException {
        String s = br.readLine();
        cbuf = s.toCharArray();
        System.out.println(cbuf);
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() throws IOException {
        br.close();
    }
}
