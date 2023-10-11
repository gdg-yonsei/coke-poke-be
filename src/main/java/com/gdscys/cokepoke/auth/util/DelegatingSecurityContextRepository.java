package com.gdscys.cokepoke.auth.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class DelegatingSecurityContextRepository implements SecurityContextRepository {
    private final List<SecurityContextRepository> delegates;

    public DelegatingSecurityContextRepository(SecurityContextRepository... delegates) {
        this(Arrays.asList(delegates));
    }

    public DelegatingSecurityContextRepository(List<SecurityContextRepository> delegates) {
        Assert.notEmpty(delegates, "delegates cannot be empty");
        this.delegates = delegates;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        SecurityContext result = null;
        for (SecurityContextRepository delegate : this.delegates) {
            SecurityContext delegateResult = delegate.loadContext(requestResponseHolder);
            if (result == null || delegate.containsContext(requestResponseHolder.getRequest())) {
                result = delegateResult;
            }
        }
        return result;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        for (SecurityContextRepository delegate : this.delegates) {
            delegate.saveContext(context, request, response);
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        for (SecurityContextRepository delegate : this.delegates) {
            if (delegate.containsContext(request)) {
                return true;
            }
        }
        return false;
    }
}
