package com.investadvisor;

import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetLoader;
import com.investadvisor.model.InvestmentTypeName;
import io.dropwizard.servlets.tasks.Task;
import org.apache.commons.collections.CollectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Author Dmitriy Liandres
 * Date 12.06.2016
 */
public class ReloadProvidersTask extends Task {

    private static final Map<InvestmentTypeName, InvestmentTargetLoader> loaderPerInstanceMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ReloadProvidersTask.class);
    private final List<InvestmentTarget> investmentTargets;


    @Inject
    public ReloadProvidersTask(Injector injector, @InvestmentTargets List<InvestmentTarget> investmentTargets) {
        super("ReloadProvidersTask");
        this.investmentTargets = investmentTargets;
        ConfigurationBuilder cfgBldr = new ConfigurationBuilder();
        cfgBldr.forPackages("com.investadvisor.investarget");
        Set<Class<? extends InvestmentTargetLoader>> subTypes = new Reflections(cfgBldr).getSubTypesOf(InvestmentTargetLoader.class);
        subTypes.forEach(loader -> {
            if (!Modifier.isAbstract(loader.getModifiers())) {
                InvestmentTargetLoader investmentTargetLoader = injector.getInstance(loader);
                loaderPerInstanceMap.put(investmentTargetLoader.getInvestmentTypeName(), investmentTargetLoader);
            }
        });
    }

    @Override
    public void execute(ImmutableMultimap parameters, PrintWriter output) throws Exception {

        Collection investmentTargetsToUpdate = parameters.get("investmentTarget");

        List<InvestmentTarget> investmentTargetsLocal = new ArrayList<>();
        List<InvestmentTypeLoaderFuture> loadersFutureTasks = new ArrayList<>();
        for (InvestmentTargetLoader investmentTargetLoader : loaderPerInstanceMap.values()) {
            if(CollectionUtils.isEmpty(investmentTargetsToUpdate) || investmentTargetsToUpdate.contains(investmentTargetLoader.getInvestmentTypeName().name())) {
                InvestmentTypeLoaderFuture investmentTypeLoaderFuture = new InvestmentTypeLoaderFuture(investmentTargetLoader);
                Thread t = new Thread(investmentTypeLoaderFuture);
                t.start();
                loadersFutureTasks.add(investmentTypeLoaderFuture);
            }
        }

        loadersFutureTasks.forEach(pammLoadersFutureTask -> {
            try {
                investmentTargetsLocal.addAll(pammLoadersFutureTask.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Impossible to get investment target, targetName = " + pammLoadersFutureTask.getInvestmentTypeName(), e);
            }
        });

        investmentTargets.clear();
        investmentTargets.addAll(investmentTargetsLocal);
    }


    private static class InvestmentTypeLoaderFuture extends FutureTask<List<InvestmentTarget>> {
        private InvestmentTargetLoader investmentTargetLoader;

        public InvestmentTypeLoaderFuture(InvestmentTargetLoader investmentTargetLoader) {
            super(() -> investmentTargetLoader.load());
            this.investmentTargetLoader = investmentTargetLoader;
        }

        public InvestmentTypeName getInvestmentTypeName() {
            return investmentTargetLoader.getInvestmentTypeName();
        }
    }
}