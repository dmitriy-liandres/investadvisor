package com.toolformoney;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.toolformoney.model.InvestmentTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 08.10.2015
 */
public class ToolForMoneyGuiceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(new TypeLiteral<List<InvestmentTarget>>() {})
                .annotatedWith(InvestmentTargets.class)
                .toInstance(new ArrayList<>());
    }


}