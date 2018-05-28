package com.aiw.extend;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

public class PageJSRuleBundle implements TagRuleBundle {
	
	
	@Override
	public void cleanUp(State arg0, ContentProperty arg1, SiteMeshContext arg2) {
		
	}

	@Override
	public void install(State arg0, ContentProperty arg1, SiteMeshContext arg2) {
		arg0.addRule("pagejs", new ExportTagToContentRule(arg2, arg1.getChild("pagejs"), false));
	}
	
}
