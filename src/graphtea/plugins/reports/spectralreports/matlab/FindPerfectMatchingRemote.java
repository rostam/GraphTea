/*
 * MATLAB Compiler: 4.11 (R2009b)
 * Date: Mon Aug 16 21:59:31 2010
 * Arguments: "-B" "macro_default" "-W" 
 * "java:graphtea.plugins.reports.spectralreports.matlab,FindPerfectMatching" "-d"
 * "C:\\Users\\hoomanmohajeri\\Desktop\\BSc 
 * Project\\Matlab\\JATool\\graphtea.plugins.reports.spectralreports.matlab\\src" "-T"
 * "link:lib" "-v" "class{FindPerfectMatching:C:\\Users\\hoomanmohajeri\\Desktop\\BSc 
 * Project\\Theory\\Matching\\FindPerfectMatching.m}" 
 */

package graphtea.plugins.reports.spectralreports.matlab;

import com.mathworks.toolbox.javabuilder.pooling.Poolable;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The <code>FindPerfectMatchingRemote</code> class provides a Java RMI-compliant 
 * interface to the M-functions from the files:
 * <pre>
 *  C:\\Users\\hoomanmohajeri\\Desktop\\BSc Project\\Theory\\Matching\\FindPerfectMatching.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a 
 * <code>FindPerfectMatchingRemote</code> instance when it is no longer needed to ensure 
 * that native resources allocated by this class are properly freed, and the server-side 
 * proxy is unexported.  (Failure to call dispose may result in server-side threads not 
 * being properly shut down, which often appears as a hang.)  
 *
 * This interface is designed to be used together with 
 * <code>com.mathworks.toolbox.javabuilder.remoting.RemoteProxy</code> to automatically 
 * generate RMI server proxy objects for instances of 
 * graphtea.plugins.reports.spectralreports.matlab.FindPerfectMatching.
 */
public interface FindPerfectMatchingRemote extends Poolable
{
    /**
     * Provides the standard interface for calling the <code>FindPerfectMatching</code> 
     * M-function with 1 input argument.  
     *
     * Input arguments to standard interface methods may be passed as sub-classes of 
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of any 
     * supported Java type (i.e. scalars and multidimensional arrays of any numeric, 
     * boolean, or character type, or String). Arguments passed as Java types are 
     * converted to MATLAB arrays according to default conversion rules.
     *
     * All inputs to this method must implement either Serializable (pass-by-value) or 
     * Remote (pass-by-reference) as per the RMI specification.
     *
     * M-documentation as provided by the author of the M function:
     * <pre>
     * % Build Tutte matrix.
     * % Add dummy vertices so that size is a power of 2.
     * % The chosen range of random values ensures success w.p. > 1/2
     * </pre>
     *
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the M function.
     *
     * @return Array of length nargout containing the function outputs. Outputs are 
     * returned as sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>. 
     * Each output array should be freed by calling its <code>dispose()</code> method.
     *
     * @throws java.jmi.RemoteException An error has occurred during the function call or 
     * in communication with the server.
     */
    public Object[] FindPerfectMatching(int nargout, Object... rhs) throws RemoteException;
  
    /** Frees native resources associated with the remote server object */
    void dispose() throws RemoteException;
}
