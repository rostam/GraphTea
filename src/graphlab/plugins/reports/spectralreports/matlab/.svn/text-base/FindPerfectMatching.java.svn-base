/*
 * MATLAB Compiler: 4.11 (R2009b)
 * Date: Mon Aug 16 21:59:31 2010
 * Arguments: "-B" "macro_default" "-W" 
 * "java:graphlab.plugins.reports.spectralreports.matlab,FindPerfectMatching" "-d" 
 * "C:\\Users\\hoomanmohajeri\\Desktop\\BSc 
 * Project\\Matlab\\JATool\\graphlab.plugins.reports.spectralreports.matlab\\src" "-T" 
 * "link:lib" "-v" "class{FindPerfectMatching:C:\\Users\\hoomanmohajeri\\Desktop\\BSc 
 * Project\\Theory\\Matching\\FindPerfectMatching.m}" 
 */

package graphlab.plugins.reports.spectralreports.matlab;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;
import java.util.*;

/**
 * The <code>FindPerfectMatching</code> class provides a Java interface to the M-functions
 * from the files:
 * <pre>
 *  C:\\Users\\hoomanmohajeri\\Desktop\\BSc Project\\Theory\\Matching\\FindPerfectMatching.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a 
 * <code>FindPerfectMatching</code> instance when it is no longer needed to ensure that 
 * native resources allocated by this class are properly freed.
 * @version 0.0
 */
public class FindPerfectMatching extends MWComponentInstance<FindPerfectMatching>
{
    /**
     * Tracks all instances of this class to ensure their dispose method is
     * called on shutdown.
     */
    private static final Set<Disposable> sInstances = new HashSet<Disposable>();

    /**
     * Maintains information used in calling the <code>FindPerfectMatching</code> 
     *M-function.
     */
    private static final MWFunctionSignature sFindPerfectMatchingSignature =
        new MWFunctionSignature(/* max outputs = */ 1,
                                /* has varargout = */ false,
                                /* function name = */ "FindPerfectMatching",
                                /* max inputs = */ 1,
                                /* has varargin = */ false);

    /**
     * Shared initialization implementation - private
     */
    private FindPerfectMatching (final MWMCR mcr) throws MWException
    {
        super(mcr);
        // add this to sInstances
        synchronized(FindPerfectMatching.class) {
            sInstances.add(this);
        }
    }

    /**
     * Constructs a new instance of the <code>FindPerfectMatching</code> class.
     */
    public FindPerfectMatching() throws MWException
    {
        this(MatlabMCRFactory.newInstance());
    }
    
    private static MWComponentOptions getPathToComponentOptions(String path)
    {
        MWComponentOptions options = new MWComponentOptions(new MWCtfExtractLocation(path),
                                                            new MWCtfDirectorySource(path));
        return options;
    }
    
    /**
     * @deprecated Please use the constructor {@link #FindPerfectMatching(MWComponentOptions componentOptions)}.
     * The <code>com.mathworks.toolbox.javabuilder.MWComponentOptions</code> class provides API to set the
     * path to the component.
     * @param pathToComponent Path to component directory.
     */
    public FindPerfectMatching(String pathToComponent) throws MWException
    {
        this(MatlabMCRFactory.newInstance(getPathToComponentOptions(pathToComponent)));
    }
    
    /**
     * Constructs a new instance of the <code>FindPerfectMatching</code> class. Use this 
     * constructor to specify the options required to instantiate this component.  The 
     * options will be specific to the instance of this component being created.
     * @param componentOptions Options specific to the component.
     */
    public FindPerfectMatching(MWComponentOptions componentOptions) throws MWException
    {
        this(MatlabMCRFactory.newInstance(componentOptions));
    }
    
    /** Frees native resources associated with this object */
    public void dispose()
    {
        try {
            super.dispose();
        } finally {
            synchronized(FindPerfectMatching.class) {
                sInstances.remove(this);
            }
        }
    }
  
