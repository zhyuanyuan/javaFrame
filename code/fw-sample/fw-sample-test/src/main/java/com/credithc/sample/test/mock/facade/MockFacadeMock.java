package com.credithc.sample.test.mock.facade;

import com.credithc.sample.test.mock.facade.dto.MockReq;
import com.credithc.sample.test.mock.facade.dto.MockRes;

public class MockFacadeMock implements MockFacade {

	@Override
	public MockRes mockCall(MockReq req) {
		MockRes r = new MockRes();
		r.setRespCode("mock code");
		r.setRespMsg("mock msg:mockChain is:" + req.getRequestChain());
		return r;
	}

}
