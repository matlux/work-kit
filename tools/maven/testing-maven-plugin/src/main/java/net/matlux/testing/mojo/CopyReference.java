package net.matlux.testing.mojo;

import static org.codehaus.plexus.logging.Logger.LEVEL_INFO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.codehaus.plexus.util.FileUtils;

/**
 * Mojo to copy reference data
 * 
 * @goal copy-reference
 */
public class CopyReference extends AbstractMojo {

	/** @component */
	private ArtifactResolver resolver;

	/** @parameter expression="${localRepository}" */
	private ArtifactRepository localRepository;

	/** @parameter expression="${project.remoteArtifactRepositories}" */
	private List<ArtifactRepository> remoteRepositories;

	/** @parameter default-value="${project}" */
	private MavenProject project;

	/**
	 * The target directories
	 * 
	 * @parameter
	 */
	private File[] targetDirectories = new File[] {};

	/**
	 * The reference artifactId
	 * 
	 * @parameter default-value="testing-reference"
	 */
	private String referenceArtifactId;

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Copying testing reference to target directories");
		extract(resolved(referenceArtifactId));
		File extractedTo = extractedTo();
		for ( File d : targetDirectories ){
			try {
				FileUtils.copyDirectory(extractedTo, d);
			} catch (IOException e) {
				throw new MojoFailureException(
						"Could not copy directory to " + d, e);
			}
		}
	}

	private void extract(File file) {
		ZipUnArchiver archiver = new ZipUnArchiver(file);
		archiver.enableLogging(new ConsoleLogger(LEVEL_INFO, "console"));
		File extractedTo = extractedTo();
		extractedTo.mkdirs();
		archiver.setDestDirectory(extractedTo);
		archiver.extract();
	}

	private File extractedTo() {
		return new File(System.getProperty("java.io.tmpdir"),
				referenceArtifactId);
	}

	@SuppressWarnings("unchecked")
	private File resolved(String artifactId) throws MojoFailureException {
		for (Artifact a : (Set<Artifact>) project.getDependencyArtifacts()) {
			if (artifactId.equals(a.getArtifactId())) {
				try {
					resolver.resolve(a, remoteRepositories, localRepository);
					return a.getFile();
				} catch (ArtifactResolutionException e) {
					throw new MojoFailureException(
							"Could not resolve artifact " + a, e);
				} catch (ArtifactNotFoundException e) {
					throw new MojoFailureException("Could not find artifact "
							+ a, e);
				}
			}
		}
		throw new MojoFailureException("Could not find artifact " + artifactId);
	}

}