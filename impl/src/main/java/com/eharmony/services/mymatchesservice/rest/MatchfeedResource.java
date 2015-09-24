/*
 * This software is the confidential and proprietary information of
 * eharmony.com and may not be used, reproduced, modified, distributed,
 * publicly displayed or otherwise disclosed without the express written
 * consent of eharmony.com.
 *
 * This software is a work of authorship by eharmony.com and protected by
 * the copyright laws of the United States and foreign jurisdictions.
 *
 * Copyright 2000-2015 eharmony.com, Inc. All rights reserved.
 *
 */
package com.eharmony.services.mymatchesservice.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eharmony.services.datastore.cassandra.model.CassandraMatchFeedItem;
import com.eharmony.services.datastore.mongodb.model.MatchFeedItem;
import com.eharmony.services.mymatchesservice.service.UserMatchesFeedService;


@Component @Path("/matchfeed")
public class MatchfeedResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Resource private UserMatchesFeedService userMatchesFeedService;

    /**
     * Returns user match feed by userId. filters the results by distance query parameter
     * @param userId   Integer
     * @param distance Integer, distance query param
     * @return List of MatchFeedItem
     */
    @GET
    @Path("/users/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MatchFeedItem> getMatchfeed(@PathParam("userId") Integer userId,
    		@QueryParam("distance") Integer distance) {

        log.info("fetching match feed for user ={}", userId);

        return userMatchesFeedService.getUserMatches(userId, distance);

    }
    
    @GET
    @Path("/users/{userId}/cassandra")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CassandraMatchFeedItem> getCassandraMatchfeed(@PathParam("userId") Integer userId,
    		@QueryParam("distance") Integer distance) {

        log.info("fetching match feed for user ={}", userId);
         
        return userMatchesFeedService.getUserMatchesFromCassandra(userId);

    }
    
    /**
     * Returns user match feed by userId. filters the results by distance query parameter
     * @param userId   Integer
     * @param matchId  Long
     * @return MatchFeedItem
     */
    @GET
    @Path("/users/{userId}/matches/{matchId}")
    @Produces(MediaType.APPLICATION_JSON)
    public MatchFeedItem getMatch(@PathParam("userId") Integer userId,
    		@PathParam("matchId") Long matchId) {

        log.info("fetching match feed for user ={} and match ={}", userId, matchId);

        return userMatchesFeedService.getUserMatch(userId, matchId);

    }

}
