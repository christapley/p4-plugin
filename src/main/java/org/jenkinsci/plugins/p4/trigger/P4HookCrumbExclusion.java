package org.jenkinsci.plugins.p4.trigger;

import hudson.Extension;
import hudson.security.csrf.CrumbExclusion;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Extension
public class P4HookCrumbExclusion extends CrumbExclusion {

	@Override
	public boolean process(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
		throws IOException, ServletException {
		String pathInfo = req.getPathInfo();

		if (pathInfo == null) {
			return false;
		}

		if (pathInfo.equals(getExclusionPath())) {
			chain.doFilter(req, resp);
			return true;
		}

		return false;
	}

	public String getExclusionPath() {
		return "/" + P4Hook.URLNAME + "/change";
	}
}
