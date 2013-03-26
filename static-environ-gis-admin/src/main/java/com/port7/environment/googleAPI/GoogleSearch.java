/*
 * Copyright (c) Port7 2013. All rights reserved. No part of this program may be reproduced, translated or transmitted, in any form or by any means, electronic, mechanical,
 * photocopying, recording or otherwise, or stored in any retrieval system of any nature, without written permission of the copyright holder.
 */
package com.port7.environment.googleAPI;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Martin Grunbaum (martin@port7.dk)
 * 
 */
@ManagedBean(name = "GoogleSearch")
@SessionScoped
public class GoogleSearch {
	private String	address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
