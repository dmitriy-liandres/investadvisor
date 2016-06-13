package com.investadvisor.health;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class TemplateHealthCheck extends InjectableHealthCheck {


    public TemplateHealthCheck() {

    }

    @Override
    protected Result check() throws Exception {

        return Result.healthy();
    }

    @Override
    public String getName() {
        return "The only health check";
    }
}
