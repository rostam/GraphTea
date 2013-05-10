package graphtea.extensions.io;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.io.*;

import org.basex.core.*;
import org.basex.core.cmd.*;
import org.basex.data.*;
import org.basex.io.serial.*;
import org.basex.query.*;
import org.basex.query.iter.*;
import org.basex.query.value.item.*;


/**
 * Created with IntelliJ IDEA.
 * User: rostam
 * Date: 5/10/13
 * Time: 9:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoadGraphML implements GraphReaderExtension {

    /** Database context. */
    static Context context = new Context();

    @Override
    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    @Override
    public String getName() {
        return "GraphML";
    }

    @Override
    public String getExtension() {
        return "xml";
    }

    @Override
    public GraphModel read(File file) throws GraphIOException {
        try {
            new CreateDB("input", file.getAbsolutePath()).execute(context);
            String queryS = "data(//node/@id)";
            query(queryS);
            process(queryS);
            serialize(queryS);
            iterate(queryS);
        } catch (BaseXException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return new GraphModel();
    }

    @Override
    public String getDescription() {
        return "GraphML format";
    }


    static void query(final String query) throws BaseXException {
        System.out.println(new XQuery(query).execute(context));
    }

    /**
     * This method uses the {@link QueryProcessor} to evaluate a query.
     * The resulting items are passed on to an {@link XMLSerializer} instance.
     * @param query query to be evaluated
     * @throws QueryException if an error occurs while evaluating the query
     */
    static void process(final String query) throws QueryException {
        // ------------------------------------------------------------------------
        // Create a query processor
        QueryProcessor proc = new QueryProcessor(query, context);

        // ------------------------------------------------------------------------
        // Execute the query
        Result result = proc.execute();

        // ------------------------------------------------------------------------
        // Print result as string.
        System.out.println(result);

        // ------------------------------------------------------------------------
        // Close the query processor
        proc.close();
    }

    /**
     * This method uses the {@link QueryProcessor} to evaluate a query.
     * The results are iterated one by one and converted to their Java
     * representation, using {{@link Item#toJava()}. This variant is especially
     * efficient if large result sets are expected.
     * @param query query to be evaluated
     * @throws QueryException if an error occurs while evaluating the query
     */
    static void iterate(final String query) throws QueryException {
        // ------------------------------------------------------------------------
        // Create a query processor
        QueryProcessor proc = new QueryProcessor(query, context);

        // ------------------------------------------------------------------------
        // Store the pointer to the result in an iterator:
        Iter iter = proc.iter();

        // ------------------------------------------------------------------------
        // Iterate through all items and serialize
        for(Item item; (item = iter.next()) != null;) {
            System.out.println(item.toJava());
        }

        // ------------------------------------------------------------------------
        // Close the query processor
        proc.close();
    }

    /**
     * This method uses the {@link QueryProcessor} to evaluate a query.
     * The results are iterated one by one and passed on to an
     * {@link XMLSerializer} instance. This variant is especially
     * efficient if large result sets are expected.
     * @param query query to be evaluated
     * @throws QueryException if an error occurs while evaluating the query
     * @throws IOException if an error occurs while serializing the results
     */
    static void serialize(final String query) throws QueryException, IOException {
        // ------------------------------------------------------------------------
        // Create a query processor
        QueryProcessor proc = new QueryProcessor(query, context);

        // ------------------------------------------------------------------------
        // Store the pointer to the result in an iterator:
        Iter iter = proc.iter();

        // ------------------------------------------------------------------------
        // Create a serializer instance
        Serializer ser = proc.getSerializer(System.out);

        // ------------------------------------------------------------------------
        // Iterate through all items and serialize contents
        for(Item item; (item = iter.next()) != null;) {
            ser.serialize(item);
        }

        // ------------------------------------------------------------------------
        // Close the serializer
        ser.close();
        System.out.println();

        // ------------------------------------------------------------------------
        // Close the query processor
        proc.close();
    }
}
