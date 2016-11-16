package com.toolformoney.resources;

import com.google.inject.Inject;
import com.toolformoney.InvestmentTargets;
import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentTargetOfferProfit;
import com.toolformoney.model.InvestmentType;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.view.InvestmentOption;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
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

    private static final Set<InvestmentTypeName> allowedInvestmentTypeNames = new HashSet<>(Arrays.asList(InvestmentTypeName.values()));


    @Inject
    public InvestmentResource(@InvestmentTargets List<InvestmentTarget> investmentTargets) {
        this.investmentTargets = investmentTargets;
    }

    @GET
    @Path("allowed-investment-type-names")
    public Set<InvestmentTypeName> getAllowedInvestmentTypeNames() throws InvocationTargetException, IllegalAccessException {
        return allowedInvestmentTypeNames;
    }

    @PUT
    @Path("allowed-investment-type-names")
    public void getAllowedInvestmentTypeNames(Set<InvestmentTypeName> allowedInvestmentTypeNames) throws InvocationTargetException, IllegalAccessException {
        InvestmentResource.allowedInvestmentTypeNames.clear();
        InvestmentResource.allowedInvestmentTypeNames.addAll(allowedInvestmentTypeNames);

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
            if (investmentTarget.getInvestmentTypeName() == null || !allowedInvestmentTypeNames.contains(investmentTarget.getInvestmentTypeName())) {
                continue;
            }
            List<InvestmentOption> investmentOptionsPerTarget = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(investmentTarget.getInvestmentTargetOffers())) {
                for (InvestmentTargetOffer offer : investmentTarget.getInvestmentTargetOffers()) {

                    InvestmentTargetOfferProfit investmentTargetOfferProfit = offer.getInvestmentTargetOfferProfit();
                    Boolean isSuitsUser = investmentTargetOfferProfit.calculateProfit(investmentTarget, offer, providedParams);
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
                        investmentOptionsPerTarget.add(investmentOption);
                    }
                }
                //For Pamm we need not more than 1 offer per target
                if (investmentTarget.getInvestmentType() == InvestmentType.PAMM && investmentOptionsPerTarget.size() > 0) {
                    //get only one offer per target
                    result.add(investmentOptionsPerTarget.stream().max((io1, io2) -> io1.getProfitPercentage().compareTo(io2.getProfitPercentage())).get());
                } else {
                    result.addAll(investmentOptionsPerTarget);
                }
            }
        }
        //filter by max allowed risk
        result = result.stream().filter(investmentOption -> investmentOption.getTotalRisk() <= providedParams.getMaxAllowedRisk()).collect(Collectors.toList());
        //filter by profit >=0
        result = result.stream().filter(investmentOption -> investmentOption.getProfitPercentage() >= 0).collect(Collectors.toList());
        result.sort((investmentOption1, investmentOption2) -> -investmentOption1.getProfitPercentage().compareTo(investmentOption2.getProfitPercentage()));
        //set risk to 1 if it is 0
        result.forEach(investmentOption -> {
            if (investmentOption.getTotalRisk() < 1) {
                investmentOption.setTotalRisk(1.);
            }
        });


        return result;

    }
}
