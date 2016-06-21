package com.investadvisor.resources;

import com.google.inject.Inject;
import com.investadvisor.InvestmentTargets;
import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetOfferProfit;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import com.investadvisor.model.view.InvestmentOption;
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
    @Path("investment-options")
    public List<InvestmentOption> getInvestmentOffers(ProvidedParams providedParams) throws Exception {
        List<InvestmentOption> result = new ArrayList<>();
        //broker coefficient
        for (InvestmentTarget investmentTarget : investmentTargets) {
            for (InvestmentTargetOffer offer : investmentTarget.getInvestmentTargetOffers()) {

                InvestmentTargetOfferProfit investmentTargetOfferProfit = offer.getInvestmentTargetOfferProfit();
                Boolean isSuitsUser = investmentTargetOfferProfit.calculateProfit(investmentTarget, providedParams);
                if (isSuitsUser) {
                    logger.info("investmentTarget = {}, offer = {} ", investmentTarget, offer);

                    offer.getInvestmentTargetOfferRisk().calculateAndSetRisk(investmentTarget, providedParams, investmentTargetOfferProfit);
                    InvestmentOption investmentOption = new InvestmentOption();
                    investmentOption.setInvestmentTypeName(investmentTarget.getInvestmentType().getName());
                    investmentOption.setInvestmentTypeLink(investmentTarget.getInvestmentTypeLink());
                    investmentOption.setInvestmentPartnerName(investmentTarget.getName());
                    investmentOption.setInvestmentPartnerLink(investmentTarget.getInvestmentPartnerLink());
                    investmentOption.setInvestmentOptionName(offer.getName());
                    investmentOption.setProfitPercentage(offer.getInvestmentTargetOfferProfit().getProfitPercentage());
                    investmentOption.setTotalRisk(offer.getInvestmentTargetOfferRisk().getTotalRisk());
                    investmentOption.setDetailsLink(offer.getLink());
                    result.add(investmentOption);
                }
            }
        }
        //filter by max allowed risk
        result = result.stream().filter(investmentOption -> investmentOption.getTotalRisk() <= providedParams.getMaxAllowedRisk()).collect(Collectors.toList());
        result.sort((investmentOption1, investmentOption2) -> investmentOption1.getTotalRisk().compareTo(investmentOption2.getTotalRisk()));
        return result;

    }
}
