package com.investadvisor;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvestAdvisorApplication extends Application<InvestAdvisorConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(InvestAdvisorApplication.class);

    private GuiceBundle<InvestAdvisorConfiguration> guiceBundle;


    public static void main(String[] args) throws Exception {
        new InvestAdvisorApplication().run(args);
    }


    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<InvestAdvisorConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<InvestAdvisorConfiguration>newBuilder()
                .addModule(new GuiceModuleInvestAdvisor())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(InvestAdvisorConfiguration.class)
                .build();


        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new AssetsBundle());

    }

    @Override
    public void run(InvestAdvisorConfiguration configuration, Environment environment) throws Exception {
        DefaultReaderConfig readerConfig = new DefaultReaderConfig();
        readerConfig.setScanAllResources(true);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("com.investadvisor.resources");
        beanConfig.setScan(true);
        environment.jersey().register(io.swagger.jaxrs.listing.ApiListingResource.class);
        environment.jersey().register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        System.out.println("ok");

    }


}
