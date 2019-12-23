package com.wujiabo.cloud.gateway.constant;

public interface SecurityConstants {
	/**
	 * token
	 */
	String TOKEN_KEY = "x-token";
	/**
	 * 标志
	 */
	String FROM = "from";
	/**
	 * 默认登录URL
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";

	String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";

    String JWT_SUBJECT = "Cloud";
}
