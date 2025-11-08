package com.strink.orbis.dto;

import com.strink.orbis.model.VoteType;

public record VoteDto (VoteType voteType, Boolean isPost) {}
