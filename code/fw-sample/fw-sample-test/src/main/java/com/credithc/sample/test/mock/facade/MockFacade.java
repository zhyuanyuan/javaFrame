package com.credithc.sample.test.mock.facade;

import com.credithc.sample.test.mock.facade.dto.MockReq;
import com.credithc.sample.test.mock.facade.dto.MockRes;

public interface MockFacade {
	
	MockRes mockCall(MockReq req);

}
