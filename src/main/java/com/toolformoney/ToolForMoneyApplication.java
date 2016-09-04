package com.toolformoney;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.toolformoney.filters.CORSResponseFilter;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ToolForMoneyApplication extends Application<ToolForMoneyConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(ToolForMoneyApplication.class);

    private GuiceBundle<ToolForMoneyConfiguration> guiceBundle;


    public static void main(String[] args) throws Exception {
        new ToolForMoneyApplication().run(args);
    }


    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ToolForMoneyConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<ToolForMoneyConfiguration>newBuilder()
                .addModule(new ToolForMoneyGuiceModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(ToolForMoneyConfiguration.class)
                .build();


        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new AssetsBundle());

    }

    @Override
    public void run(ToolForMoneyConfiguration configuration, Environment environment) throws Exception {
        DefaultReaderConfig readerConfig = new DefaultReaderConfig();
        readerConfig.setScanAllResources(true);

        //configure swagger
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("com.toolformoney.resources");
        beanConfig.setScan(true);
        environment.jersey().register(io.swagger.jaxrs.listing.ApiListingResource.class);
        environment.jersey().register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        environment.jersey().register(CORSResponseFilter.class);

        //allow all certificates
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

// Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            logger.error("Impossible to instantiate SSL", e);
        }

    }


}