    /**
     * Invokes the first m-function specified by MCC, with any arguments given on
     * the command line, and prints the result.
     */
    public static void main (String[] args)
    {
        try {
            MWMCR mcr = MatlabMCRFactory.newInstance();
            mcr.runMain( sFindPerfectMatchingSignature, args);
            mcr.dispose();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    /**
     * Calls dispose method for each outstanding instance of this class.
     */
    public static void disposeAllInstances()
    {
        synchronized(FindPerfectMatching.class) {
            for (Disposable i : sInstances) i.dispose();
            sInstances.clear();
        }
    }

    /**
     * Provides the interface for calling the <code>FindPerfectMatching</code> M-function 
     * where the first input, an instance of List, receives the output of the M-function and
     * the second input, also an instance of List, provides the input to the M-function.
     * <p>M-documentation as provided by the author of the M function:
     * <pre>
     * % Build Tutte matrix.
     * % Add dummy vertices so that size is a power of 2.
     * % The chosen range of random values ensures success w.p. > 1/2
     * </pre>
     * </p>
     * @param lhs List in which to return outputs. Number of outputs (nargout) is
     * determined by allocated size of this List. Outputs are returned as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     * Each output array should be freed by calling its <code>dispose()</code>
     * method.
     *
     * @param rhs List containing inputs. Number of inputs (nargin) is determined
     * by the allocated size of this List. Input arguments may be passed as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or
     * as arrays of any supported Java type. Arguments passed as Java types are
     * converted to MATLAB arrays according to default conversion rules.
     * @throws MWException An error has occurred during the function call.
     */
    public void FindPerfectMatching(List lhs, List rhs) throws MWException
    {
        fMCR.invoke(lhs, rhs, sFindPerfectMatchingSignature);
    }

    /**
     * Provides the interface for calling the <code>FindPerfectMatching</code> M-function 
     * where the first input, an Object array, receives the output of the M-function and
     * the second input, also an Object array, provides the input to the M-function.
     * <p>M-documentation as provided by the author of the M function:
     * <pre>
     * % Build Tutte matrix.
     * % Add dummy vertices so that size is a power of 2.
     * % The chosen range of random values ensures success w.p. > 1/2
     * </pre>
     * </p>
     * @param lhs array in which to return outputs. Number of outputs (nargout)
     * is determined by allocated size of this array. Outputs are returned as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     * Each output array should be freed by calling its <code>dispose()</code>
     * method.
     *
     * @param rhs array containing inputs. Number of inputs (nargin) is
     * determined by the allocated size of this array. Input arguments may be
     * passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     * @throws MWException An error has occurred during the function call.
     */
    public void FindPerfectMatching(Object[] lhs, Object[] rhs) throws MWException
    {
        fMCR.invoke(Arrays.asList(lhs), Arrays.asList(rhs), sFindPerfectMatchingSignature);
    }

    /**
     * Provides the standard interface for calling the <code>FindPerfectMatching</code>
     * M-function with 1 input argument.
     * Input arguments may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     *
     * <p>M-documentation as provided by the author of the M function:
     * <pre>
     * % Build Tutte matrix.
     * % Add dummy vertices so that size is a power of 2.
     * % The chosen range of random values ensures success w.p. > 1/2
     * </pre>
     * </p>
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the M function.
     * @return Array of length nargout containing the function outputs. Outputs
     * are returned as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>. Each output array
     * should be freed by calling its <code>dispose()</code> method.
     * @throws MWException An error has occurred during the function call.
     */
    public Object[] FindPerfectMatching(int nargout, Object... rhs) throws MWException
    {
        Object[] lhs = new Object[nargout];
        fMCR.invoke(Arrays.asList(lhs), 
                    MWMCR.getRhsCompat(rhs, sFindPerfectMatchingSignature), 
                    sFindPerfectMatchingSignature);
        return lhs;
    }
}
