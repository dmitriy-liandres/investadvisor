package com.investadvisor.resources;

import com.google.inject.Inject;
import com.investadvisor.InvestmentTargets;
import com.investadvisor.ProvidedParams;
import com.investadvisor.ProvidedParamsTest;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetOfferProfit;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Dmitriy Liandres
 * Date 12.06.2016
 */
@Api
@Path("/investment")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class InvestmentResource {

    private static final Logger logger = LoggerFactory.getLogger(InvestmentResource.class);
    private List<InvestmentTarget> investmentTargets;

    @Inject
    public InvestmentResource(@InvestmentTargets List<InvestmentTarget> investmentTargets) {
        this.investmentTargets = investmentTargets;
    }

    @GET
    @Path("investment-targets")
    public List<InvestmentTarget> getInvestmentTargets() throws InvocationTargetException, IllegalAccessException {
        return investmentTargets;

    }

    @POST
    @Path("investment-offers")
    public List<InvestmentTargetOffer> getInvestmentOffers(ProvidedParams providedParams) throws Exception {
          //broker coefficient
        List<InvestmentTargetOffer> investmentTargetCalculatedOffers = new ArrayList<>();
        for (InvestmentTarget investmentTarget : investmentTargets) {
            for (InvestmentTargetOffer offer : investmentTarget.getInvestmentTargetOffers()) {

                InvestmentTargetOfferProfit investmentTargetOfferProfit = offer.getInvestmentTargetOfferProfit();
                Boolean isSuitsUser = investmentTargetOfferProfit.calculateProfit(investmentTarget, providedParams);
                if (isSuitsUser) {
                    logger.info("investmentTarget = {}, offer = {} ", investmentTarget, offer);
                    investmentTargetCalculatedOffers.add(offer);
                }
                offer.getInvestmentTargetOfferRisk().calculateAndSetRisk(investmentTarget, providedParams, investmentTargetOfferProfit);
            }
        }
        //filter by max allowed risk
        investmentTargetCalculatedOffers = investmentTargetCalculatedOffers.stream().filter(offer -> offer.getInvestmentTargetOfferRisk().getTotalRisk() <= providedParams.getMaxAllowedRisk()).collect(Collectors.toList());
        investmentTargetCalculatedOffers.sort((offer1, offer2) -> offer1.getInvestmentTargetOfferRisk().getTotalRisk().compareTo(offer2.getInvestmentTargetOfferRisk().getTotalRisk()));
        return investmentTargetCalculatedOffers;

    }
}
