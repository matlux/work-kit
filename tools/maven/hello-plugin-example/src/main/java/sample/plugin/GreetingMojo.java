package sample.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Echos an object string to the output screen.
 * Test it by running:
 *      mvn sample.plugin:hello-maven-plugin:echo -Decho.message="The Eagle has Landed"
 * @goal echo
 * @requiresProject false
 */
public class GreetingMojo extends AbstractMojo
{



    /** @parameter default-value="${project}" */
    private MavenProject project;

    /**
     * Any Object to print out.
     * @parameter expression="${echo.message}" default-value="Hello World..."
     */
    private Object message=null;

    public void execute() throws MojoExecutionException
    {
        getLog().info( message.toString() );
        getLog().info( "basedir=" + project.getBasedir() );
    }
}