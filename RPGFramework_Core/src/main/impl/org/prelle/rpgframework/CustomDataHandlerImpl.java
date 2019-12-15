/**
 * 
 */
package org.prelle.rpgframework;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.rpgframework.ConfigContainer;
import de.rpgframework.ConfigOption;
import de.rpgframework.RPGFramework;
import de.rpgframework.core.CustomDataHandler;
import de.rpgframework.core.RoleplayingSystem;

/**
 * @author prelle
 *
 */
public class CustomDataHandlerImpl implements CustomDataHandler {
	
	protected Logger logger = LogManager.getLogger("babylon.custom");
	
	private ConfigContainer configRoot;
	private ConfigOption<String> cfgDataDir;
	private Path localBaseDir;

	//--------------------------------------------------------------------
	public CustomDataHandlerImpl(ConfigContainer configRoot) throws IOException {
		this.configRoot = configRoot;
		prepareConfigNode();
		
		String dataDir = cfgDataDir.getStringValue();

		// Add player specific path
		localBaseDir = FileSystems.getDefault().getPath(dataDir, "custom");
		if (!Files.exists(localBaseDir)) {
			Files.createDirectory(localBaseDir);
		}
		
	}
	
	//------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private void prepareConfigNode() {
		cfgDataDir = (ConfigOption<String>) configRoot.getOption(RPGFramework.PROP_DATADIR);
	}
	
	//------------------------------------------------------------------
	@Override
	public List<String> getAvailableCustomIDs(RoleplayingSystem rules) {
		List<String> ret = new ArrayList<>();
		// Find directory specific to roleplaying system
		Path rpgDir = localBaseDir.resolve(rules.name().toLowerCase());
		try {
			DirectoryStream<Path> dir = Files.newDirectoryStream(rpgDir, "*.xml");
			dir.forEach(filepath -> {
				String fname = filepath.getFileName().toString();
				ret.add(fname.substring(0, fname.length()-4));
			});
		} catch (IOException e) {
			logger.error("Failed reading custom data directory "+rpgDir,e);
		}
		return ret;
	}

	//------------------------------------------------------------------
	@Override
	public CustomDataPackage getCustomData(RoleplayingSystem rules, String identifier) {
		// Find directory specific to roleplaying system
		return null;
	}

}
