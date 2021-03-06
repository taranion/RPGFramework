/**
 * 
 */
package de.rpgframework.print;

import de.rpgframework.RPGFrameworkConstants;

/**
 * @author prelle
 *
 */
public enum PrintType {

	DIRECT,
	PDF,
	IMAGE,
	HTML,
	BBCODE,
	JSON
	;
	
	//--------------------------------------------------------------------
	public String getName() {
		return RPGFrameworkConstants.RES.getString("printtype."+this.name().toLowerCase());
	}
	
}
