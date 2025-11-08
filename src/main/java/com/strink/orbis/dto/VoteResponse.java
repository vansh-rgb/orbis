package com.strink.orbis.dto;

import com.strink.orbis.model.VoteType;

public record VoteResponse(VoteType voteType, Integer upvotes, Integer downvotes) {}
