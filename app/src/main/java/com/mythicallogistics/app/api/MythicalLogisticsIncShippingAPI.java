package com.mythicallogistics.app.api;

import retrofit.client.Response;
import retrofit.http.*;
import retrofit.mime.TypedByteArray;

/**
 * Generated with SoapUI Retrofit Plugin
 **/

public interface MythicalLogisticsIncShippingAPI {

   @Headers("Accept: application/json")
   @GET("/api/b2bshipping/{version}/quote")
   public Response getQuote(@Query("weight") String weight , @Query("service") String service , @Query("origin") String origin , @Query("volume") String volume , @Query("object") String object , @Query("coordinates") String coordinates , @Query("destination") String destination , @Path("version") String version );

    @Headers("Accept: application/json")
   @POST("/api/b2bshipping/{version}/shipment")
   public Response postShipment(@Path("version") String version , @Body TypedByteArray body );

    @Headers("Accept: application/json")
   @GET("/api/b2bshipping/{version}/shipment/{shipment_id}")
   public Response getShipmentByShipmentId(@Path("shipment_id") String shipmentId , @Path("version") String version );

    @Headers("Accept: application/json")
   @DELETE("/api/b2bshipping/{version}/shipment/{shipment_id}")
   public Response deleteShipmentByShipmentId(@Path("shipment_id") String shipmentId , @Path("version") String version );

    @Headers("Accept: application/json")
   @GET("/api/b2bshipping/{version}/locations")
   public Response getLocations();

}
