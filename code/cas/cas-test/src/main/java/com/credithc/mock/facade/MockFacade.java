package com.credithc.mock.facade;

import com.credithc.mock.facade.dto.MockReq;
import com.credithc.mock.facade.dto.MockRes;

public interface MockFacade {
	
	MockRes mockCall(MockReq req);

}
