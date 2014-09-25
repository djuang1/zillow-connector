package org.mule.modules.zillow;

import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.*;
import org.mule.api.annotations.rest.*;
import org.apache.commons.httpclient.HttpClient;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import java.io.IOException;

/**
 * Zillow Connector
 *
 * @author MuleSoft, Inc. - Dejim Juang
 */
@Connector(name="zillow", schemaVersion="1.0", friendlyName="Zillow")
public abstract class ZillowConnector
{
	public static final String BASE_URI = "http://www.zillow.com/";

    @RestHttpClient
    private HttpClient httpClient;
    
    /**
     * zwsidKey provided by Zillow during API registration process
     */
    @Configurable
    @RestUriParam("zwsidKey")
    @Summary("The ZWS-ID")
    @FriendlyName("ZWS-ID")
    private String zwsidKey;
    
    public ZillowConnector()
    {
        httpClient = new HttpClient();
    }

    /**
     * Set property
     *
     * @param zwsidKey Zillow API key (see http://www.zillow.com/howto/api/APIOverview.htm for access to their APIs)
     */
    public void setZwsidKey(String zwsidKey)
    {
        this.zwsidKey = zwsidKey;
    }

    public String getZwsidKey()
    {
        return this.zwsidKey;
    }
    
    /**
     * Set property
     *
     * @param httpClient
     */

    public void setHttpClient(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    public HttpClient getHttpClient(){
        return this.httpClient;
    }
    
    /**
     * getZestimate
     *
     * {@sample.xml ../../../doc/zillow-connector.xml.sample zillow:get-zestimate}
     *
     * @param zpid The Zillow Property ID for the property for which to obtain information. The parameter type is an integer.
     * @param rentzestimate Return Rent Zestimate information if available (boolean true/false, default: false)
     * @return String returns XML information of the most recent property Zestimate
     * @throws IOException Thrown in the event of a communications error
     *
     */
    @Processor
    @Summary("Get Zestimate")
    @RestCall(uri = BASE_URI + "/webservice/GetZestimate.htm?zws-id={zwsidKey}", method = HttpMethod.GET)
    public abstract String getZestimate(@RestQueryParam("zpid") @FriendlyName("Zillow Property ID") String zpid,
    		@FriendlyName("Rent Zestimate (true,false)") @Optional @Default("false") @RestQueryParam("rentzestimate") String rentzestimate) 
                    		   throws IOException;
    
    /**
     * getChart
     *
     * {@sample.xml ../../../doc/zillow-connector.xml.sample zillow:get-chart}
     *
     * @param zpid The Zillow Property ID for the property for which to obtain information. The parameter type is an integer.
     * @param unittype A string value that specifies whether to show the percent change, parameter value of "percent," or dollar change, parameter value of "dollar"
     * @param width An integer value that specifies the width of the generated image; the value must be between 200 and 600, inclusive
     * @param height An integer value that specifies the height of the generated image; the value must be between 100 and 300, inclusive. 
     * @param chartDuration The duration of past data that needs to be shown in the chart. Valid values are "1year", "5years" and "10years". If unspecified, the value defaults to "1year".
     * @return String Returns XML information of the most recent property Zestimate
     * @throws IOException Thrown in the event of a communications error
     *
     */
    @Processor
    @Summary("Get Chart")
    @RestCall(uri = BASE_URI + "/webservice/GetChart.htm?zws-id={zwsidKey}", method = HttpMethod.GET)
    public abstract String getChart(@RestQueryParam("zpid") @FriendlyName("Zillow Property ID") String zpid,
    		@FriendlyName("Unit Type (percent,dollar)") @Default("percent") @RestQueryParam("unit-type") String unittype,
    		@Optional @FriendlyName("Width (200-600)") @Default("200") @RestQueryParam("width") String width,
    		@Optional @FriendlyName("Height (100-300)") @Default("100") @RestQueryParam("height") String height,
    		@Optional @FriendlyName("Chart Duration (1year, 5years, 10years)") @Default("1year") @RestQueryParam("chartDuration") String chartDuration) 
                    		   throws IOException;
    
    /**
     * getSearchResults
     *
     * {@sample.xml ../../../doc/zillow-connector.xml.sample zillow:get-search-results}
     *
     * @param zpid The Zillow Property ID for the property for which to obtain information. The parameter type is an integer.
     * @param address The address of the property to search. This string should be URL encoded.
     * @param citystatezip The city+state combination and/or ZIP code for which to search. This string should be URL encoded. Note that giving both city and state is required. Using just one will not work.
     * @param rentzestimate Return Rent Zestimate information if available (boolean true/false, default: false)
     * @return String Returns XML information of the most recent property Zestimate
     * @throws IOException Thrown in the event of a communications error
     *
     */
    @Processor
    @Summary("Get Search Results")
    @RestCall(uri = BASE_URI + "/webservice/GetSearchResults.htm?zws-id={zwsidKey}", method = HttpMethod.GET)
    public abstract String getSearchResults(@RestQueryParam("zpid") @FriendlyName("Zillow Property ID") String zpid,
    		@FriendlyName("Address")  @RestQueryParam("address") String address,
    		@FriendlyName("City/State or Zip")  @RestQueryParam("citystatezip") String citystatezip,
    		@FriendlyName("Rent Zestimate (true,false)") @Default("false") @Optional @RestQueryParam("rentzestimate") String rentzestimate) 
                    		   throws IOException;
    
    /**
     * getComps
     *
     * {@sample.xml ../../../doc/zillow-connector.xml.sample zillow:get-comps}
     *
     * @param zpid The Zillow Property ID for the property for which to obtain information. The parameter type is an integer.
     * @param count The number of comparable recent sales to obtain (integer between 1 and 25)
     * @param rentzestimate Return Rent Zestimate information if available (boolean true/false, default: false)
     * @return String Returns XML information of the most recent property Zestimate
     * @throws IOException Thrown in the event of a communications error
     *
     */
    @Processor
    @Summary("Get Comps")
    @RestCall(uri = BASE_URI + "/webservice/GetComps.htm?zws-id={zwsidKey}", method = HttpMethod.GET)
    public abstract String getComps(@RestQueryParam("zpid") @FriendlyName("Zillow Property ID") String zpid,
    		@FriendlyName("Count (1-25)")  @Default("1") @RestQueryParam("count") String count,
    		@FriendlyName("Rent Zestimate (true,false)")  @Default("false") @Optional @RestQueryParam("rentzestimate") String rentzestimate) 
                    		   throws IOException;

}