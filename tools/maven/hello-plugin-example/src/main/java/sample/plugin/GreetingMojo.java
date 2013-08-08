package sample.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Echos an object string to the output screen.
 * @goal echo
 * @requiresProject false
 */
public class GreetingMojo extends AbstractMojo
{

    /**
     * Any Object to print out.
     * @parameter expression="${echo.message}" default-value="Hello World..."
     */
    private Object message=null;

    public void execute() throws MojoExecutionException
    {
        getLog().info( message.toString() );
    }
}